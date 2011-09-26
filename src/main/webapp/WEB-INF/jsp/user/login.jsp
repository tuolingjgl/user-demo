<%--@elvariable id="model" type="org.f0rb.demo.dto.UserDTO"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE HTML>
<html>
<head lang=zh-CN>
    <!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
    <![endif]-->
    <meta charset=utf-8>
    <title>欢迎登录</title>
    <link href="/css/global.css" rel="stylesheet">
</head>
<body>
<form id="login" method="post">
    <h4>用户登录</h4>
    <p style="color:red;"><label></label>${model.messages.login[0]}</p>
    <p><label for="username-in">用户帐号</label><input id="username-in" name="username" type="text" autocomplete="off" required value="${model.username}"></p>
    <p><label for="password-in">密码</label><input id="password-in" name="password" type="password" required></p>
    <div><button type="submit">登录</button><a href="javascript:void(0);">忘记密码</a></div>
</form>
</body>
</html>