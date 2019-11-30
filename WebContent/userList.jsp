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
                <span>用户管理页面</span>
            </div>
             <form action="inquiryUser.do" method="post">
            <div class="search">
                <span>用户名：</span>
                <input type="text" id="username" name="username" placeholder="请输入用户名" required/>
                <input style="width: 70px " type="submit" value="查询"/>
                      </form>
                <a href="userAdd.jsp">添加用户</a>
            </div>
      
            <h2><p style="color : red">${error }</p></h2>
            <!--用户-->
            <table  class="providerTable" cellpadding="0" cellspacing="0">
		 <tr class="firstTr">
                    <th width="20%">用户名</th>
                    <th width="10%">类型</th>
                    <th width="30%">注册时间</th>
                    <th width="40%">操作</th>
          </tr>
			<c:forEach items="${list}" var="user">
				    <tr>
						 <td>${user.username}</td>
						 <td>${user.type}</td>
						 <td>${user.redate}</td>
	                    <td>
	                        <a href="LookUser.do?username=${user.getUsername() }"><img src="img/read.png" alt="查看" title="查看"/></a>
	                        <a href="deleteUser.do?username=${user.getUsername() }" onclick="return confirm('确认删除？！！')"><img src="img/schu.png" alt="删除" title="删除"/></a>
	                    </td>
                    </tr>
                    </c:forEach>
	</table>
		
		<a href="${page }currentpage=0" ><font size="5" color="blue">首页</font></a>
		<a href="${page }currentpage=${currentpage>0?currentpage-1:0 }" ><font size="5" color="blue">上一页</font></a>
		<font size="5" color="blue">第${currentpage+1 }页</font>
		<a href="${page }currentpage=${currentpage+1 }" ><font size="5" color="blue">下一页</font></a>
		<a href="${page }currentpage=${pagecount }" ><font size="5" color="blue">末页</font></a>
        </div>
    </section>


    <footer class="footer">
    </footer>

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>

</body>
</html>