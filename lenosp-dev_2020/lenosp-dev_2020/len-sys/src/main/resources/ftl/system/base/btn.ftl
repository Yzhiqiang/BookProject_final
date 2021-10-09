<#--表格按钮-->

<#--description
-->

<#assign permission=hasPermission!=''/>
<#if permission >
    <@shiro.hasPermission name="${hasPermission}">
        <button class="layui-btn layui-btn-normal  layui-btn-sm" data-type="${type}">
            <i class="layui-icon">${icon}</i>${name}
        </button>
    </@shiro.hasPermission>
<#else >
    <button class="layui-btn layui-btn-normal  layui-btn-sm" data-type="${type}">
        <i class="layui-icon">${icon}</i>${name}
    </button>
</#if>

<#--置空变量 防止覆盖后面的引用-->
<#assign hasPermission=''>
<#assign permission=''>
<#assign type=''>
<#assign icon=''>