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
 <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
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
                <span>试题修改页面</span>
            </div>
           
            <div class="search">
           
            </div>
            <!--账单表格 样式和供应商公用-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                	<th width="10%">试卷编号</th>
                    <th width="10%">题目编号</th>
                    <th width="10%">题目类型</th>
                    <th width="30%">题目</th>
                    <th width="10%">分值</th>
                    <th width="10%">描述</th>
                    <th width="10%">写入时间</th>
                    <th width="10%">操作</th>
                </tr>
                 <h2><p style="color:red">${error }</p></h2>
                 <h2><p style="color:blue">${hint }</p></h2>
                 <%
                 	int i=1;
          
                 %>
                <c:forEach items="${list}" var="question">
				    <tr>
				    	<td>${record=record+1 }</td>
						 <td>${question.tid}</td>
						 <td>${question.type}</td>
						 <td>${question.title}</td>
						 <td>${question.score}</td>
						 <td>${question.description}</td>
						 <td>${question.date}</td>
	                    <td>
	        
	                        <a href="LookQuestion.do?tid=${question.getTid() }"><img src="img/read.png" alt="查看" title="查看"/></a>	
	                        <c:if test="${flag }">
	                       		 <a href="deleteTest.do?tid=${question.getTid() }&id=${id }" onclick="return confirm('确认删除？！！')" ><img src="img/schu.png" alt="删除" title="删除"/></a>
	                        </c:if>                    
	                    </td>
                    </tr>
                    </c:forEach>
            
            </table>
            <a href="${page }id=${id }&currentpage=0" ><font size="5" color="blue">首页</font></a>
		<a href="${page }id=${id }&currentpage=${currentpage>0?currentpage-1:0 }" ><font size="5" color="blue">上一页</font></a>
		<font size="5" color="blue">第${currentpage+1 }页</font>
		<a href="${page }id=${id }&currentpage=${currentpage+1 }" ><font size="5" color="blue">下一页</font></a>
		<a href="${page }id=${id }&currentpage=${pagecount }" ><font size="5" color="blue">末页</font></a>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
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