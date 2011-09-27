<%--@elvariable id="model" type="org.f0rb.demo.dto.UserDTO"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE HTML>
<html lang=zh-CN>
<head>
    <!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
    <![endif]-->
    <meta charset=utf-8>
    <title>用户注册</title>
    <link href="/css/global.css" rel="stylesheet">
</head>
<body>
<form id="register" method="post">
    <h4>用户注册</h4>

    <p>
        <label for="username-in">账号</label><input id="username-in" name="username" required type="email" placeholder='请输入有效的Email地址'><span>${model.messages.username[0]}</span>
    </p>

    <p>
        <label for="password-in">密码</label><input id="newpass-in" name="newpass" required type="password" placeholder="密码长度为6-25位"><span>${model.messages.newpass[0]}</span>
    </p>

    <p>
        <label for="confpass-in">确认密码</label><input id="confpass-in" name="confpass" required type="password" placeholder="请再次输入密码"><span>${model.messages.confpass[0]}</span>
    </p>

    <p>
        <label for="nickname-in">昵称</label><input id="nickname-in" name="nickname" required="required" type="text" placeholder="用户名应为2-20个字节"><span>${model.messages.nickname[0]}</span>
    </p>

    <div>
        <button type="submit">提交</button><button type="reset">重填</button>
    </div>
</form>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
    addCheckFiled("username");
    addCheckFiled("newpass");
    addCheckFiled("nickname");
    function addCheckFiled(fieldname) {
        /*$("#username-in").blur(function() {
         var elem = this;
         if (!elem.value || elem.value.trim() == "") return;
         var val = elem.value.trim();
         $.get("check-username", {
         "username": val
         }, function(data) {
         if (data.messages) {
         elem.nextSibling.innerHTML = data.messages.username;
         }
         })
         }) */
        $("#" + fieldname + "-in").blur(function() {
            var elem = this;
            if (!elem.value || elem.value.trim() == "") return;
            var val = elem.value.trim();
            var param = {};
            param[fieldname] = val;
            $.get("check-" + fieldname, param, function(data) {
                if (data.messages) {
                    elem.nextSibling.innerHTML = data.messages[fieldname];
                }
            })
        })
    }

    $("#confpass-in").blur(function() {
        if (!this.value) return;
        var confpass = this.value;
        var newpass = $("#newpass-in")[0].value;
        if (confpass != newpass) {
            this.nextSibling.innerHTML = "密码不匹配";
        } else {
            this.nextSibling.innerHTML = "";
        }
    })
})
</script>
</body>
</html>