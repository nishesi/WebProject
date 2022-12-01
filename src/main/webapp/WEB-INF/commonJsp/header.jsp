<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="logoUrl" scope="application" type="java.lang.String"/>

<%@include file="/WEB-INF/commonJsp/alertsHandler.jsp" %>

<header class="container-fluid">
    <nav class="navbar navbar-expand-md bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img id="logo" src="${logoUrl}" alt="logo">
                <strong>${appName}</strong>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">

                    <li class="nav-item">
                        <a class="nav-link <c:if test="${\"random\".equals(listType)}">active</c:if> "
                           aria-current="page" href="${pageContext.servletContext.contextPath}">Random</a>
                    </li>

                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <a class="nav-link <c:if test="${\"subs\".equals(listType)}">active</c:if>"
                               href="${pageContext.servletContext.contextPath}?listType=subs">Subscriptions</a>
                        </li>
                    </c:if>

                    <li class="nav-item">
                        <form action="${pageContext.servletContext.contextPath}${UrlPatterns.SEARCH_PAGE}" method="get"
                              role="search"
                              class="ms-md-5">
                            <div class="input-group flex-nowrap">
                                <input
                                        type="text"
                                        name="substring"
                                        class="form-control"
                                        placeholder="Search"
                                        aria-label="Search"
                                        value="${requestScope.substring}"
                                />
                                <button class="btn btn-outline-secondary" type="submit">?</button>
                            </div>
                        </form>
                    </li>
                </ul>

                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="navbar-brand fs-5 ms-md-5 btn" type="button" data-bs-toggle="offcanvas"
                             data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                            <img class="rounded-circle" width="40" height="40" src="${sessionScope.user.userImgUrl}" alt="user image">
                        </div>
                    </c:when>

                    <c:otherwise>
                        <ul class="navbar-nav ms-md-5 mb-2 mb-md-0">
                            <li class="nav-item me-2">
                                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"
                                        data-bs-target="#regModal">
                                    <strong>Register</strong>
                                </button>
                            </li>
                            <li class="nav-item">
                                <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal"
                                        data-bs-target="#authModal">
                                    <strong>Login</strong>
                                </button>
                            </li>
                        </ul>

                        <jsp:include page="/WEB-INF/commonJsp/modalRegPage.jsp"/>
                        <jsp:include page="/WEB-INF/commonJsp/modalAuthPage.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>

<jsp:include page="/WEB-INF/commonJsp/menu.jsp"/>

