<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="commonCssUrl" scope="application" type="java.lang.String"/>

<jsp:useBean id="video" scope="request" type="ru.itis.MyTube.model.dto.Video"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MyTube</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${commonCssUrl}">
    <script src="${requestScope.videoReactionsScriptUrl}"></script>
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp" %>

<main class="container-fluid">
    <div class="row">

        <div class="col">
            <video id="${video.uuid}" height="480" width="640" type="video/mp4" controls="controls">
                <source src="${video.videoUrl}">
            </video>
        </div>

        <div class="col">
            <div class="container-fluid">
                <div class="m-2">${video.videoCover.name}</div>
                <div class="container p-0 align-baseline d-inline mt-2">
                    <img class="rounded-circle channel-image" src="${video.videoCover.channelCover.channelImgUrl}"
                         alt="channel cover">
                    ${video.videoCover.channelCover.name}
                </div>
                <div id="reactions" class="btn-group float-end" role="group">
                    <input type="radio" class="btn-check reactionType" name="btnradio" id="like" autocomplete="off"
                           <c:if test="${requestScope.reaction == 1}">checked</c:if>>
                    <label id="likeLabel" class="btn btn-outline-success" for="like">Like ${video.likes}</label>

                    <input type="radio" class="btn-check reactionType" name="btnradio" id="void" autocomplete="off"
                           <c:if test="${requestScope.reaction == 0}">checked</c:if>>
                    <label id="voidLabel" class="btn btn-outline-secondary" for="void"> </label>

                    <input type="radio" class="btn-check reactionType" name="btnradio" id="dislike" autocomplete="off"
                           <c:if test="${requestScope.reaction == -1}">checked</c:if>>
                    <label id="dislikeLabel" class="btn btn-outline-danger"
                           for="dislike">Dislike ${video.dislikes}</label>
                </div>
                <div class="mt-2">
                    ${video.info}
                </div>
            </div>
        </div>

    </div>


    <div class="container-fluid p-0 mt-5">
        <%@include file="/WEB-INF/commonJsp/videoCoverList.jsp" %>
    </div>
</main>

<%@include file="/WEB-INF/commonJsp/footer.jsp" %>
</body>
</html>
