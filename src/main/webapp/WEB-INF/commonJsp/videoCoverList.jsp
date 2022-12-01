<%@ page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="o" tagdir="/WEB-INF/tags" %>


<c:choose>
    <c:when test="${requestScope.videoCoverList == null}">
        <div class="alert alert-warning text-center">
            Videos not loaded :(
        </div>
    </c:when>
    <c:otherwise>
        <jsp:useBean id="videoCoverList" scope="request" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>"/>
        <c:choose>
            <c:when test="${empty videoCoverList}">
                <div class="alert alert-info text-center" role="alert">
                    No suitable videos found :(
                </div>
            </c:when>
            <c:otherwise>
                <div class="container-fluid p-0">
                <div class="row row-cols-2 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-3">

                    <c:forEach items="${videoCoverList}" var="videoCover" >

                        <o:videoCover videoCover="${videoCover}"/>

                    </c:forEach>

                </div>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
