<#include "/system/base/head.ftl">

<#--搜索-->
<div class="lenos-search">
    <div class="select">
        <@lenInclude path="/system/base/queryBox.ftl" name="用户名" id="uname" ></@lenInclude>
        <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
    </div>
    <#include "/system/base/searth.ftl">
</div>

<#--按钮-->
<div class="layui-col-md12 len-button">
    <div class="layui-btn-group">
        <@lenInclude path="/system/base/btn.ftl" hasPermission="user:select"
        type="add" name="新增" icon="&#xe608;"></@lenInclude>
        <@lenInclude path="/system/base/btn.ftl" hasPermission="user:select"
        type="update" name="编辑" icon="&#xe642;"></@lenInclude>
        <@lenInclude path="/system/base/btn.ftl" hasPermission="user:del"
        type="detail" name="查看" icon="&#xe605;"></@lenInclude>
        <@lenInclude path="/system/base/btn.ftl" hasPermission="user:repass"
        type="changePwd" name="修改密码" icon="&#xe605;"></@lenInclude>
    </div>
</div>

<#--表格-->
<table id="userList" width="100%" lay-filter="user"></table>

<#--toobar-->
<script type="text/html" id="bar">
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="user:select"   name="查看" event="detail"/>
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="user:update"  name="编辑" event="edit"/>
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="user:del" name="删除" event="del"/>
</script>
<script type="text/html" id="switchTpl">
    <input type="checkbox" name="sex" lay-skin="switch" lay-text="女|男" lay-filter="sexDemo">
</script>


<script>
    layui.use('table', function () {
        table = layui.table;
        //方法级渲染
        table.render({
            id: 'userList',
            elem: '#userList'
            , url: 'showUserList'
            , parseData: function (res) {
                return {
                    "code": res.code,
                    "msg": res.msg,
                    "count": res.count,
                    "data": res.data
                };
            }
            , cols: [[
                {checkbox: true, fixed: true, width: '5%'}
                , {field: 'username', title: '用户名', width: '10%', sort: true,}
                , {field: 'age', title: '年龄', width: '17%', sort: true}
                , {field: 'realName', title: '真实姓名', width: '20%'}
                , {field: 'email', title: '邮箱', width: '13%'}
                , {field: 'right', title: '操作', width: '20%', toolbar: "#bar"}
            ]]
            , page: true,
            height: 'full-100'
        });

        var $ = layui.$, active = {
            data: function () {
                return table.checkStatus('userList').data;
            },
            /*查询*/
            select: function () {
                table.reload('userList', {
                    where: {
                        username: $('#uname').val(),
                        email: $('#email').val()
                    }
                });
            },
            /*重置*/
            reload: function () {
                $('#uname').val('');
                $('#email').val('');
                table.reload('userList', {
                    where: {
                        username: null,
                        email: null
                    }
                });
            },
            /*添加*/
            add: function () {
                Len.add('/user/showAddUser', '添加用户');
            },
            /*更新*/
            update: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.update('/user/updateUser?id=' + data[0].id, '编辑用户',) : false;
            },
            /*详情*/
            detail: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.detail('/user/updateUser?id=' + data[0].id, '查看用户信息',) : false;
            },
            /*重置密码*/
            changePwd: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.popup('/user/goRePass?id=' + data[0].id, '修改密码', 500, 350) : false;

            }
        };

        /*监听表格复选框选择*/
        table.on('checkbox(user)', function (obj) {
        });

        /*监听工具条*/
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case "detail":
                    Len.detail('/user/updateUser?id=' + data.id, '编辑用户',);
                    break;
                case "edit":
                    Len.update('/user/updateUser?id=' + data.id);
                    break;
                case "del":
                    layer.confirm('确定删除用户[<label style="color: #00AA91;">' + data.username + '</label>]?', {
                        btn: ['逻辑删除', '物理删除']
                    }, function () {
                        Len.ajaxPost('del', {id: data.id, realDel: false}, function () {
                            Len.success("删除成功");
                            layui.table.reload('userList');
                        }, data.username);
                    }, function () {
                        Len.ajaxPost('del', {id: data.id, realDel: true}, function () {
                            Len.success("删除成功");
                            layui.table.reload('userList');
                        }, data.username);
                    });
            }
        });
        Len.btnBind(active);
        Len.keydown(active);
    });
</script>

