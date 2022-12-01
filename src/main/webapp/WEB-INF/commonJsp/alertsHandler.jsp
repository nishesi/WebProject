<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="al" uri="/WEB-INF/tag/AlertHandler.tld" %>

<div>
    <jsp:useBean id="alerts" scope="session" type="java.util.Queue<ru.itis.MyTube.auxiliary.Alert>"/>
    <al:alertList alerts="${alerts}"/>
</div>
