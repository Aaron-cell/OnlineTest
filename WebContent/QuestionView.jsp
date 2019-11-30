<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>在线测试系统</title>
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>在线测试系统</h1>

    <div class="publicHeaderR">
        <p><span id="hours"></span>, 欢迎你！</p>
        <a href="index.jsp">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
    <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
</section>
<!--主体内容-->
<section class="publicMian ">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                           <li ><a href="QuestionManager.do?currentpage=0">题库管理</a></li>
               <li><a href="TestRecordManager.do">试卷管理</a></li>
                <li><a href="UserManager.do?currentpage=0">用户管理</a></li>
               <li><a href="GradeManager.do">成绩导出</a></li>
                <li><a href="password.jsp">密码修改</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>题库管理页面 >> 试题查看</span>
        </div>
        <div class="providerView">
            <p><strong>试题编号：</strong><span>${question.tid }</span></p>
            <p><strong>试题类型：</strong><span>${question.type }</span></p>
            <p><strong>题目：</strong><span>${question.title }</span></p>
            <p><strong>A选项：</strong><span>${question.optionA }</span></p>
            <p><strong>B选项：</strong><span>${question.optionB }</span></p>
            <p><strong>C选项：</strong><span>${question.optionC }</span></p>
            <p><strong>D选项：</strong><span>${question.optionD }</span></p>
            <p><strong>E选项：</strong><span>${question.optionE }</span></p>
            <p><strong>正确结果：</strong><span>${question.correct }</span></p>
            <p><strong>分值：</strong><span>${question.score }</span></p>
            <p><strong>描述：</strong><span>${question.description }</span></p>
            <p><strong>写入时间：</strong><span>${question.date }</span></p>

            <a href="#"onclick="history.back(-1)">返回</a>
        </div>
    </div>
</section>
<footer class="footer">
</footer>
<script src="js/time.js"></script>

</body>
</html>