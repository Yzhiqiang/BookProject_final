<#--表格 toolbar 模板-->

<#--description
定义默认class
查看：layui-btn-primary
编辑：layui-btn-normal
删除：layui-btn-danger
未定义：layui-btn-normal-->

<#assign notExistsClass=class==''/>
<#if name=="查看"&&notExistsClass>
    <#assign currentClass='layui-btn-primary'/>
<#elseif name=="编辑"&&notExistsClass>
    <#assign currentClass='layui-btn-normal'/>
<#elseif name=="删除"&&notExistsClass>
    <#assign currentClass='layui-btn-danger'/>
<#elseif notExistsClass>
    <#assign currentClass='layui-btn-normal'/>
<#else >
    <#assign currentClass=class/>
</#if>

<#if hasPermission!='' >
    <@shiro.hasPermission name="${hasPermission}">
        <a class="layui-btn layui-btn-xs ${currentClass}"
           lay-event="${event}">${name}</a>
    </@shiro.hasPermission>
<#else >
    <a class="layui-btn layui-btn-xs ${currentClass}"
       lay-event="${event}">${name}1</a>
</#if>

<#--置空变量 防止覆盖后面的引用-->
<#assign currentClass=''>
<#assign notExistsClass=''>
<#assign hasPermission=''>
<#assign class=''>
<#assign event=''>
<#assign name=''>