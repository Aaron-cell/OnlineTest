<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>在线答题系统</title>
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>在线答题系统</h1>

    <div class="publicHeaderR">
        <p><span id="hours"></span> , 欢迎你！</p>
        <a href="index.jsp">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
    <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
</section>
<!--主体内容-->
<section class="publicMian">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li ><a href="OnlineTest.do?username=${username }">在线答卷</a></li>
                <li><a href="#">在线答题</a></li>
               <li ><a href="#">错题系统</a></li>
               <li><a href="InquiryTestGrade.do?username=${username }">成绩查询</a></li>
                <li><a href="password.do?username=${username }">密码修改</a></li>
               
            </ul>
        </nav>
    </div>
    <div class="right">
        <img class="wColck" src="img/clock.jpg" alt=""/>
        <div class="wFont">
            <p>欢迎来到在线答题系统!</p>
            <h3>${username }</h3>
        </div>
    </div>
</section>
<footer class="footer"> 
</footer>
<script src="js/time.js"></script>
<div style="text-align:center;">
<p>更多模板：<a href="http://www.mycodes.net/" target="_blank">源码之家</a></p>
</div>
</body>
</html>