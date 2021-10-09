<#include "/system/base/head.ftl">

<#--搜索-->
<div class="lenos-search">
    <div class="select">
        <@lenInclude path="/system/base/queryBox.ftl" name="任务名称" id="jobName" ></@lenInclude>
        <@lenInclude path="/system/base/queryBox.ftl" name="描述" id="remark" ></@lenInclude>
    </div>
    <#include "/system/base/searth.ftl">
</div>

<#--按钮-->
<div class="layui-col-md12">
    <div class="layui-btn-group">
        <@lenInclude path="/system/base/btn.ftl" hasPermission="job:add"
        type="add" name="新增" icon="&#xe608;"></@lenInclude>
        <@lenInclude path="/system/base/btn.ftl" hasPermission="job:update"
        type="update" name="编辑" icon="&#xe642;"></@lenInclude>
        <@lenInclude path="/system/base/btn.ftl" hasPermission="job:select"
        type="detail" name="查看" icon="&#xe605;"></@lenInclude>
    </div>
</div>
<#--表格-->
<table id="jobList" width="100%" lay-filter="job"></table>

<script type="text/html" id="toolBar">
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="job:add"  name="查看" event="detail"/>
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="job:update" name="编辑" event="edit"/>
    {{#  if(!d.status){ }}
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="job:start" name="启动" event="start"/>
    {{#  } }}
    {{# if(d.status){ }}
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="job:end"   name="停止" event="end"/>
    {{#  } }}
    <@lenInclude  path="/system/base/bar.ftl" hasPermission="job:del"   name="删除" event="del"/>
</script>
<script>

    layui.use('table', function () {
        table = layui.table;
        //方法级渲染
        table.render({
            id: 'jobList',
            elem: '#jobList'
            , url: 'showJobList'
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
                , {field: 'jobName', title: '任务名称', width: '10%', sort: true}
                , {field: 'cron', title: '表达式', width: '10%'}
                , {field: 'clazzPath', title: '任务类', width: '20%', sort: true}
                , {field: 'status', title: '状态', width: '10%', sort: true}
                , {field: 'jobDesc', title: '任务描述', width: '10%'}
                , {
                    title: '创建时间',
                    width: '10%',
                    templet: '<div>{{ moment(+d.createDate).format(\'YYYY-MM-DD\') }}</div>'
                }
                , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]]
            , page: true
            , height: 'full-100'
        });

        var $ = layui.$, active = {
            select: function () {
                var jobName = $('#jobName').val();
                var remark = $('#remark').val();
                table.reload('jobList', {
                    where: {
                        jobName: jobName,
                        jobDesc: remark
                    }
                });
            },
            reload: function () {
                $('#jobName').val('');
                $('#remark').val('');
                table.reload('jobList', {
                    where: {
                        jobName: null,
                        jobDesc: null
                    }
                });
            },
            add: function () {
                Len.add('/job/showAddJob', '添加任务');
            },
            update: function () {
                var data = table.checkStatus('jobList').data;
                if (Len.onlyOne(data.length)) {
                    if (data[0].status) {
                        layer.msg('已经启动任务无法更新,请停止后更新', {icon: 5, offset: 'rb', area: ['200px', '100px'], anim: 2});
                        return false;
                    }
                    Len.update('/job/updateJob?id=' + data[0].id, '编辑任务');
                }
            },
            detail: function () {
                var data = table.checkStatus('jobList').data;
                Len.onlyOne(data.length) ?
                    Len.detail('/job/updateJob?id=' + data[0].id, '查看任务信息') : false;
            }
        };
        //监听工具条
        table.on('tool(job)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case "detail":
                    Len.detail('/job/updateJob?id=' + data.id, '编辑角色');
                    break;
                case "del":
                    if (!data.status) {
                        Len.delete('del', data.id, '', 'jobList');
                    } else {
                        layer.msg('已经启动任务无法更新,请停止后删除', {icon: 5, offset: 'rb', area: ['200px', '100px'], anim: 2});
                    }
                    break;
                case "edit":
                    if (!data.status) {
                        Len.update('/job/updateJob?id=' + data.id, '编辑任务');
                    } else {
                        layer.msg('已经启动任务无法更新,请停止后更新', {icon: 5, offset: 'rb', area: ['200px', '100px'], anim: 2});
                    }
                    break;
                case "start":
                    layer.confirm('确定开启任务[<label style="color: #00AA91;">' + data.jobName + '</label>]?', function () {
                        Len.ajaxPost('startJob', {id: data.id}, function (res) {
                            Len.rbSuccess(res.msg);
                            layui.table.reload('jobList');
                        });
                    });
                    break;
                case "end":
                    layer.confirm('确定停止任务[<label style="color: #00AA91;">' + data.jobName + '</label>]?', function () {
                        Len.ajaxPost('endJob', {id: data.id}, function (res) {
                            Len.rbSuccess(res.msg);
                            layui.table.reload('jobList');
                        });
                    });
                    break;
            }
        });
        Len.btnBind(active);
        Len.keydown(active);
    });
</script>
