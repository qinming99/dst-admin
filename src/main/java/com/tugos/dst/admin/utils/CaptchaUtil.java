package com.tugos.dst.admin.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成工具
 */
public class CaptchaUtil {

    private final static int WIDTH = 120;
    private final static int HEIGHT = 45;
    private final static int LENGTH = 4;
    private final static String EX_CHARS = "10ioIO";

    /**
     * 生成随机验证码
     */
    public static String getRandomCode(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
    	int i = 0;
    	while(i< LENGTH){
            int t=random.nextInt(123);
            if((t>=97||(t>=65&&t<=90)||(t>=48&&t<=57))&&(EX_CHARS ==null|| EX_CHARS.indexOf((char)t)<0)){
                sb.append((char)t);
                i++;
            }
        }
		return sb.toString();
    }

    /**
     * 生成验证码图片
     * @param randomCode 验证码
     */
    public static BufferedImage genCaptcha(String randomCode){
        // 创建画布
    	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    	Graphics2D g = image.createGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        Random random = new Random();

        // 绘制干扰线
        g.setColor(getRandColor(100, 180));
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(WIDTH - 1);
            int y = random.nextInt(HEIGHT - 1);
            int xl = random.nextInt(WIDTH / 2);
            int yl = random.nextInt(WIDTH / 2);
            g.drawLine(x, y, x + xl, y + yl + 20);
        }

        // 添加噪点
        float rate = 0.1f;
        int area = (int) (rate * WIDTH * HEIGHT);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            image.setRGB(x, y, getRandColor(100, 200).getRGB());
        }

        // 绘制验证码
        int size = HEIGHT -4;
        Font font = new Font("Algerian", Font.ITALIC, size);
        g.setFont(font);
        char[] chars = randomCode.toCharArray();
        for(int i = 0; i < randomCode.length(); i++){
        	g.drawChars(chars, i, 1, ((WIDTH -10) / randomCode.length()) * i + 5, HEIGHT /2 + size/2 - 6);
        }

        g.dispose();
        return image;
    }

    /**
     * 获取相应范围的随机颜色
     * @param min 最小值
     * @param max 最大值
     */
    private static Color getRandColor(int min, int max) {
    	min = min > 255 ? 255 : min;
    	max = max > 255 ? 255 : max;
        Random random = new Random();
        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);
        return new Color(r, g, b);
    }

}
