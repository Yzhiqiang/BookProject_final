<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>用户登录</h1>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        用户名：<input name="username" type="text"><br/>
        密 码：<input name="password" type="password"><br/>
        请输入验证码：<input type="text"><img src="" alt=""><br/>
        <a href="register.jsp">注册</a><br/>
        <input type="submit" value="登录">
    </form>
</body>
</html>