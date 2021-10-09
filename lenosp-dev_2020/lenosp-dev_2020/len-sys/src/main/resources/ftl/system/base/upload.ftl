<#--文件上传-->

<#--description
使用方法
$("#id").uploadFile({
    before:function(res){

    },
    done:function(res){

    }
});
-->

<div class="layui-input-inline">
    <div class="layui-upload-drag" style="margin-left:10%;" id="${id}">
        <i style="font-size:30px;" class="layui-icon"></i>
        <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
    </div>
</div>
<script>
    (function () {
        var uploadFile = function (id, opt) {
            this.defaults = {
                elem: $(id)
                , url: 'upload'
            };
            this.options = $.extend({}, this.defaults, opt);
        };

        uploadFile.prototype = {
            init: function () {
                var _this = this;
                return layui.use('upload', function () {
                    var upload = layui.upload;
                    var uploadInst = upload.render(_this.options);
                });
            }
        };

        $.fn.uploadFile = function (options) {
            var file = new uploadFile(this, options);
            return file.init();
        }
    })();
</script>
<#assign id=''>

<#--使用方法


-->


