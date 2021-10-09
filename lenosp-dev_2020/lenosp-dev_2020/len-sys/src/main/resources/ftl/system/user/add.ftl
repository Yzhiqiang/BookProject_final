<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <#include "/system/base/formHead.ftl">
</head>

<body>
<div class="x-body">
    <form id="useradd" class="layui-form layui-form-pane" style="margin-left: 20px;">
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
                        <img src="${re.contextPath}/plugin/x-admin/images/bg.png" width="100px" height="100px"
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
                    <input type="text" id="uname" name="username" lay-verify="username"
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
                        <input type="text" id="realName" name="realName" lay-verify="realName" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="age" class="layui-form-label">
                        <span class="x-red">*</span>年龄
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="age" name="age" lay-verify="number"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <#--密码-->
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="password" class="layui-form-label">
                        <span class="x-red">*</span>密码
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="password" name="password" lay-verify="password"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="L_repass" class="layui-form-label">
                        <span class="x-red">*</span>确认密码
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="l_repass" name="repass" lay-verify="repass"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <#--邮箱-->
            <div>
                <label for="email" class="layui-form-label">
                    <span class="x-red"></span>邮箱
                </label>
                <div class="layui-input-block">
                    <input type="email" id="email" style="width: 93%" name="email" lay-verify="email"
                           autocomplete="off" class="layui-input">
                    <input id="photo" name="photo" type="hidden">
                </div>
            </div>

            <#--角色-->
            <div class="layui-form-item">
                <label class="layui-form-label">角色选择</label>
                <div class="layui-input-block">
                    <#list boxJson as json>
                        <input type="checkbox" name="role" title="${json.name}" lay-filter="check" value="${json.id}">
                    </#list>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <@lenInclude path="system/base/formBtn.ftl"></@lenInclude>
    </form>
</div>
<script type="text/javascript" src="/plugin/validator/validator.min.js"></script>
<script>
    var flag, msg;
    new Validator('useradd', [
        {
            name: 'username',
            display: "用户名不能为空",
            rules: 'required'
        }, {
            name: 'realName',
            display: "真实姓名不能为空",
            rules: 'required'
        }
    ], function (obj) {
        if (obj.errors) {
            layer.msg(obj.errors[0].display, {icon: 5, anim: 6, offset: 't'});
        }
    });

    $(function () {
        $('#uname').on("blur", function () {
            var uname = $('#uname').val();
            if (uname.match(/[\u4e00-\u9fa5]/)) {
                return;
            }
            if (!/(.+){3,12}$/.test(uname)) {
                return;
            }
            if (!Len.isEmpty(uname)) {
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
            }
        });

    });
    layui.use(['form', 'layer', 'upload'], function () {
        $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer;

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
            Len.layerAjax('addUser', data.field, 'userList');
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
