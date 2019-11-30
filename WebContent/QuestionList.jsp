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
                <span>题库管理页面</span>
            </div>
           
            <div class="search">
			<form action="LookLibrary.do" method="post">
                <span>题目：</span>
                <input type="text" id="title" name="title" placeholder="请输入题目" />
                <span>类型：</span>
				<select name="type">
					<option value="全部"selected>全部</option>
                    <option value="单选">单选</option>
                    <option value="多选">多选</option>
                </select>
                     <a href="QuestionAdd.jsp">添加试题</a>
                      <a href="QuestionImport.jsp">批量导入</a>
                <input style="width:70px"type="submit" value="查询"/>
                </form>
            </div>
            <div class="search">
			<form action="LookLibraryByDate.do" method="post">
			 <span>写入日期：</span>
               <input type="text" name="start" id="qwe" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" placeholder="请输入开始日期"/>
                <input type="text" name="ends" id="qwe"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"placeholder="请输入结束日期"/>
                
                <input style="width:70px"type="submit" value="查询"/>
                
                </form>
           		
            </div>
            <!--账单表格 样式和供应商公用-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">题目编号</th>
                    <th width="10%">题目类型</th>
                    <th width="30%">题目</th>
                    <th width="10%">分值</th>
                    <th width="10%">描述</th>
                    <th width="10%">写入时间</th>
                    <th width="20%">操作</th>
                </tr>
                 <h2><p style="color:red">${error }</p></h2>
                 <h2><p style="color:red">${error1 }</p></h2>
                 <h2><p style="color:blue">${hint }</p></h2>
                <c:forEach items="${list}" var="question">
				    <tr>
						 <td>${question.tid}</td>
						 <td>${question.type}</td>
						 <td>${question.title}</td>
						 <td>${question.score}</td>
						 <td>${question.description}</td>
						 <td>${question.date}</td>
	                    <td>
	        
	                        <a href="LookQuestion.do?tid=${question.getTid() }"><img src="img/read.png" alt="查看" title="查看"/></a>
	                        <c:if test="${Testid!=0 }">
	                        	 <a href="AddTest.do?tid=${question.tid }&page=${page }&currentpage=${currentpage }"><img src="img/tianjia.png" alt="添加试题" title="添加试题"/></a>
	                         </c:if>
	                         <a href="AlterQuestion.do?tid=${question.tid }"><img src="img/xiugai.png" alt="修改试题" title="修改试题"/></a>
	                        <a href="deleteQuestion.do?tid=${question.getTid() }"  onclick="return confirm('确认删除？！！')" ><img src="img/schu.png" alt="删除" title="删除"/></a>
	                    
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

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>

</body>
</html>