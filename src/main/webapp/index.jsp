<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang=zh-CN>
<body>
<h2>Hello World!</h2>

<div>
    <% if (session.getAttribute("userdto") == null) {%>
    <p>
        <a href="user/login">登录</a>
        <a href="user/register">注册</a>
    </p>
    <% } else {%>
    <p>
        Welcome, <a href="javascript:void(0);">${sessionScope.userdto.nickname}</a>!
        <a href="user/logout">登出</a>
    </p>
    <% } %>
</div>
</body>
</html>
