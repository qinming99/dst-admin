    <script src="/css/bootstrap/jquery.js"></script>
    <script src="/css/bootstrap/bootstrap.min.js"></script>
    <script src="/css/bootstrap/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/css/bootstrap/plugins/layer/layer.js"></script>
    <script src="/css/bootstrap/plugins/layer/extend/layer.ext.js"></script>
    <script src="/css/bootstrap/plugins/laydate5/laydate.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapTable/bootstrap-table.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapTable/bootstrap-table-zh-CN.js"></script>
    <script src="/css/bootstrap/plugins/formValidator/jquery.form-validator.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapSelect/bootstrap-select.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapDialog/bootstrap-dialog.min.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapTags/bootstrap-tagsinput.js"></script>
    <script src="/css/bootstrap/plugins/nprogress/nprogress.js"></script>
    <script src="/css/bootstrap/plugins/pagination/mricode.pagination.js"></script>
    <script src="/css/bootstrap/plugins/eurybia-upload/eurybia-upload.js"></script>
    <script src="/css/bootstrap/plugins/bootstrapSwitch/bootstrap-switch.js"></script>
    <script src="/js/vue.js"></script>

    <script>

        function encodeParam(v) {
            let s = encodeURIComponent(v)
                    .replace( '@',/%40/gi)
                    .replace( ':',/%3A/gi)
                    .replace( '$',/%24/gi)
                    .replace( ',',/%2C/gi)
                    .replace( ';',/%3B/gi)
                    .replace( '+',/%2B/gi)
                    .replace( ';',/%3D/gi)
                    .replace( '?',/%3F/gi)
                    .replace( '/',/%2F/gi);
            return s;
        }

        function ajax(opt) {
            //打开遮罩
            $("#loadingModal").modal('show');
            opt = opt || {};
            opt.method = opt.method.toUpperCase() || 'POST';
            opt.url = opt.url || '';
            opt.async = opt.async || true;
            opt.data = opt.data || null;
            opt.success = opt.success || function () {};
            var xmlHttp = null;
            if (XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
            else {
                xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
            }var params = [];
            for (var key in opt.data){
                params.push(key + '=' + encodeParam(opt.data[key]));
            }
            var postData = params.join('&');
            if (opt.method.toUpperCase() === 'POST') {
                xmlHttp.open(opt.method, opt.url, opt.async);
                xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8');
                xmlHttp.send(postData);
            }
            else if (opt.method.toUpperCase() === 'GET') {
                xmlHttp.open(opt.method, opt.url + '?' + postData, opt.async);
                xmlHttp.send(null);
            }
            xmlHttp.onreadystatechange = function () {
                if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                    opt.success(xmlHttp.responseText);
                }
                //隐藏遮罩
                $("#loadingModal").modal('hide');
            };
        }

    </script>
