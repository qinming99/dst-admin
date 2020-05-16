/**
 * Created by xujianxin on 2016/12/9.
 */
(function ($) {

    var Upload = function (element, options) {
        this.id = '#' + element.id;
        this.$this = $(element);
        this.options = $.extend({}, Upload.defaults, options);
        this.fileName = null;
        this.btnUpload = null;
        this.btnIn = null;
        this.btnClear = null;
        this.progressParent = null;
        this.flieList = [];	//数据，为一个复合数组
        this.fileType = null;
    };

    Upload.defaults = {
        title: '上传文件',
        allowed: ['jpg', 'png', 'webp', 'ts','apk'],//允许上传文件的类型
        maxFileSize:500,//单位为K
        preview: false,//是否预览，为true则回调isReady方法
        data: { //附加参数
            resizeImage: 'false',//是否生成缩例图,上传图片适用
            imgWidth: null,//限制图片宽度,上传图片适用
            imgHeight: null,//限制图片高度,上传图片适用
            imgMinWidth: null,//限制图片最小宽度,上传图片适用
            imgMinHeight: null,//限制图片最小高度,上传图片适用
            imgMaxWidth: null,//限制图片最大宽度,上传图片适用
            imgMaxHeight: null//限制图处于最大高度,上传图片适用
        },
        isReady: function () {}, //选择好文件回调
        clearFn: function () {}, //清除上传回调
        success: function () {}  //上传成功回调
    };

    Upload.prototype.init = function () {
        var that = this;
        var form = '<div class="eurybia-upload"><div class="eurybia-upload-button"></div></div>';
        form = $(form);
        this.$this.wrap(form);
        //选择文件时过滤文件类型，会影响打开速度
        //this.$this.attr('accept', getAccepts(that.options.allowed));
        this.$this.after('<span class="eurybia-upload-icon"><i class="fa fa-cloud-upload" style="font-size: 14px"></i> ' + this.options.title + '</span>');
        this.$this.parents('.eurybia-upload').prepend('<div class="eurybia-filename eurybia-hidden"><span></span></div>')
        this.$this.parents('.eurybia-upload').append('<button type="button" class="eurybia-btnIn eurybia-upload-button eurybia-hidden">开始上传</button><button type="button" class="eurybia-Clear eurybia-upload-button clear eurybia-hidden">清除</a>');
        this.$this.parents('.eurybia-upload').append('<div class="progressParent eurybia-hidden"><p class="progress" style="width: 0%;"></p><span class="progressNum">0%</span></div>');

        that.fileName = this.$this.parents('.eurybia-upload').find('.eurybia-filename');
        that.btnUpload = this.$this.parents('.eurybia-upload').find('.eurybia-upload-button');
        that.btnIn = this.$this.parents('.eurybia-upload').find('.eurybia-btnIn');
        that.btnClear = this.$this.parents('.eurybia-upload').find('.eurybia-Clear');
        that.progressParent = this.$this.parents('.eurybia-upload').find('.progressParent');

        //点击选择文件按钮选文件
        this.$this.off('change').on("change", function () {
            if(that.action(this.files)){
                that.btnUpload.addClass('eurybia-hidden');
                that.btnIn.removeClass('eurybia-hidden');
                that.btnClear.removeClass('eurybia-hidden');
            }
        });

        //开始上传
        that.btnIn.on('click',function(){
            that.uploadFn();
        });

        //清除
        that.btnClear.on('click', function () {
            that.fileName.addClass('eurybia-hidden');
            that.btnUpload.removeClass('eurybia-hidden');
            that.btnIn.addClass('eurybia-hidden');
            that.btnClear.addClass('eurybia-hidden');
            that.flieList = [];//清除待上传文件列表
            if ($.isFunction(that.options.clearFn)) {
                that.options.clearFn(that.$this);
            }
            var jqObj = $(that.id);
            jqObj.val("");
            var domObj = jqObj[0];
            domObj.outerHTML = domObj.outerHTML;
            var newJqObj = jqObj.clone();
            jqObj.before(newJqObj);
            jqObj.remove();
            $(that.id).unbind().change(function (e) {
                if(that.action(this.files)){
                    that.btnUpload.addClass('eurybia-hidden');
                    that.btnIn.removeClass('eurybia-hidden');
                    that.btnClear.removeClass('eurybia-hidden');
                }
            });
        });
    };

    //提交上传
    Upload.prototype.action = function (obj) {
        var that = this;
        //如果没有文件
        if (obj.length < 1) {
            return false;
        }
        that.flieList = [];
        //暂时只支持单个文件上传
        var fileObj = obj[0];		//单个文件
        var name = fileObj.name;	//文件名
        var size = fileObj.size;	//文件大小
        var type = fileType(name);	//文件类型，获取的是文件的后缀

        //校验文件
        if (that.options.allowed.indexOf(type) == -1) {
            alert('文件类型不符合要求！');
            return false;
        }

        //文件大于30M，就不上传
        if (size > that.options.maxFileSize * 1024 || size == 0) {
            alert('“' + name + '”超过了' + that.options.maxFileSize + 'K，不能上传');
            return false;
        }

        var fType = fileObj.type.split('/');
        that.fileType = fType[0];
        if (that.options.preview) {
            if (fType[0] == 'image') {
                var fileReader = new FileReader();
                fileReader.onload = function () {
                    if ($.isFunction(that.options.isReady)) {
                        that.options.isReady(fileReader.result, that.$this);
                    }
                };
                fileReader.readAsDataURL(fileObj);
            }
        } else {
            that.fileName.removeClass('eurybia-hidden').find('span').html(name);
        }

        //给json对象添加内容，得到选择的文件的数据
        var itemArr = [fileObj, name, size, type];	//文件，文件名，文件大小，文件类型
        that.flieList.push(itemArr);
        return true;
    };

    //开始上传
    Upload.prototype.uploadFn = function(){
        var that = this;
        that.btnIn.addClass('eurybia-hidden');
        that.btnClear.addClass('eurybia-hidden');
        that.progressParent.removeClass('eurybia-hidden');

        if(that.flieList.length>0){
            var formData = new FormData();
            var arrNow = that.flieList[0];						//获取数据数组的当前项

            // 从当前项中获取上传文件，放到 formData对象里面，formData参数以key name的方式
            var result = arrNow[0];							//数据
            formData.append("imageFile" , result);

            var name = arrNow[1];							//文件名
            formData.append("email" , name);
            formData.append("maxFileSize" , that.options.maxFileSize); //最大文件大小
            //增加表单参数
            for (var key in that.options.data) {
                var val = that.options.data[key];
                if (val != null) {
                    formData.append(key, val);
                }
            }

            var progress = that.progressParent.find(".progress");			//上传进度背景元素
            var progressNum = that.progressParent.find(".progressNum");		//上传进度元素文字

            var request = $.ajax({
                type: "POST",
                url: that.options.url,
                data: formData,			//这里上传的数据使用了formData 对象
                processData : false, 	//必须false才会自动加上正确的Content-Type
                contentType : false,
                dataType:'json',
                //这里我们先拿到jQuery产生的XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
                xhr: function(){
                    var xhr = $.ajaxSettings.xhr();
                    if(onprogress && xhr.upload) {
                        xhr.upload.addEventListener("progress" , onprogress, false);
                        return xhr;
                    }
                },
                //上传成功后回调
                success: function(data){
                    //alert(data.success);
                    that.fileName.addClass('eurybia-hidden');
                    that.btnUpload.removeClass('eurybia-hidden');
                    that.btnIn.addClass('eurybia-hidden');
                    that.btnClear.addClass('eurybia-hidden');
                    that.progressParent.addClass('eurybia-hidden');
                    if ($.isFunction(that.options.success)) {
                        that.options.success(data, that.$this, that.fileType);
                    }
                },
                //上传失败后回调
                error: function(){
                    alert('上传失败！');
                    that.btnIn.removeClass('eurybia-hidden');
                    that.btnClear.removeClass('eurybia-hidden');
                    that.progressParent.addClass('eurybia-hidden');
                }
            });

            //侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
            function onprogress(evt) {
                var loaded = evt.loaded;	//已经上传大小情况
                var tot = evt.total;		//附件总大小
                var per = Math.floor(100 * loaded / tot);  //已经上传的百分比
                progressNum.html(per + "%");
                progress.css("width", per + "%");
            }
        }


    };

    //通过文件名，返回文件的后缀名
    function fileType(name) {
        var nameArr = name.split(".");
        return nameArr[nameArr.length - 1].toLowerCase();
    }

    //处理过滤文件类型
    function getAccepts(exts) {
        var accepts = []
        $.each(exts, function (i, n) {
            accepts.push('.' + n);
        });
        return accepts.join(',');
    }


    $.fn.eurybiaUpload = function (options) {
        return this.each(function () {
            var upload = new Upload(this, options);
            upload.init();
            return upload;
        });
    };
})(jQuery);