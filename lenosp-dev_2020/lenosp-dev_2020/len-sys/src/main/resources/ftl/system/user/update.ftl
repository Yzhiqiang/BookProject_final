<#include "/system/base/formHead.ftl">
<script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:400px;overflow: auto;">

            <#--头像-->
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">头像上传</legend>
                </fieldset>

                <#--上传组件-->
                <@lenInclude path="/system/base/upload.ftl" id="uploadFile"></@lenInclude>

                <div class="layui-input-inline">
                    <div id="showImage" style="margin-top: 20px;margin-left: 50px">
                        <img src="/images/${re.contextPath}/${user.photo}" width="100px" height="100px"
                             class="layui-upload-img layui-circle">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>

            <#--用户名-->
            <div class="layui-form-item">
                <label for="uname" class="layui-form-label">
                    <span class="x-red">*</span>用户名
                </label>
                <div class="layui-input-inline">
                    <input value="${user.id}" type="hidden" name="id">
                    <input type="text" id="uname" value="${user.username}" readonly lay-verify="username"
                           autocomplete="off" class="layui-input">
                </div>
                <div id="ms" class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span><span id="ums">将会成为您唯一的登入名</span>
                </div>
            </div>

            <#--真实姓名-->
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="realName" class="layui-form-label">
                        <span class="x-red">*</span>真实姓名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="realName" value="${user.realName}" name="realName" lay-verify="realName"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="age" class="layui-form-label">
                        <span class="x-red">*</span>年龄
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="age" name="age" value="${user.age}" lay-verify="number"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <#--邮箱-->
            <div class="layui-form-item">
                <label for="email" class="layui-form-label">
                    <span class="x-red"></span>邮箱
                </label>
                <div class="layui-input-block">
                    <input type="email" id="email" value="${user.email}" style="width: 93%" name="email"
                           lay-verify="email"
                           autocomplete="off" class="layui-input">
                    <input id="photo" value="${user.photo}" name="photo" type="hidden">
                </div>
            </div>

            <#--角色-->
            <div class="layui-form-item">
                <label class="layui-form-label">角色选择</label>
                <div class="layui-input-block">
                    <#list boxJson as json>
                        <input type="checkbox" name="role" lay-filter="check" value="${json.id}" title="${json.name}"
                               <#if json.check?string=='true'>checked</#if>>
                    </#list>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <#if !detail>
           <@lenInclude path="system/base/formBtn.ftl"></@lenInclude>
        </#if>
    </form>
</div>
<script>
    var flag, msg;
    $(function () {
        var name = '${user.username}';
        if ($('#uname').val() === name)
            flag = true;
        $('#uname').on("blur", function () {
            var uname = $('#uname').val();
            if (uname.match(/[\u4e00-\u9fa5]/)) {
                return;
            }
            if (!/(.+){3,12}$/.test(uname)) {
                return;
            }
            if (uname != '' && uname != name) {
                $.ajax({
                    url: 'checkUser?uname=' + uname, async: false, type: 'get', success: function (data) {
                        flag = data.flag;
                        $('#ms').find('span').remove();
                        if (!data.flag) {
                            msg = data.msg;
                            $('#ms').append("<span style='color: red;'>" + data.msg + "</span>");
                        } else {
                            flag = true;
                            $('#ms').append("<span style='color: green;'>用户名可用</span>");
                        }
                    }, beforeSend: function () {
                        $('#ms').find('span').remove();
                        $('#ms').append("<span>验证ing</span>");
                    }
                });
            } else {
                flag = true;
            }
        });

    });
    layui.use(['form', 'layer', 'upload'], function () {
        $ = layui.jquery;
        var form = layui.form;


        $('#uploadFile').uploadFile({
            before: function (obj) {
                obj.preview(function (index, file, result) {
                    var imgObj = $('#showImage');
                    imgObj.find('img').remove();
                    imgObj.append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img layui-circle">');
                });
            }, done: function (res) {
                $("#photo").val(res.msg);
            }
        });

        //自定义验证规则
        form.verify({
            username: function (value) {
                if (value.trim() === "") {
                    return "用户名不能为空";
                }
                if (value.match(/[\u4e00-\u9fa5]/)) {
                    return "用户名不能为中文";
                }
                if (!/(.+){3,12}$/.test(value)) {
                    return "用户名必须3到12位";
                }
                if (typeof (flag) == 'undefined') {
                    return "用户名验证ing";
                }
                if (!flag) {
                    return msg;
                }
            }
            , email: function (value) {
                if (value !== "") {
                    if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)) {
                        return "邮箱格式不正确";
                    }
                }
            }
        });

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        //监听提交
        form.on('submit(add)', function (data) {
            var r = document.getElementsByName("role");
            var role = [];
            for (var i = 0; i < r.length; i++) {
                if (r[i].checked) {
                    role.push(r[i].value);
                }
            }
            data.field.role = role;
            Len.layerAjax('updateUser', data.field, 'userList');
            return false;
        });
        form.render();
    });
</script>
