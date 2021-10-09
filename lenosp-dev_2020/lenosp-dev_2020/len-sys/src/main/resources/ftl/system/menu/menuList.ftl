<#--公共文件-->
<#include "/system/base/head.ftl">

<#--搜索-->
<div class="lenos-search">
    <div class="select">
        <@lenInclude path="/system/base/queryBox.ftl" name="菜单名称" id="name" ></@lenInclude>
    </div>
    <#include "/system/base/searth.ftl">
</div>
<hr class="layui-bg-gray">
<#--按钮-->
<div class="layui-btn-container">
    <div class="layui-btn-group">
        <@lenInclude path="/system/base/btn.ftl" type="add" name="新增" icon="&#xe608;"></@lenInclude>
    </div>
</div>
<#--表格-->
<table id="treeList" width="100%"></table>
<!-- 表格操作列 -->
<script type="text/html" id="tbBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/javascript">
    /*加载treetable 模块*/
    layui.config({
        base: '${re.contextPath}/plugin/layui/modules/'
    }).extend({
        treeTable: 'treeTable'
    });
    /*渲染tree table*/
    layui.use(['treeTable'], function () {
        var $ = layui.jquery;
        var treeTable = layui.treeTable;
        // $('body').removeClass('layui-hide');

        // 渲染表格
        table = treeTable.render({
            elem: '#treeList',
            url: 'showMenuList',
            tree: {
                iconIndex: 2,
                isPidData: true,
                idName: 'id',
                pidName: 'pid'
            },
            cols: [[
                {type: 'numbers'},
                {type: 'checkbox'},
                {field: 'name', title: '菜单名称', minWidth: 165},
                {
                    title: '图标',
                    templet: function (d) {
                        if (typeof (d.icon) != "undefined") {
                            return '<i class="layui-icon">' + d.icon + '</i>';
                        }
                    },
                },
                {title: '类型', templet: '<p>{{d.menuType=="1"?"按钮":"菜单"}}</p>', align: 'center', width: 60},
                {field: 'url', title: '菜单地址'},
                {field: 'permission', title: '权限标识'},
                {title: '创建时间', templet: '<div>{{ moment(+d.createDate).format(\'YYYY-MM-DD\') }}</div>'},
                {align: 'center', toolbar: '#tbBar', title: '操作', width: 120}
            ]],
            style: 'margin-top:0;'
        });
        var active = {

            /*查询*/
            select: function () {
                table.reload({
                    where: {
                        name: $('#name').val(),
                    }
                });
            },
            /*重置*/
            reload: function () {
                $('#name').val('');
                table.reload({
                    where: {
                        name: '',
                    }
                });
            },

            /*添加*/
            add: function () {
                Len.add('menu/showAddMenu');
            },
        };

        treeTable.on('tool(treeList)', function (obj) {
            var event = obj.event, data = obj.data;
            switch (event) {
                case "edit":
                    Len.update('menu/showUpdateMenu?id=' + data.id);
                    break;
                case "del":
                    Len.delete('del', data.id, function () {
                        Len.rbSuccess("删除成功");
                        active.reload();
                    });
                    break;
            }
        });
        Len.btnBind(active);
        Len.keydown(active);
    });
</script>
