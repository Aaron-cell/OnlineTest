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
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>在线测试系统</h1>

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
            <span>供应商管理页面 >> 供应商添加页面</span>
        </div>
        <div class="providerAdd">
        	 <h2><p style="color:red">${error }</p></h2>
            <form action="AddTestRecord.do">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="testname">试卷名称</label>
                    <input type="text" name="testname" id="testname" placeholder="请输入试卷名称"  value="${testname==null?'':testname }" required/>
                    <span>*请输试卷名称</span>
                </div>
                <div>
                    <label for="description">试卷描述：</label>
                    <input type="text" name="description" id="description" placeholder="请输入试卷描述"  value="${description==null?'':description }" />
                    <span >*请输入试卷描述</span>
                </div>
                <div>
                    <label for="start">试卷开始时间：</label>
                    <input type="text" name="start" id="qwe" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" placeholder="请输入试卷开始日期" required/>
                    <span>*试卷开始时间</span>
                </div>
                <div>
                    <label for="ends">试卷结束时间：</label>
                    <input type="text" name="ends" id="qwe"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"placeholder="请输入结束日期" required/>
                    <span>*请输入试卷结束时间</span>
                </div>
                   <div class="providerAddBtn">
          
                    <input sytle="width:70px" type="submit" value="保存"/>
                  
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