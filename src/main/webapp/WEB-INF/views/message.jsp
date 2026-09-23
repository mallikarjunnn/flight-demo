<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty error ? 'Message' : 'Error'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="nav">
    <div class="brand">SkyReserve</div>
    <div><a href="${pageContext.request.contextPath}/">Home</a></div>
</div>
<div class="wrap">
    <div class="card">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-ok">${success}</div>
        </c:if>
        <p><a class="btn" href="${pageContext.request.contextPath}/">Back home</a></p>
    </div>
</div>
</body>
</html>
