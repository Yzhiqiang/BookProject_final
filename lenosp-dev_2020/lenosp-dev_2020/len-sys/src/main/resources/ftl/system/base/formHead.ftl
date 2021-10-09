<#--表单头引用-->

<#--description
form表单 引用
-->
<#include "system/base/head.ftl">
<script type="text/javascript" src="${re.contextPath}/plugin/validator/validator.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if ('${detail}') {
            $("form").disable();
        }
    });
</script>