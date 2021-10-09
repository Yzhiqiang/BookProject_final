<#--表单按钮-->

<#--description
确定/取消按钮
-->

<#if !detail>
    <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
      position: fixed;bottom: 1px;margin-left:-20px;">
        <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
            <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
                确定
            </button>
            <button class="layui-btn layui-btn-primary" id="close">
                取消
            </button>

        </div>
    </div>
</#if>
<#assign detail=''>