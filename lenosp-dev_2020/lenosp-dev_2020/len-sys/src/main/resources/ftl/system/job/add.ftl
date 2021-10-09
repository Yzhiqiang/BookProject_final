<#include "/system/base/head.ftl">

<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:400px;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">任务信息</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <label for="jobName" class="layui-form-label">
                    <span class="x-red">*</span>任务名称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="jobName" name="jobName" lay-verify="jobName"
                           autocomplete="off" class="layui-input">
                </div>
                <div id="ms" class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span><span id="ums">名称必填</span>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="cron" class="layui-form-label">
                        <span class="x-red">*</span>表达式
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="cron" name="cron" lay-verify="cron" autocomplete="off"
                               class="layui-input">
                    </div>
                    <div id="ms" class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span><a href="http://cron.qqe2.com/" target="_blank">获取表达式</a>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">执行类要实现Job</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="clazzPath" class="layui-form-label">
                        <span class="x-red">*</span>任务执行类
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="clazzPath" style="width: 300px;" name="clazzPath" lay-verify="clazzPath"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="jobDesc" class="layui-form-label">
                        <span class="x-red">*</span>任务描述
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="jobDesc" name="jobDesc" lay-verify="jobDesc" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <#include "/system/base/formBtn.ftl">
    </form>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer;

        //自定义验证规则
        form.verify({
            jobName: function (value) {
                if (Len.isEmpty(value)) {
                    return "任务名称不能为空";
                }
            },
            cron: function (value) {
                if (Len.isEmpty(value)) {
                    return "表达式不能为空";
                }
            },
            clazzPath: function (value) {
                if (Len.isEmpty(value)) {
                    return "执行类不能为空";
                }
            }
        });

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        //监听提交
        form.on('submit(add)', function (data) {
            Len.layerAjax('addJob', data.field, 'jobList');
            return false;
        });
    });
</script>