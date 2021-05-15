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
        var condition = $('#condition').val();
        //回显
        $.post("/BaseServlet?method=findDataOnPage", {condition: condition,}, function (lessons) {
            for (let i = 0; i < lessons.length; i++) {
                $('#queryTable').append(" <tr height={40px} align=center>" +
                    "                    <td><font color=gray>" + lessons[i].id + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].name + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].week + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].turn + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].number + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].classroom + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].teacher + "</font></td>\n" +
                    "                    <td><font color=gray>" + lessons[i].category + "</font></td>\n" +
                    "                </tr>");
                $('#pageBtn').append(" <li class='page-item active'><a class='page-link' href='/BaseServlet?method=findDataOnPage&currentPage=&rows=5&condition='>i</a></li>')");
            }
        })
        $('#window').show();
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
        $.post("/BaseServlet?method=lessonQuery", function (lessons) {
            for (let i = 0; i < lessons.length; i++) {
                var lesson = lessons[i];
                $('#lessonTable').append("<tr height = 40px align = center >" +
                    "                    <td ><font color = blue><b>" + (i + 1) + "</b></font></td><td>" + lesson[0] + "</td><td>" + lesson[1] + "</td><td>" + lesson[2] + "</td><td>" + lesson[3] + "</td><td>" + lesson[4] + "</td></tr>")
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
        <form accept-charset="UTF-8">
            <input type="text" aria-label="First name" id="condition" placeholder="Name">
            <a class="btn btn-outline-primary" name="btnCityAndName" onclick="show(this)">Query By Name</a>
        </form>
        <div>
            <table id="lessonTable" border=6 frame=void align=center width=750px height=100px cellspacing=0px
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
<div align="center" style="display: none" id="window">
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
                    <th><font color=gray>教师</font></th>
                    <th><font color=gray>课程类型</font></th>
                </tr>
            </table>
        </div>
        <nav aria-label="Page navigation example" style="padding-left: 80px">
            <ul class="pagination" id="pageBtn">
                <%--        上一页按钮--%>
                <c:if test="${pb.currentPage == 1}">
                <li class="disabled page-item">
                    </c:if>

                    <c:if test="${pb.currentPage!=1}">
                <li class="page-item">
                    </c:if>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${pb.currentPage - 1}&rows=6&condition=${condition}">Previous</a>
                </li>

                <%--    页数按钮--%>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                    <c:if test="${pb.currentPage==i}">
                        <li class="page-item active"><a class="page-link"
                                                        href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${i}&rows=5&condition=${condition}">${i}</a>
                        </li>
                    </c:if>
                    <c:if test="${pb.currentPage!=i}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&currentPage=${i}&rows=5&condition=${condition}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>

                <%--            下一页按钮--%>
                <c:if test="${pb.currentPage==pb.totalPage}">
                <li class="disabled page-item">
                    </c:if>

                    <c:if test="${pb.currentPage!=pb.totalPage}">
                <li class="page-item">
                    </c:if>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/BaseServlet?method=findDataOnPage&&currentPage=${pb.currentPage+1}&&rows=6&condition=${condition}">Next</a>
                </li>
            </ul>
        </nav>
    </div>
    <div id="fade" class="black_overlay"></div>
</div>


</body>
</html>
