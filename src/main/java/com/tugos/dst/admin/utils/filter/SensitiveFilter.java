package com.tugos.dst.admin.utils.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NavigableSet;

/**
 * @author qinming
 * @date 2020-11-26 23:13:42
 * <p> 敏感词过滤器 </p>
 */
public class SensitiveFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SENSITIVE_WORD_FILE = "sensitive_words.txt";

    /**
     * 默认的单例，使用自带的敏感词库
     */
    public static  SensitiveFilter DEFAULT = null;

    static {
        try {
            AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, "666DSTADMIN88888".getBytes(), "0102030405060708".getBytes());
            ClassPathResource classPathResource = new ClassPathResource(SENSITIVE_WORD_FILE);
            InputStream inputStream = classPathResource.getInputStream();
            byte[] buf = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = (inputStream.read(buf))) != -1) {
                sb.append(new String(buf, 0, len));
            }
            //解密需要过滤的关键字
            String decryptStr = aes.decryptStr(sb.toString(), CharsetUtil.CHARSET_UTF_8);
            ByteArrayInputStream input = new ByteArrayInputStream(decryptStr.getBytes());
            //构建过滤器
            DEFAULT = new SensitiveFilter(new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)));
            inputStream.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 为2的n次方，考虑到敏感词大概在10k左右，
     * 这个数量应为词数的数倍，使得桶很稀疏
     * 提高不命中时hash指向null的概率，
     * 加快访问速度。
     */
    static final int DEFAULT_INITIAL_CAPACITY = 131072;

    /**
     * 类似HashMap的桶，比较稀疏。
     * 使用2个字符的hash定位。
     */
    protected SensitiveNode[] nodes = new SensitiveNode[DEFAULT_INITIAL_CAPACITY];

    /**
     * 构建一个空的filter
     */
    public SensitiveFilter() {

    }

    /**
     * 加载一个文件中的词典，并构建filter<br/>
     * 文件中，每行一个敏感词条<br/>
     * <b>注意：</b>读取完成后会调用{@link BufferedReader#close()}方法。<br/>
     * <b>注意：</b>读取中的{@link IOException}不会抛出
     */
    public SensitiveFilter(BufferedReader reader) {
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                put(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个敏感词，如果词的长度（trim后）小于2，则丢弃<br/>
     * 此方法（构建）并不是主要的性能优化点。
     */
    public boolean put(String word) {
        // 长度小于2的不加入
        if (word == null || word.trim().length() < 2) {
            return false;
        }
        // 两个字符的不考虑
        if (word.length() == 2 && word.matches("\\w\\w")) {
            return false;
        }
        StringPointer sp = new StringPointer(word.trim());
        // 计算头两个字符的hash
        int hash = sp.nextTwoCharHash(0);
        // 计算头两个字符的mix表示（mix相同，两个字符相同）
        int mix = sp.nextTwoCharMix(0);
        // 转为在hash桶中的位置
        int index = hash & (nodes.length - 1);

        // 从桶里拿第一个节点
        SensitiveNode node = nodes[index];
        if (node == null) {
            // 如果没有节点，则放进去一个
            node = new SensitiveNode(mix);
            // 并添加词
            node.words.add(sp);
            // 放入桶里
            nodes[index] = node;
        } else {
            // 如果已经有节点（1个或多个），找到正确的节点
            for (; true; node = node.next) {
                // 匹配节点
                if (node.headTwoCharMix == mix) {
                    node.words.add(sp);
                    return true;
                }
                // 如果匹配到最后仍然不成功，则追加一个节点
                if (node.next == null) {
                    new SensitiveNode(mix, node).words.add(sp);
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 对句子进行敏感词过滤<br/>
     * 如果无敏感词返回输入的sentence对象，即可以用下面的方式判断是否有敏感词：<br/><code>
     * String result = filter.filter(sentence, '*');<br/>
     * if(result != sentence){<br/>
     * &nbsp;&nbsp;// 有敏感词<br/>
     * }
     * </code>
     *
     * @param sentence 句子
     * @param replace  敏感词的替换字符
     * @return 过滤后的句子
     */
    public String filter(String sentence, char replace) {
        // 先转换为StringPointer
        StringPointer sp = new StringPointer(sentence);

        // 标示是否替换
        boolean replaced = false;

        // 匹配的起始位置
        int i = 0;
        while (i < sp.length - 2) {
            /*
             * 移动到下一个匹配位置的步进：
             * 如果未匹配为1，如果匹配是匹配的词长度
             */
            int step = 1;
            // 计算此位置开始2个字符的hash
            int hash = sp.nextTwoCharHash(i);
            /*
             * 根据hash获取第一个节点，
             * 真正匹配的节点可能不是第一个，
             * 所以有后面的for循环。
             */
            SensitiveNode node = nodes[hash & (nodes.length - 1)];
            /*
             * 如果非敏感词，node基本为null。
             * 这一步大幅提升效率
             */
            if (node != null) {
                /*
                 * 如果能拿到第一个节点，
                 * 才计算mix（mix相同表示2个字符相同）。
                 * mix的意义和HashMap先hash再equals的equals部分类似。
                 */
                int mix = sp.nextTwoCharMix(i);
                /*
                 * 循环所有的节点，如果非敏感词，
                 * mix相同的概率非常低，提高效率
                 */
                outer:
                for (; node != null; node = node.next) {
                    /*
                     * 对于一个节点，先根据头2个字符判断是否属于这个节点。
                     * 如果属于这个节点，看这个节点的词库是否命中。
                     * 此代码块中访问次数已经很少，不是优化重点
                     */
                    if (node.headTwoCharMix == mix) {
                        /*
                         * 查出比剩余sentence小的最大的词。
                         * 例如剩余sentence为"色情电影哪家强？"，
                         * 这个节点含三个词从小到大为："色情"、"色情电影"、"色情信息"。
                         * 则从“色情电影”开始向前匹配
                         */
                        NavigableSet<StringPointer> desSet = node.words.headSet(sp.substring(i), true);
                        for (StringPointer word : desSet.descendingSet()) {
                            /*
                             * 仍然需要再判断一次，例如"色情信息哪里有？"，
                             * 如果节点只包含"色情电影"一个词，
                             * 仍然能够取到word为"色情电影"，但是不该匹配。
                             */
                            if (sp.nextStartsWith(i, word)) {
                                // 匹配成功，将匹配的部分，用replace制定的内容替代
                                sp.fill(i, i + word.length, replace);
                                // 跳过已经替代的部分
                                step = word.length;
                                // 标示有替换
                                replaced = true;
                                // 跳出循环（然后是while循环的下一个位置）
                                break outer;
                            }
                        }

                    }
                }
            }

            // 移动到下一个匹配位置
            i += step;
        }

        // 如果没有替换，直接返回入参（节约String的构造copy）
        if (replaced) {
            return sp.toString();
        } else {
            return sentence;
        }
    }

}
