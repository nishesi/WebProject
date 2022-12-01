<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="user" scope="session" type="ru.itis.MyTube.model.dto.User"/>
<jsp:useBean id="appName" scope="application" type="java.lang.String"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${appName}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  <link rel="stylesheet" href="${requestScope.regPageCss}">
</head>
<body background="${pageContext.servletContext.contextPath}/images/reg-background-img.jpg">

<%@include file="/WEB-INF/commonJsp/alertsHandler.jsp" %>

<div id="box">
  <form action="" method="post" enctype="multipart/form-data">
    <div class="form m-4">
      <h4 class="text-center mb-3">User Information</h4>

      <div class="input-group">
        <div class="container text-center">
          <img class="card-img-top rounded-circle" src="${user.userImgUrl}" alt="user image">
        </div>
        <div class="mb-3">
          <label for="formFile" class="form-label">Choose new icon</label>
          <input class="form-control" type="file" id="formFile" name="icon">
        </div>
      </div>
      <div class="mb-4 text-danger">
        <o:problemOut name="icon" map="${requestScope.problems}"/>
      </div>

      <div class="input-group mb-4">
        <div class="input-group-text">Username</div>
        <div class="form-control" aria-label="Username">${user.username}</div>
      </div>

      <div class="input-group">
        <div class="input-group-text">Password</div>
        <input type="password" class="form-control" placeholder="Write your password..." aria-label="Password"
               name="password">
      </div>
      <div class="mb-4 text-danger">
        <o:problemOut name="password" map="${requestScope.problems}"/>
      </div>

      <div class="input-group">
        <div class="input-group-text">First name</div>
        <input type="text" class="form-control" placeholder="Write..." aria-label="First name" name="firstName"
               value="${user.firstName}">
        <div class="input-group-text">Last name</div>
        <input type="text" class="form-control" placeholder="Write..." aria-label="Last name" name="lastName"
               value="${user.lastName}">
      </div>
      <div class="mb-4 text-danger">
        <o:problemOut name="firstName" map="${requestScope.problems}"/>
        <o:problemOut name="lastName" map="${requestScope.problems}"/>
      </div>

      <div class="input-group">
        <div class="input-group-text">Birthdate</div>
        <input type="date" class="form-control" placeholder="Write your birthdate..." aria-label="Birthdate"
               name="birthdate" value="${user.birthdate}">
      </div>
      <div class="mb-4 text-danger">
        <o:problemOut name="birthdate" map="${requestScope.problems}"/>
      </div>

      <div class="input-group">
        <div class="input-group-text">Country</div>
        <input type="text" class="form-control" placeholder="Write your country..." aria-label="Country"
               name="country" value="${user.country}">
      </div>
      <div class="mb-5 text-danger">
        <o:problemOut name="country" map="${requestScope.problems}"/>
      </div>

      <div class="container text-center">
        <button type="submit" class="btn btn-primary">Change</button>
      </div>
    </div>
  </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
</body>
</html>
