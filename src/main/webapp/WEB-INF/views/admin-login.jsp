<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="nav">
    <div class="brand">SkyReserve Admin</div>
    <div><a href="${pageContext.request.contextPath}/">User site</a></div>
</div>
<div class="wrap">
    <div class="card">
        <h1>Admin login</h1>
        <p class="muted">Default account from schema.sql: <strong>admin</strong> / <strong>admin123</strong></p>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/admin/login" method="post">
            <div class="field">
                <label>Username</label>
                <input name="username" required>
            </div>
            <p>
            <div class="field">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>
            </p>
            <p><button class="btn" type="submit">Login</button></p>
        </form>
    </div>
</div>
</body>
</html>
