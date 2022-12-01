<%@tag description="list of names of videos" pageEncoding="UTF-8" %>

<%@ attribute name="list" required="true" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>" %>
<%@ attribute name="url" required="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${list}" var="videoCover">
    <li><a class="dropdown-item" href="${url}?uuid=${videoCover.uuid}">${videoCover.name}</a></li>
</c:forEach>
