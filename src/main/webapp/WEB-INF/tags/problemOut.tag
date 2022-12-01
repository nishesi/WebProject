<%@tag description="output error description if exists" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="map" required="true" type="java.util.Map<java.lang.String, java.lang.String>" %>

<c:if  test="${not empty map.get(name)}">
    <div class="alert alert-danger text-center p-1">${map.get(name)}</div>
</c:if>
