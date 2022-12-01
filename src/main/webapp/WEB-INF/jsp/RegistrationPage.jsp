<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="form" scope="request" type="ru.itis.MyTube.model.dto.forms.RegistrationForm"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${requestScope.regPageCss}">
</head>
<body background="${pageContext.servletContext.contextPath}/images/reg-background-img.jpg">
<%@include file="/WEB-INF/commonJsp/alertsHandler.jsp" %>
<div id="box">
    <form action="" method="post">
        <div class="form m-4">
            <h4 class="text-center mb-3">Registration</h4>

            <div class="input-group">
                <div class="input-group-text">Username</div>
                <input type="text" class="form-control" placeholder="Write your username..." aria-label="Username"
                       name="username" value="${form.username}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="username" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Password</div>
                <input type="password" class="form-control" placeholder="Write your password..." aria-label="Password"
                       name="password" value="${form.password}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="password" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <span class="input-group-text">Repeat password</span>
                <input type="password" class="form-control" placeholder="Repeat your password..."
                       aria-label="Repeat password"
                       name="passwordRepeat" value="${form.passwordRepeat}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="passwordRepeat" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">First name</div>
                <input type="text" class="form-control" placeholder="Write..." aria-label="First name" name="firstName"
                       value="${form.firstName}">
                <div class="input-group-text">Last name</div>
                <input type="text" class="form-control" placeholder="Write..." aria-label="Last name" name="lastName"
                       value="${form.lastName}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="firstName" map="${requestScope.problems}"/>
                <o:problemOut name="lastName" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Birthdate</div>
                <input type="date" class="form-control" placeholder="Write your birthdate..." aria-label="Birthdate"
                       name="birthdate" value="${form.birthdate}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="birthdate" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Country</div>
                <input type="text" class="form-control" placeholder="Write your country..." aria-label="Country"
                       name="country" value="${form.country}">
            </div>
            <div class="mb-5 text-danger">
                <o:problemOut name="country" map="${requestScope.problems}"/>
            </div>


            <div class="input-group">
                <div class="input-group-text">
                    <input type="checkbox" aria-label="agreement" class="form-check-input" name="agreement">
                </div>
                <span class="form-control">I am agree with processing my personal data</span>
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="agreement" map="${requestScope.problems}"/>
            </div>

            <div class="container text-center">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
</body>
</html>
