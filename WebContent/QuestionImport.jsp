<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <span>导入试题页面</span>
        </div>
		<br><br>
        <!--供应商操作表格-->
       <center><font size="4" color="blue"><a href="DownExcelTemplet.do">下载Excel模板</a></font></center>
       <br><br>
	<hr>
	<br><br>
	<center>
       <div>
       <!-- enctype默认情况下只能上传文本格式文件，multipart/form-date将文件以二进制文件上传，可支持多种类型文件传输 -->
    <form method="post" action="ExcelImport.do" enctype="multipart/form-data">
       <font size="4" color="blue"> <input type="file" id="excelFile" name="file"/></font>
       <font size="4" color="blue"> <button type="submit">开始导入</button></font>
    </form>
</div>
</center>
<br><br>
<hr>
  <center><font size="4" color="red">${hint }</font></center>     
     <center><font size="4" color="red">${error }</font></center>  
     <table border="1" align="center" width="40%">
     	<tr>
     		<td>失败id</td>
     		<td>失败原因</td>
     	</tr>
     	<c:forEach items="${list2}" var="error">
     		<tr>
     			<td>${error.id }</td>
     			<td>${error.hint }</td>
     		</tr>
     	</c:forEach>
     </table>   
   </div>
</section>



<footer class="footer">
</footer>

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>

</body>
</html>