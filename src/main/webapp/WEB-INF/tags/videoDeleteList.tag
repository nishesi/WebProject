<%@tag description="list of names of videos" pageEncoding="UTF-8" %>

<%@ attribute name="list" required="true" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>" %>
<%@ attribute name="url" required="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${list}" var="videoCover">
    <li>
        <form method="post" action="${url}">
            <input type="text" value="${videoCover.uuid}" style="display: none" name="videoUuid">
            <button type="submit" class="dropdown-item">${videoCover.name}</button>
        </form>
    </li>
</c:forEach>
