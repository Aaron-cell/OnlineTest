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
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
        <h2><p style="color:red">${hint }</p></h2>
            <form action="UserAdd.do">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="userId">用户名：</label>
                    <input type="text" name="username" id="username"/>
                    <span>*请输入用户名，且不能重复</span>
                </div>
                <div>
                    <label for="userName">密码：</label>
                    <input type="password" name="password" id="password"/>
                    <span >*请输入用户密码</span>
                </div>
                <div>
                    <label for="userRemi">确认密码：</label>
                    <input type="password" name="repassword" id="repassword"/>
                    <span>*请输入确认密码</span>
                </div>
                <div>
                    <label >用户类别：</label>
                    <select name="type">
                        <option value="admin">管理员</option>
                        <option value="counterman" selected>业务员</option>
                    </select>
                </div>
                <div class="providerAddBtn">
                  
                      <input  type="submit" value="保存" />
                </div>
            </form>
        </div>

    </div>
</section>
<footer class="footer">
</footer>
<script src="js/time.js"></script>

</body>
</html>