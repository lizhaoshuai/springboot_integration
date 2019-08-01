<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/29
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-3.4.1.min.js" type="application/javascript"></script>
    <script src="/js/bootstrap.min.js" type="application/javascript"></script>
    <title>Title</title>
</head>
<body>
    <h1>学生信息</h1>
    <table id="tab" class="table table-condensed">
        <tr>
            <th>学生编号</th>
            <th>学生姓名</th>
            <th>学生年龄</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${list}" var="student">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
                <td>
                    <a href="/student/findById/${student.id}" style="color: white;text-decoration: none"></a>
                    <a href="${student.id}" style="color: white;text-decoration: none"></a>
                    <button id="ubtn" type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModal" data-whatever="update">修改</button>
                    <button type="button" class="btn btn-info" data-toggle="" data-target="#exampleModal" data-whatever="del">删除</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModal" data-whatever="save">添加</button>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel"></h4>
                </div>
                <div class="modal-body">
                    <form id="myFormId" name="form1" action=" " method="post">
                        <div class="form-group">
                            <label for="student-id" class="control-label">学生编号</label>
                            <input type="text" class="form-control" id="student-id" name="id">
                        </div>
                        <div class="form-group">
                            <label for="student-name" class="control-label">学生姓名</label>
                            <input type="text" class="form-control" id="student-name" name="name">
                        </div>
                        <div class="form-group">
                            <label for="student-age" class="control-label">学生年龄</label>
                            <input type="text" class="form-control" id="student-age" name="age">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="sbtn">提交</button>
                </div>
            </div>
        </div>
    </div>
    <script>
            $('#exampleModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget) //触发模式的按钮
                var recipient = button.data('whatever') // 从data-*属性中提取信息
                var modal = $(this)
                if (recipient == "save"){
                    modal.find(".modal-title").text("添加学生信息")
                    $("#myFormId").attr("action", "/student/save") //为action属性赋值
                    $("#sbtn").click(function () {
                        $("#myFormId").submit()
                    })
                }
                if (recipient == "update"){
                    modal.find(".modal-title").text("修改学生信息")
                   //$("#myFormId").attr("action","/student/update/" + sid)
                }
            })
    </script>
</body>
</html>
