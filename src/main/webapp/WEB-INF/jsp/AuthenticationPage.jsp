<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${requestScope.regPageCss}">
</head>
<body background="${pageContext.servletContext.contextPath}/images/reg-background-img.jpg">

<%@include file="/WEB-INF/commonJsp/alertsHandler.jsp" %>

<div id="box">
    <form action="" method="post">
        <div class="form m-4">
            <h4 class="text-center mb-3">Authentication</h4>
            <c:if test="${requestScope.error != null}">
                <div class="error">
                    <p>${requestScope.error}</p>
                </div>
            </c:if>

            <div class="input-group">
                <div class="input-group-text">Username</div>
                <input type="text" class="form-control" placeholder="Write your username..." aria-label="Username"
                       name="username" value="${requestScope.form.username}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="username" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Password</div>
                <input type="password" class="form-control" placeholder="Write your password..." aria-label="Password"
                       name="password" value="${requestScope.form.password}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="password" map="${requestScope.problems}"/>
            </div>
            <div class="container text-center">

                <button type="submit" class="btn btn-outline-primary">Authorize</button>
                <a class="btn btn-outline-secondary"
                   href="${pageContext.servletContext.contextPath}${UrlPatterns.REGISTRATION_PAGE}">Register</a>
            </div>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
</body>
</html>
