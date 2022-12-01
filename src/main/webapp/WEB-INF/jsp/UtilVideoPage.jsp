<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${requestScope.regPageCss}">
</head>

<body background="${pageContext.servletContext.contextPath}/images/reg-background-img.jpg">

<%@include file="/WEB-INF/commonJsp/alertsHandler.jsp" %>

<div id="box">
    <form method="post" action="${requestScope.url}" enctype="multipart/form-data">
        <div class="form m-4">
            <h4 class="text-center mb-3">${requestScope.pageType} video</h4>

            <div class="input-group">
                <div class="mb-3">
                    <label for="icon" class="form-label">Choose icon</label>
                    <input class="form-control" type="file" id="icon" name="icon">
                </div>
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="icon" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Name</div>
                <input type="text" class="form-control" placeholder="Write video name..." aria-label="Name"
                       name="name" value="${requestScope.form.name}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="name" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="mb-3">
                    <label for="video" class="form-label">Choose video</label>
                    <input class="form-control" type="file" id="video" name="video">
                </div>
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="video" map="${requestScope.problems}"/>
            </div>

            <div class="input-group">
                <div class="input-group-text">Information</div>
                <input type="text" class="form-control text-area" placeholder="Write video info..." aria-label="Name"
                       name="info" value="${requestScope.form.name}">
            </div>
            <div class="mb-4 text-danger">
                <o:problemOut name="info" map="${requestScope.problems}"/>
            </div>

            <div class="container text-center">
                <button type="submit" class="btn btn-primary">${requestScope.pageType}</button>
            </div>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
</body>
</html>
