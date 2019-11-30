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
            <span>账单管理页面 >> 订单添加页面</span>
        </div>
        <div class="providerAdd">
            <form action="AddQuestion.do">
                <!--div的class 为error是验证错误，ok是验证成功-->
                     <h2><p style="color : red">${errors }</p></h2>
                <div class="">
                    <label for="title">题目：</label>
                    <input type="text" name="title" id="title"  placeholder="请输入题目"  value="${title==null?'':title }"required />
                    <span>*请输入题目</span>
                </div>
                <div>
                    <label >题目类型：</label>
                    <input type="radio" name="type" value="单选" ${type1==null?'checked':type1 }/>单选
                    <input type="radio" name="type" value="多选" ${type2==null?'':type2 }/>多选
                    <span>*请选择试题类型</span>
                </div>
                <div>
                    <label for="optionA">A选项：</label>
                    <input type="text" name="optionA" id="optionA"   placeholder="请输入A选项答案"  value="${optionA==null?'':optionA }" required/>
                    <span >*请输入A选项</span>
                </div>
                <div>
                    <label for="optionB">B选项：</label>
                    <input type="text" name="optionB" id="optionB"   placeholder="请输入B选项答案" value="${optionB==null?'':optionB }" required/>
                    <span >*请输入B选项</span>
                </div>
                <div>
                    <label for="optionC">C选项：</label>
                    <input type="text" name="optionC" id="optionC"   placeholder="请输入C选项答案"  value="${optionC==null?'':optionC }"  required/>
                    <span >*请输入C选项</span>
                </div>
                <div>
                    <label for="optionD">D选项：</label>
                    <input type="text" name="optionD" id="optionD"   placeholder="请输入D选项答案" value="${optionD==null?'':optionD }"  required/>
                    <span >*请输入D选项</span>
                </div>
                <div>
                    <label for="optionE">E选项：</label>
                    <input type="text" name="optionE" id="optionE"   placeholder="请输入E选项答案" value="${optionE==null?'':optionE }"  />
                    <span >请输入E选项,若题型为单选，则此处可以不填</span>
                </div>
         		 <div>
     
         		  <label >请勾选正确答案:</label><span >*切记选项不能为空</span><br>
                <label > A</label><input type="checkbox" name="correct" value="A" style=”width:20px;height:20px;”/><br>
                  <label >B</label><input type="checkbox" name="correct" value="B" style=”width:20px;height:20px;”/><br>
                     <label >C</label><input type="checkbox" name="correct" value="C" style=”width:20px;height:20px;”/><br>
                  <label > D</label><input type="checkbox" name="correct" value="D" style=”width:20px;height:20px;”/><br>
                   <label > E</label><input type="checkbox" name="correct" value="E" style=”width:20px;height:20px;”/> 
             	 <span >若题型为单选，则此处可以不勾选</span>
                </div>
                
                <div>
                    <label for="score">分值</label>
                    <input type="text" name="score" id="score" value="${score==null?'':score }" required/>
                    <span>*请输入分值</span>
                </div>
                <div>
                    <label for="description">描述</label>
                    <input type="text" name="description" id="description" value="${description==null?'':description }"  />
                    <span>请输入此题描述</span>
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