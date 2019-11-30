<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户登录>>用户注册</span>
        </div>
        <div class="providerAdd">
            <form action="AddUser.do">
                <!--div的class 为error是验证错误，ok是验证成功-->
                 <div class="inputbox">
                    <label for="user"><font size="5">用户名：</font></label>
                    <input id="user" type="text" name="username" placeholder="请输入用户名" required/>
                    <span>*请输入用户名</span>
                </div>
                <div class="inputbox">
                    <label for="mima"><font size="5">设置密码：</font></label>
                    <input id="mima" type="password" name="password" placeholder="请输入密码" required/>
                    <span>*密码长度必须大于6位小于20位</span>
                </div>
                <div class="inputbox">
                    <label for="mima"><font size="5">确认密码：</font></label>
                    <input id="mima" type="password" name="repassword" placeholder="请输入密码" required/>
                    <span>*请确认密码</span>
                </div>
                 <h2><p style="color:red">${hint }</p></h2>
                <div class="providerAddBtn">
                      <input  type="submit" value="注册" />
                </div>
            </form>
        </div>

    </div>
<footer class="footer">
</footer>
<script src="js/time.js"></script>

</body>
</html>