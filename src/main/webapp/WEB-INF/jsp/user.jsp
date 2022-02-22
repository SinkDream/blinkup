<%@ taglib prefix="zgs" uri="http://java.sun.com/jsp/jstl/core" %>
<zgs:set var="ZGS" value="${pageContext.request.contextPath}"/>
<%--
  Created by IntelliJ IDEA.
  User: zhangguosheng
  Date: 2022/2/22
  Time: 8:45 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
    <h1 style="text-align: center; margin-top: 50px">不要温和地走进那个良夜</h1>
    <form>
        <fieldset>
            <legend>
                👴给你一jio
            </legend>
            <table>
                <tr>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>邮箱</th>
                </tr>
                <zgs:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.phone}</td>
                        <td>${user.email}</td>
                        <td>
                            <a href="${ZGS}/user_edit?id=${user.id}">修改</a>
                            <a href="${ZGS}/user_delete?id=${user.id}">删除</a>
                        </td>
                    </tr>
                </zgs:forEach>

            </table>
        </fieldset>
    </form>

</body>
</html>
