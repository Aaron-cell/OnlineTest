<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 在线答题系统</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>在线答题系统</h1>
        </header>
      <h2><p style="color:blue">${news  }</p></h2>
        <section class="loginCont">
            <form class="loginForm" action="login.do">
              
                <div class="inputbox">
                    <label for="user">用户名：</label>
                    <input id="user" type="text" name="username" placeholder="请输入用户名" required value="${username==null?'':username }" />
                </div>
                <div class="inputbox">
                    <label for="mima">密码：</label>
                    <input id="mima" type="password" name="password" placeholder="请输入密码" required/>
                    <h3><p style="color:red">${error }</p></h3>
                </div>
                
                <div class="subBtn">
                    <input type="submit" value="登录"  />
                      <input type="reset" value="重置"/>
                </div>
                   <a href="register.jsp" >用户注册</a>
            </form>
        </section>
    </section>

</body>
</html>