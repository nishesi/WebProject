<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="commonCssUrl" scope="application" type="java.lang.String"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyTube</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${commonCssUrl}">
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp" %>

<main class="container-fluid">

    <%@include file="/WEB-INF/commonJsp/videoCoverList.jsp" %>

</main>


<%@include file="/WEB-INF/commonJsp/footer.jsp" %>
</body>
</html>
