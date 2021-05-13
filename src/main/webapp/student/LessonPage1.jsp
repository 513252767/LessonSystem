<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 2021/5/12
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--导入CSS全局样式--%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<%--导入JQuery--%>
<script src="../js/jquery-3.5.1.min.js"></script>
<%--导入BootStrap--%>
<script src="../js/bootstrap.min.js"></script>
<script type="text/javascript"></script>
<script>
    function show(element) {
        var condition = $('queryByCondition').value();
        //回显
        $.ajax("/BaseServlet?method=findDataOnPage", {condition:condition}, function (lessons) {
            for (let lesson in lessons) {
                $('messageTable').append(" <tr height={40px} align=center>\n" +
                    "                    <td><font color=gray>" + lesson.id + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.name + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.week + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.turn + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.number + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.classroom + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.teacher + "</font></td>\n" +
                    "                    <td><font color=gray>" + lesson.category + "</font></td>\n" +
                    "                </tr>");
            }
        })

        var light = document.getElementById('light');
        var fade = document.getElementById('fade');
        light.style.display = 'block';
        fade.style.display = 'block';
        var a = document.getElementsByClassName().innerHTML = sd;
        console.log();
    }

    function hide(tag) {
        var light = document.getElementById(tag);
        var fade = document.getElementById('fade');
        light.style.display = 'none';
        fade.style.display = 'none';
    }

    <!--        展开课程-->
    $(function () {
        $.ajax("/BaseServlet?method=lessonQuery", function (data) {
            var lessonTable = $('lessonTable');
            var i = 0;
            for (let dataKey in data) {
                lessonTable.append("<tr height = 40px align = center id=\"lessonOne\">\n" +
                    "                    <td ><font color = blue><b>i</b></font></td><td>" + dataKey[0] + "</td><td>" + dataKey[1] + "</td><td>" + dataKey[2] + "</td><td>" + dataKey[3] + "</td><td>" + dataKey[4] + "</td></tr>")
                i++;
            }
        })
    })
</script>
<html>
<head>
    <title>课程表</title>
</head>
<body>
<div align="center" style="padding-left: 200px;padding-right: 200px">
    <div>
        <form action="<c:url value="/BaseServlet?method=findDataOnPage"/>" method="post" accept-charset="UTF-8">
            <input type="text" aria-label="First name" id="condition" placeholder="Name" >
            <button type="submit" class="btn btn-outline-primary" name="btnCityAndName">Query By City AND Name</button>
            <a href=javascript:void(0) onclick={show(this)}>查看评价</a>
        </form>
        <div>
            <table id="lessonTable" border=6 frame=void align=center width=650px height=100px cellspacing=0px
                   cellpadding=10px>
                <tr height=50px align=center>
                    <th width=110px></th>
                    <th><font color=blue>星期一</font></th>
                    <th><font color=blue>星期二</font></th>
                    <th><font color=blue>星期三</font></th>
                    <th><font color=blue>星期四</font></th>
                    <th><font color=blue>星期五</font></th>
                </tr>
            </table>
        </div>
    </div>
</div>

<!--弹出窗口-->
<div align="center">
    <div id="light" class="white_content">
        <div class="close"><a href="javascript:void(0)" onclick="hide('light')"> 关闭</a></div>
        <div class="con" align="center">
            <table id="queryTable" border=6 frame=void align=center width=650px height=100px cellspacing=0px
                   cellpadding=10px>
                <tr height=50px align=center>
                    <th><font color=gray>课程id</font></th>
                    <th><font color=gray>课程名称</font></th>
                    <th><font color=gray>星期</font></th>
                    <th><font color=gray>节数</font></th>
                    <th><font color=gray>最大人数</font></th>
                    <th><font color=gray>教室</font></th>
                    <th><font color=gray>种类</font></th>
                </tr>
            </table>
        </div>
    </div>

    <nav aria-label="Page navigation example" style="padding-left: 80px">
        <ul class="pagination">
            <%--        上一页按钮--%>
            <c:if test="${pb.currentPage == 1}">
            <li class="disabled page-item">
                </c:if>

                <c:if test="${pb.currentPage!=1}">
            <li class="page-item">
                </c:if>
                <a class="page-link" href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${pb.currentPage - 1}&rows=6&condition=${condition}">Previous</a>
            </li>

            <%--    页数按钮--%>
            <c:forEach begin="1" end="${pb.totalPage}" var="i">
                <c:if test="${pb.currentPage==i}">
                    <li class="page-item active"><a class="page-link" href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${i}&rows=5&condition=${condition}">${i}</a></li>
                </c:if>
                <c:if test="${pb.currentPage!=i}">
                    <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${i}&rows=5&condition=${condition}">${i}</a></li>
                </c:if>
            </c:forEach>

            <%--            下一页按钮--%>
            <c:if test="${pb.currentPage==pb.totalPage}">
            <li class="disabled page-item">
                </c:if>

                <c:if test="${pb.currentPage!=pb.totalPage}">
            <li class="page-item">
                </c:if>
                <a class="page-link" href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&&currentPage=${pb.currentPage+1}&&rows=6&condition=${condition}">Next</a></li>
        </ul>
        总${pb.totalCount}条,共${pb.totalPage}页
    </nav>

    <div id="fade" class="black_overlay"></div>
</div>


</body>
</html>
