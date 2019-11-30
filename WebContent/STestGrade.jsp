<%@ page language="java" contentType="text/html; charset=utf-8" import="com.ssm.entity.TestGrade,java.util.*"
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
                <li ><a href="OnlineTest.do?username=${username }">在线答卷</a></li>
                <li><a href="#">在线答题</a></li>
               <li ><a href="#">错题系统</a></li>
               <li><a href="InquiryTestGrade.do?username=${username }">成绩查询</a></li>
                 <li><a href="password.do?username=${username }">密码修改</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>试卷管理页面</span>
        </div>
        <div class="search">
        	<font size="3" color="red">*请留意开考的时间，考试时间为120分钟，请在规定的时间内完成答题</font>
        </div>
        <h2><p style="color : red">${hint }</p></h2>
        	
            <tr class="firstTr"><table  class="providerTable" cellpadding="0" cellspacing="0">
		 <tr class="firstTr">
                    <th width="15%">试卷编号</th>
                    <th width="20%">试卷名称</th>
          			<th width="15%">用户名</th>
                    <th width="15%">成绩</th>
                     <th width="15%">是否完成</th>
                    <th width="20%">提交时间</th>
                </tr>
                <c:forEach items="${list}" var="testgrade">
				    <tr>
						 <td>${testgrade.testid }</td>
						<td>${testgrade.testname }</td>
						<td>${testgrade.username }</td>
						 <td>${testgrade.grade }</td>
						 <td>${testgrade.flag }</td>
						 <td>${testgrade.submitdate }</td>
                    </tr>
                    
                    </c:forEach>
                    
		</table>
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="deleteSupplier.do?designation=${S.getDesignation() }" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>


<footer class="footer">
</footer>

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>

</body>
</html>