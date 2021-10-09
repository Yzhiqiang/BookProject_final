
<#include "/system/base/head.ftl">
<body>

<#--搜索-->
<div class="lenos-search">
    <div class="select">
        <@lenInclude path="/system/base/queryBox.ftl" name="操作用户" id="userName" ></@lenInclude>
        <@lenInclude path="/system/base/queryBox.ftl" name="操作类型" id="type" ></@lenInclude>
    </div>
    <#include "/system/base/searth.ftl">
</div>

<#--按钮-->
<div class="layui-col-md12">
    <div class="layui-btn-group">
        <@lenInclude path="/system/base/btn.ftl" hasPermission="control:del"
        type="del" name="删除" icon="&#xe640;"></@lenInclude>
    </div>
</div>

<#--表格-->
<table id="logList" width="100%" lay-filter="log"></table>

<#--toolbar-->
<script type="text/html" id="toolBar">
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="control:del"   name="删除" event="del"/>
</script>
<script>

    layui.use('table', function () {
        var table = layui.table;
        //方法级渲染
        table.render({
            id: 'logList',
            elem: '#logList'
            , url: 'showLogList'
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
                , {field: 'userName', title: '操作人', width: '10%', sort: true}
                , {field: 'type', title: '操作类型', width: '10%', sort: true}
                , {field: 'text', title: '描述内容', width: '10%', sort: true}
                , {field: 'param', title: '参数', width: '40%', sort: true}
                , {
                    title: '操作时间',
                    width: '15%',
                    templet: '<div>{{ moment(+d.createTime).format(\'YYYY-MM-DD HH:mm:ss\')  }}</div>'
                }
                , {field: 'text', title: '操作', width: '5%', toolbar: '#toolBar'}

            ]]
            , page: true
            , height: 'full-100'
        });

        var $ = layui.$, active = {
            select: function () {
                var userName = $('#userName').val();
                var type = $('#type').val();
                table.reload('logList', {
                    where: {
                        userName: userName,
                        type: type
                    }
                });
            }
            , del: function () {
                var checkStatus = table.checkStatus('logList')
                    , data = checkStatus.data;
                if (data.length === 0) {
                    layer.msg('请选择要删除的数据', {icon: 5});
                    return false;
                }
                var ids = [];
                for (item in data) {
                    ids.push(data[item].id);
                }
                del(ids);
            }
            , reload: function () {
                $('#userName').val('');
                $('#type').val('');
                table.reload('logList', {
                    where: {
                        userName: null,
                        type: null
                    }
                });
            },
        };
        //监听工具条
        table.on('tool(log)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                var ids = [];
                ids.push(data.id)
                del(ids);
            }
        });
        Len.formBind(active);

    });

    /**批量删除id*/
    function del(ids) {
        $.ajax({
            url: "del",
            type: "post",
            data: {ids: ids},
            dataType: "json", traditional: true,
            success: function (data) {
                layer.msg(data.msg, {icon: 6});
                layui.table.reload('logList');
            }
        });
    }
</script>
