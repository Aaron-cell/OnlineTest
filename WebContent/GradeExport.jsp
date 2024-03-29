﻿<%@ page language="java" contentType="text/html; charset=utf-8"
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
            <span>成绩导出页面</span>
        </div>
     
        <h2><p style="color : red">${hint }</p></h2>

        <!--供应商操作表格-->
        	
            <tr class="firstTr"><table  class="providerTable" cellpadding="0" cellspacing="0">
		 <tr class="firstTr">
                    <th width="10%">试卷编号</th>
                    <th width="15%">试卷名称</th>
                    <th width="15%">试卷描述</th>
                    <th width="15%">开始时间</th>
                    <th width="15%">结束时间</th>
                    <th width="10%">是否发布</th>
                    <th width="10%">总分值</th>
                    <th width="10%">操作</th>
                </tr>
                <c:forEach items="${list}" var="testrecord">
				    <tr>
						 <td>${testrecord.testid }</td>
						<td>${testrecord.testname }</td>
						 <td>${testrecord.description }</td>
						 <td>${testrecord.start }</td>
						 <td>${testrecord.ends }</td>
						 <td>${testrecord.publish }</td>
						 <td>${testrecord.testscore }</td>
	                    <td>
		           
	                        <a href="ExportExcel.do?testid=${testrecord.testid }&testname=${testrecord.testname }"><img src="img/xiugai.png" alt="成绩导出" title="成绩导出"/></a>
	                       
	                    </td>
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