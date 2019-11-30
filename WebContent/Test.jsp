<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在线答题</title>
</head>

<body>
<center><font size="6" color="blue" align="center">${testname }</font></center>
<center><font size="3" color="red" align="center">${hint }</font></center>

<div style="text-align:right; ">
       <img src="img/time.png" />考试还剩余
    </div>
    <div id="time" style="text-align:right;"></div>
    <%
    int i=1; 
    %>
<form id="frm" action="CalculateTest.do?testid=${testid }&username=${username }" method="post">
 <c:forEach items="${list}" var="question">
 &nbsp &nbsp<font size="4"><%=i %>.
 <c:if test="${question.type eq '单选' }">(单选)</c:if>
 <c:if test="${question.type eq '多选' }">(多选)</c:if>
  ${question.title }</font>
 <br>
 <br>
  <c:if test="${question.type eq '单选' }">
  &nbsp &nbsp &nbsp &nbsp<input type="radio" value="A" name="test<%=i%>"/>A.${question.optionA }
  &nbsp &nbsp<input type="radio" value="B" name="test<%=i%>"/>B.${question.optionB }
  &nbsp &nbsp<input type="radio" value="C" name="test<%=i%>"/>C.${question.optionC }
  &nbsp &nbsp<input type="radio" value="D" name="test<%=i++%>"/>D.${question.optionD }
 <br>
 <br>
 </c:if>
 <c:if test="${question.type eq '多选' }">
 &nbsp &nbsp &nbsp &nbsp<input type="checkbox" value="A" name="test<%=i%>"/>A.${question.optionA }
  &nbsp &nbsp<input type="checkbox" value="B" name="test<%=i%>"/>B.${question.optionB }
  &nbsp &nbsp<input type="checkbox" value="C" name="test<%=i%>"/>C.${question.optionC }
  &nbsp &nbsp<input type="checkbox" value="D" name="test<%=i%>"/>D.${question.optionD }
   &nbsp &nbsp<input type="checkbox" value="E" name="test<%=i++%>"/>E.${question.optionE }
   <br>
 <br>
 </c:if>
 </c:forEach>
 <center><input type="submit"  style="color:blue ;width:80px; height:40px " value="提交"></center>

</form>
<script>
    var times = 120*60;//剩余时间,单位秒
    var timeDiv = document.getElementById("time");
    var timeObj = null;
    function timer(){
        if(times == 0){
            //倒计时结束，提交表单
            document.getElementById("frm").submit();
            window.clearInterval(timeObj);
            return;
        }
        var t = Math.floor(times/60) +"分"+times%60+"秒"
        timeDiv.innerHTML = t;
        times --;
    }
    timeObj = window.setInterval(timer,1000);
</script>
</body>
</html>