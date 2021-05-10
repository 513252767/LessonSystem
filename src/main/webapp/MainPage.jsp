<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 2021/5/9
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>主页面</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <style>body {
        padding-top: 60px;
    }</style>

    <link href="css/bootstrap.css" rel="stylesheet"/>
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">

    <script src="js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.js" type="text/javascript"></script>

    <style>
        .wrap {
            margin: 0 auto;
            width: 80%;
        }

        #left {
            width: 200px;
            height: 500px;
            background: #ccffff;
            float: left;
        }

        #right {
            width: 800px;
            height: 500px;
            background: #ffcccc;
            margin-left: 50px;
        }
    </style>

    <script>
        $(function () {
            $.get("/BaseServlet?method=menuQuery", {parentId: 0}, function (data) {
                //获取菜单信息
                var menuTree = $('#left');
                for (var i = 0; i < data.length; i++) {
                    menuTree.append("<li><a onclick='menu(this)' id='" + data[i].id + "' href=" + data[i].url + ">" + data[i].name + "</a></li>");
                }
            })
        })

        function menu(a) {
            //获取点击的超链接标签
            var afirst = $(a);
            //判断一级菜单是否有菜单信息，有则获取子菜单，没有则资源跳转
            if (afirst.attr("href") !== '' && afirst.attr("href") != null) {
                $("#ifData").attr("src", afirst.attr("href"));
                return;
            }
            //获取当前超链接标签后的同级标签
            var ul = afirst.next();
            //判断是否存在子菜单信息，存在则删除，不存在则发起ajax请求获取下级菜单
            if (ul.length > 0) {
                ul.remove();
            } else {
                $.get("/BaseServlet?method=menuQuery", {parentId: afirst.attr("id")}, function (data) {
                    alert(data);
                    //判断session是否失效
                    if ("sessionError" === data) {
                        window.Location.href = "Login.jsp";
                    }
                    //在一级菜单后加入ul标签
                    afirst.attr("<ul></ul>");
                    //afirst.after("<ul></ul>")
                    //在二级标签中显示ul标签
                    var ul = afirst.next();
                    for (var i = 0; i < data.length; i++) {
                        ul.append("<li><a onclick='menu(this)' id='" + data[i].id + "' href='" + data[i].url + "'>" + data[i].name + "</a></li>");
                    }
                })
            }
        }
    </script>
</head>
<body>
<div class="wrap">
    <ul id="left"></ul>
    <iframe id="right" src="teacher/GradeConfirm.html"></iframe>

</div>
</body>
</html>
