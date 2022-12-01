<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

<div class="modal fade" id="authModal" tabindex="-1" aria-labelledby="authModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="${pageContext.servletContext.contextPath}${UrlPatterns.AUTHENTICATION_PAGE}" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="authModalLabel">Authorization</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <div class="form m-4">
                        <c:if test="${requestScope.error != null}">
                            <div class="error">
                                <p>${requestScope.error}</p>
                            </div>
                        </c:if>

                        <div class="input-group">
                            <div class="input-group-text">Username</div>
                            <input type="text" class="form-control" placeholder="Write your username..." aria-label="Username"
                                   name="username" value="${form.username}">
                        </div>
                        <div class="mb-4 text-danger">
                            <o:problemOut name="usernameProblem" map="${requestScope.problems}"/>
                        </div>

                        <div class="input-group">
                            <div class="input-group-text">Password</div>
                            <input type="password" class="form-control" placeholder="Write your password..." aria-label="Password"
                                   name="password" value="${form.password}">
                        </div>
                        <div class="mb-4 text-danger">
                            <o:problemOut name="passwordProblem" map="${requestScope.problems}"/>
                        </div>
                        <div class="container text-center">
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-target="#regModal" data-bs-toggle="modal">Register</button>
                    <button type="submit" class="btn btn-primary">Authorize</button>
                </div>
            </div>
        </form>
    </div>
</div>
