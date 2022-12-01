<%@tag description="output video cover for video area" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="v" uri="/WEB-INF/tag/VideoCoverAuxiliary.tld" %>

<%@attribute name="videoCover" required="true" type="ru.itis.MyTube.model.dto.VideoCover" %>

<div class="col">
    <div class="card">
        <a href="${videoCover.watchUrl}">
            <img src="${videoCover.videoCoverImgUrl}" class="card-img-top" alt="video icon">
        </a>

        <div class="card-body p-1">

            <h6 class="card-title">
                <a class="" href="${videoCover.watchUrl}">
                    ${videoCover.name}
                </a>
            </h6>

            <a href="${videoCover.channelCover.channelUrl}">
                <div class="container p-0 align-baseline">
                    <img class="rounded-circle channel-image" src="${videoCover.channelCover.channelImgUrl}"
                         alt="channel cover">
                    ${videoCover.channelCover.name}
                </div>
            </a>

            <div class="container m-1">

                <p class="d-inline">
                    <v:views views="${videoCover.views}"/>
                </p>
                —
                <p class="d-inline">
                    <v:duration duration="${videoCover.duration}"/>
                </p>
            </div>
            <p class="m-0 text-center">
                <v:whenAdded addedDate="${videoCover.addedDate}"/>
            </p>
        </div>
    </div>
</div>
