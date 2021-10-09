<#--查询文本框-->

<#--description
-->

<div>
    <span>${name}：</span>
    <span class="layui-inline">
        <input class="layui-input" height="20px" id="${id}" autocomplete="off">
    </span>
</div>

<#--置空变量 防止覆盖后面的引用-->
<#assign name=''>
<#assign id=''>