/**
 * Created by xujianxin on 2017/3/30.
 */

var cityData={"11":{"119800":"北京市"},"12":{"129800":"天津市"},"13":{"130100":"石家庄市","130200":"保定市","130300":"邯郸市","130400":"秦皇岛市","130500":"张家口市","130600":"唐山市","130700":"承德市","130800":"廊坊市","130900":"沧州市","131000":"衡水市","131100":"邢台市","139900":"未匹配"},"14":{"140100":"太原市","140200":"大同市","140300":"阳泉市","140400":"长治市","140500":"晋城市","140600":"朔州市","140700":"晋中市","140800":"运城市","140900":"忻州市","141000":"临汾市","141100":"吕梁市","149900":"未匹配"},"15":{"150100":"呼和浩特市","150200":"包头市","150300":"乌海市","150400":"赤峰市","150500":"通辽市","150600":"鄂尔多斯市","150700":"呼伦贝尔市","150800":"巴彦淖尔市","150900":"乌兰察布市","151000":"兴安盟","151100":"锡林郭勒盟","151200":"阿拉善盟","159900":"未匹配"},"21":{"219800":"上海市"},"22":{"220100":"南京市","220200":"无锡市","220300":"徐州市","220400":"常州市","220500":"苏州市","220600":"南通市","220700":"连云港市","220800":"淮安市","220900":"盐城市","221100":"镇江市","221200":"泰州市","221300":"宿迁市","225000":"扬州市","229900":"未匹配"},"23":{"230100":"杭州市","230200":"宁波市","230300":"温州市","230400":"台州市","230500":"湖州市","230600":"嘉兴市","230700":"金华市","230800":"衢州市","230900":"绍兴市","231000":"丽水市","231100":"舟山市","239900":"未匹配"},"24":{"240100":"合肥市","240200":"芜湖市","240300":"蚌埠市","240400":"淮南市","240500":"马鞍山市","240600":"淮北市","240700":"铜陵市","240800":"安庆市","240900":"黄山市","241000":"滁州市","241100":"阜阳市","241200":"宿州市","241300":"六安市","241400":"亳州市","241500":"池州市","241600":"宣城市","249900":"未匹配"},"25":{"250100":"福州市","250200":"厦门市","250300":"泉州市","250400":"莆田市","250500":"三明市","250600":"漳州市","250700":"南平市","250800":"龙岩市","250900":"宁德市","259900":"未匹配"},"26":{"260100":"南昌市","260200":"上饶市","260300":"九江市","260400":"萍乡市","260500":"新余市","260600":"鹰潭市","260700":"赣州市","260800":"宜春市","260900":"景德镇市","261000":"吉安市","261100":"抚州市","269900":"未匹配"},"27":{"270100":"济南市","270200":"青岛市","270300":"淄博市","270400":"枣庄市","270500":"东营市","270600":"烟台市","270700":"潍坊市","270800":"济宁市","270900":"泰安市","271000":"威海市","271100":"日照市","271200":"滨州市","271300":"德州市","271400":"聊城市","271500":"临沂市","271600":"菏泽市","271700":"莱芜市","279900":"未匹配"},"31":{"310100":"沈阳市","310200":"大连市","310300":"鞍山市","310400":"抚顺市","310500":"本溪市","310600":"丹东市","310700":"锦州市","310800":"营口市","310900":"阜新市","311000":"辽阳市","311200":"盘锦市","311300":"铁岭市","311400":"朝阳市","311500":"葫芦岛市","319900":"未匹配"},"32":{"320100":"长春市","320200":"吉林市","320300":"四平市","320400":"辽源市","320500":"通化市","320600":"白山市","320700":"松原市","320800":"白城市","320900":"延边朝鲜族自治州","329900":"未匹配"},"33":{"330100":"哈尔滨市","330200":"齐齐哈尔市","330300":"牡丹江市","330400":"佳木斯市","330500":"大庆市","330600":"鸡西市","330700":"双鸭山市","330800":"伊春市","330900":"七台河市","331000":"鹤岗市","331100":"黑河市","331200":"绥化市","331300":"大兴安岭地区","339900":"未匹配"},"41":{"410100":"郑州市","410200":"开封市","410300":"洛阳市","410400":"平顶山市","410500":"安阳市","410600":"鹤壁市","410700":"新乡市","410800":"焦作市","410900":"濮阳市","411000":"许昌市","411100":"漯河市","411200":"三门峡市","411300":"南阳市","411400":"商丘市","411500":"信阳市","411600":"周口市","411700":"驻马店市","411800":"济源市","419900":"未匹配"},"42":{"420100":"武汉市","420200":"黄石市","420300":"十堰市","420400":"荆州市","420500":"宜昌市","420600":"襄阳市","420700":"鄂州市","420800":"荆门市","420900":"孝感市","421000":"黄冈市","421100":"咸宁市","421200":"随州市","421300":"恩施土家族苗族自治州","421400":"仙桃市","421500":"天门市","421600":"潜江市","421700":"神农架林区","429900":"未匹配"},"43":{"430100":"长沙市","430200":"株洲市","430300":"湘潭市","430400":"衡阳市","430500":"邵阳市","430600":"岳阳市","430700":"常德市","430800":"张家界市","430900":"益阳市","431000":"郴州市","431100":"永州市","431200":"怀化市","431300":"娄底市","431400":"湘西土家族苗族自治州","439900":"未匹配"},"51":{"510100":"广州市","510300":"珠海市","510400":"汕头市","510500":"佛山市","510600":"韶关市","510700":"湛江市","510800":"肇庆市","510900":"江门市","511000":"茂名市","511100":"惠州市","511200":"梅州市","511300":"汕尾市","511400":"河源市","511500":"阳江市","511600":"清远市","511700":"东莞市","511800":"中山市","511900":"潮州市","512000":"揭阳市","512100":"云浮市","518000":"深圳市","519900":"未匹配"},"55":{"550100":"南宁市","550200":"柳州市","550300":"桂林市","550400":"梧州市","550500":"北海市","550600":"防城港市","550700":"钦州市","550800":"贵港市","550900":"玉林市","551000":"百色市","551100":"贺州市","551200":"河池市","551300":"来宾市","551400":"崇左市","559900":"未匹配"},"56":{"560100":"海口市","560200":"三亚市","560300":"三沙市","560400":"五指山市","560500":"琼海市","560600":"儋州市","560700":"文昌市","560800":"万宁市","560900":"东方市","561000":"澄迈县","561100":"定安县","561200":"屯昌县","561300":"临高县","561400":"白沙黎族自治县","561500":"昌江黎族自治县","561600":"乐东黎族自治县","561700":"陵水黎族自治县","561800":"保亭黎族苗族自治县","561900":"琼中黎族苗族自治县","562000":"洋浦经济开发区","569900":"未匹配"},"61":{"610100":"西安市","610200":"宝鸡市","610300":"咸阳市","610400":"渭南市","610500":"铜川市","610600":"延安市","610700":"榆林市","610800":"汉中市","610900":"安康市","611000":"商洛市","619900":"未匹配"},"62":{"620100":"兰州市","620200":"嘉峪关市","620300":"金昌市","620400":"白银市","620500":"天水市","620600":"武威市","620700":"张掖市","620800":"酒泉市","620900":"平凉市","621000":"庆阳市","621100":"定西市","621200":"陇南市","621300":"临夏回族自治州","621400":"甘南藏族自治州","629900":"未匹配"},"63":{"630100":"西宁市","630200":"海东市","630300":"海北藏族自治州","630400":"海南藏族自治州","630500":"黄南藏族自治州","630600":"果洛藏族自治州","630700":"玉树藏族自治州","630800":"海西蒙古族藏族自治州","639900":"未匹配"},"64":{"640100":"银川市","640200":"石嘴山市","640300":"吴忠市","640400":"固原市","640500":"中卫市","649900":"未匹配"},"65":{"650100":"乌鲁木齐市","650200":"克拉玛依市","650300":"吐鲁番地区","650400":"哈密地区","650500":"阿克苏地区","650600":"喀什地区","650700":"和田地区","650800":"阿勒泰地区","650900":"昌吉回族自治州","651000":"博尔塔拉蒙古自治州","651100":"巴音郭楞蒙古自治州","651200":"克孜勒苏柯尔克孜自治州","651300":"伊犁哈萨克自治州","651400":"石河子市","651500":"阿拉尔市","651600":"图木舒克市","651700":"五家渠市","659900":"未匹配"},"71":{"719800":"香港特别行政区"},"72":{"729800":"澳门特别行政区"},"73":{"739800":"台湾"},"81":{"810100":"成都市","810200":"自贡市","810300":"攀枝花市","810400":"泸州市","810500":"德阳市","810600":"绵阳市","810700":"广元市","810800":"遂宁市","810900":"内江市","811000":"乐山市","811100":"南充市","811200":"宜宾市","811300":"眉山市","811400":"广安市","811500":"达州市","811600":"雅安市","811700":"巴中市","811800":"资阳市","811900":"阿坝藏族羌族自治州","812000":"甘孜藏族自治州","812100":"凉山彝族自治州","819900":"未匹配"},"82":{"820100":"贵阳市","820200":"六盘水市","820300":"遵义市","820400":"安顺市","820500":"铜仁市","820600":"毕节市","820700":"黔南布依族苗族自治州","820800":"黔西南布依族苗族自治州","820900":"黔东南苗族侗族自治州","829900":"未匹配"},"83":{"830100":"昆明市","830200":"曲靖市","830300":"玉溪市","830400":"保山市","830500":"昭通市","830600":"丽江市","830700":"普洱市","830800":"临沧市","830900":"楚雄彝族自治州","831000":"大理白族自治州","831100":"红河哈尼族彝族自治州","831200":"文山壮族苗族自治州","831300":"西双版纳傣族自治州","831400":"德宏傣族景颇族自治州","831500":"怒江傈僳族自治州","831600":"迪庆藏族自治州","839900":"未匹配"},"84":{"840100":"拉萨市","840200":"昌都地区","840300":"林芝地区","840400":"山南地区","840500":"日喀则地区","840600":"那曲地区","840700":"阿里地区","849900":"未匹配"},"85":{"859800":"重庆市"},"99":{"999900":"未匹配"},"root":{"A-G":[{"id":"24","name":"安徽省"},{"id":"72","name":"澳门特别行政区"},{"id":"11","name":"北京市"},{"id":"85","name":"重庆市"},{"id":"25","name":"福建省"},{"id":"51","name":"广东省"},{"id":"62","name":"甘肃省"},{"id":"55","name":"广西壮族自治区"},{"id":"82","name":"贵州省"}],"H-K":[{"id":"13","name":"河北省"},{"id":"42","name":"湖北省"},{"id":"33","name":"黑龙江省"},{"id":"41","name":"河南省"},{"id":"43","name":"湖南省"},{"id":"56","name":"海南省"},{"id":"32","name":"吉林省"},{"id":"22","name":"江苏省"},{"id":"26","name":"江西省"}],"L-S":[{"id":"31","name":"辽宁省"},{"id":"15","name":"内蒙古自治区"},{"id":"64","name":"宁夏回族自治区"},{"id":"63","name":"青海省"},{"id":"81","name":"四川省"},{"id":"27","name":"山东省"},{"id":"21","name":"上海市"},{"id":"14","name":"山西省"},{"id":"61","name":"陕西省"}],"T-Z":[{"id":"12","name":"天津市"},{"id":"73","name":"台湾"},{"id":"99","name":"未匹配"},{"id":"71","name":"香港特别行政区"},{"id":"65","name":"新疆维吾尔自治区"},{"id":"84","name":"西藏自治区"},{"id":"83","name":"云南省"},{"id":"23","name":"浙江省"}]}};

(function ($) {

    var CityPicker = function(element, options){
        this.id = '#' + element.id;
        this.$this = $(element);
        this.options = $.extend({}, CityPicker.defaults, options);
        this.$elId = '#' + this.options.containerId;
        this.options.data = cityData;
        this.init();
    };

    CityPicker.defaults = {
        title: '选择标签',
        containerId: 'earybia-city-picker',
        className: 'big-window',
        nameId: null,
        required: true,
        data: [],
        maxCount: 0,
        onConfirm: null,//确认回调
        cleanCallBack: null,
        selectedRegion: [],
        currentProvinceId: 0,
        filterOption: true
    };

    CityPicker.prototype = {
        init : function(){
            this.render();
        },
        render:function(){
            var $this = this;
            var container = $("#" + $this.options.containerId);

            var num = $this.options.containerId;
            if (container.length <= 0) {
                container = $('<div id="' + num + '" class="city-picker-window"></div>');
                $("body").append(container);

                var title = "<div class=\"title\"><b>" + $this.options.title + "</b>";
                if ($this.options.maxCount > 0) {
                    title += "<span class=\"tip\">(最多选择" + $this.options.maxCount + "项)</span>";
                }
                title += " &nbsp&nbsp;<span class='tip' style='font-weight:bold;' id='tip_" + num + "'></span><a href=\"javascript:void(0)\" ></a></div>";
                container.append(title);

                var seleTag = [];
                seleTag.push('<div class="sele-tag">');
                seleTag.push('<span class="st">已选择：</span>');
                seleTag.push('<div class="plus-tag tagbtn" id="city-sele-tags">');
                seleTag.push('</div>');
                seleTag.push('<a id="cleanAll' + num + '" href="javascript:void(0)" style="top: 30px;" class="btn btn-fixed">清除选择</a>');
                seleTag.push('<a id="btnSure_' + num + '" href="javascript:void(0)" class="btn">确定</a>');
                seleTag.push('</div>');
                container.append(seleTag.join(''));

                var tagsTab = [];
                tagsTab.push('<div class="tags-select-wrap">');
                tagsTab.push('<div class="tags-select-tab">');
                tagsTab.push('<a class="active">省份</a><a>城市</a>');
                tagsTab.push('</div>');
                tagsTab.push('<div class="tags-select-handler">');
                tagsTab.push('<a id="checkAll' + num + '" href="javascript:void(0)" class="">全选</a>');
                tagsTab.push('<a id="cleanPart' + num + '" href="javascript:void(0)" class="">清除</a>');
                tagsTab.push('</div>');
                tagsTab.push('<div class="tags-select-content">');
                tagsTab.push('<div class="tags-select">');
                tagsTab.push('<dl class="clearfix main">');

                $.each(cityData.root, function (key, item) {
                    tagsTab.push('<dt>' + key + '</dt>');
                    tagsTab.push('<dd>');
                    $.each(item, function (i, sItem) {
                        tagsTab.push('<a title="' + sItem.name + '" data-code="' + sItem.id + '" class="">' + sItem.name + '<span></span></a>');
                    });
                    tagsTab.push('</dd>');
                });

                tagsTab.push('</dl>');
                tagsTab.push('</div>');
                tagsTab.push('<div class="tags-select" style="display: none;">');
                tagsTab.push('<dl class="clearfix"><dd>');
                tagsTab.push('</dd></dl>');
                tagsTab.push('</div></div></div>');
                container.append(tagsTab.join(''));

                // 关闭选择框
                container.find(".title a").bind("click", function () {
                    $this.close();
                });

                $this.bind();
            }
            container.show();
            $this.setSelectedRegion();
        },
        setSelectedRegion: function() {
            let $this = this;
            let selectedRegion = this.options.selectedRegion;
            $.each(selectedRegion, function (i, o) {
                //展开城市对应的省份下的所有城市
                if (o.id > 100) {
                    $.each(cityData, function (ii, oo) {
                       let keys = Object.keys(oo);
                       if (keys.includes(o.id.toString())) {
                           $('.tags-select dd a[data-code="' + ii + '"] span', $($this.$elId)).click();
                       }
                    });
                }
                $.each($('.tags-select dd a', $($this.$elId)), function (index, obj) {
                    if ($(this).attr('data-code') == o.id) {
                        $this.addSeleTags($(this).attr('data-code'), $(this).attr('title'), $(this).attr('prev-code'));
                    }
                });
            });
        },
        bind : function(){
            var $this = this;
            $('.tags-select-tab a',$(this.$elId)).on('click',function(){
                var index = $('.tags-select-tab a',$($this.$elId)).index(this);
                $this.open(index);
            });

            $('.tags-select dd', $(this.$elId)).on('mouseenter', 'a', function () {
                if($(this).find('span').length>0){
                    $(this).css('padding-right','0px');
                }
            });
            $('.tags-select dd', $(this.$elId)).on('mouseleave', 'a', function () {
                if($(this).find('span').length>0){
                    $(this).css('padding-right','10px');
                }
            });

            $('.tags-select dd', $(this.$elId)).on('click', 'a', function () {
                $this.addSeleTags($(this).attr('data-code'), $(this).attr('title'), $(this).attr('prev-code'));
            });

            $('.tags-select dd a span', $($this.$elId)).on('click', function (event) {
                event.stopPropagation();
                var index = 1;
                var selectItem = $('.tags-select-content .tags-select', $($this.$elId)).eq(index).find('dd');
                selectItem.empty();
                var id = $(this).parent().attr('data-code');
                $this.currentProvinceId = id;
                if ($this.options.data[id] != null) {
                    $.each($this.options.data[id], function (k, v) {
                        selectItem.append('<a title="' + v + '" data-code="' + k + '" prev-code="' + id + '" class="">' + v + '</a>');
                    });
                }
                $this.open(index);
            });

            $('#city-sele-tags', $(this.$elId)).on('click','a .close',function (){
                $(this).parent().remove();
            });

            $('#btnSure_' + this.options.containerId).on('click',function(){
                if($.isFunction($this.options.onConfirm)){
                    var data = [];
                    $('#city-sele-tags a', $($this.$elId)).each(function (i, el) {
                        data.push({
                            id: $(el).attr('data-code'),
                            region: $(el).attr('data-code'),
                            name: $(el).text()
                        })
                    });
                    $this.options.onConfirm(data);
                    $this.close();
                }
            });
            $('#cleanAll' + this.options.containerId).on('click', function () {
                if ($.isFunction($this.options.cleanCallBack)) {
                    $('#city-sele-tags a', $($this.$elId)).remove();
                    $this.options.cleanCallBack();
                }
            });

            $('#checkAll' + this.options.containerId).on('click', function () {
                let position = $('.tags-select-tab a[class="active"]').text();
                if (position == '省份') {
                    $.each($('.tags-select dd a', $($this.$elId)), function (index, obj) {
                        if ($(this).attr('data-code') < 100) {
                            $this.addSeleTags($(this).attr('data-code'), $(this).attr('title'), $(this).attr('prev-code'));
                        }
                    });
                } else if (position == '城市') {
                    $.each($('.tags-select dd a', $($this.$elId)), function (index, obj) {
                        if ($(this).attr('data-code') > 100) {
                            $this.addSeleTags($(this).attr('data-code'), $(this).attr('title'), $(this).attr('prev-code'));
                        }
                    });
                }
            });

            $('#cleanPart' + this.options.containerId).on('click', function () {
                let position = $('.tags-select-tab a[class="active"]').text();
                if (position == '省份') {
                    $.each($('.tags-select dd a', $($this.$elId)), function (index, obj) {
                        let that = this;
                        $('#city-sele-tags a', $($this.$elId)).each(function (i, el) {
                            if ($(that).attr('data-code') ==  $(el).attr('data-code') && $(el).attr('data-code') < 100) {
                                $(el).remove();
                            }
                        });
                    });
                } else if (position == '城市') {
                    $.each($('.tags-select dd a', $($this.$elId)), function (index, obj) {
                        let that = this;
                        $('#city-sele-tags a', $($this.$elId)).each(function (i, el) {
                            if ($(that).attr('data-code') ==  $(el).attr('data-code') && $(el).attr('prev-code') == $this.currentProvinceId) {
                                $(el).remove();
                            }
                        });
                    });
                }

            });
        },
        open : function(index){
            $('.tags-select-tab a',$(this.$elId)).siblings().removeClass('active').eq(index).addClass('active');
            $('.tags-select-content .tags-select',$(this.$elId)).siblings().css('display','none').eq(index).css('display','block');
        },
        addSeleTags: function (code, name,prev_code) {
            let _this = this;
            if (this.options.maxCount != 0 && $('#city-sele-tags a', $(this.$elId)).length >= this.options.maxCount) {
                alert('最多只能选择' + this.options.maxCount + '项');
                return;
            }
            let flag = false;
            $('#city-sele-tags a', $(this.$elId)).each(function (i, el) {
                if ($(el).attr('data-code') == code) {
                    flag = true;
                    $(el).css('background','#46A4FF');
                    setTimeout(function(){$(el).css('background','#fafafa');},800);
                }
                if(_this.options.filterOption && prev_code != null && prev_code == $(el).attr('data-code')){
                    el.remove();
                }
                if(_this.options.filterOption && code == $(el).attr('prev-code')){
                    el.remove();
                }
            });
            if (flag == false) {
                $('#city-sele-tags', $(this.$elId)).append('<a href="javascript:;" data-code="' + code + '" prev-code="' + prev_code + '">' + name + '<span class="close"></span></a>');
            }
        },
        close: function () {
            $(this.$elId).hide();
            $("#" + this.options.containerId + "_bg").hide();
            $('#city-sele-tags').empty();
        }
    };

    $.fn.cityPicker = function (options) {
        return this.each(function () {
            return new CityPicker(this, options);
        });
    }
})(jQuery);
