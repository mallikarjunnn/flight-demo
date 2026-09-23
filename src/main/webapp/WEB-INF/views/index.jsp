<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SkyReserve | Flight Booking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="nav">
    <div class="brand">SkyReserve</div>
    <div>
        <a href="${pageContext.request.contextPath}/">Search</a>
        <a href="${pageContext.request.contextPath}/booking">My booking</a>
        <a href="${pageContext.request.contextPath}/admin/login">Admin</a>
    </div>
</div>
<div class="wrap">
    <div class="card hero">
        <h1>Find a flight</h1>
        <p class="muted">Search by city. Date is optional — leave it blank to see every matching route.</p>
        <form action="${pageContext.request.contextPath}/search" method="get">
            <div class="row">
                <div class="field">
                    <label>From</label>
                    <input name="source" placeholder="Delhi" required>
                </div>
                <div class="field">
                    <label>To</label>
                    <input name="destination" placeholder="Mumbai" required>
                </div>
                <div class="field">
                    <label>Date</label>
                    <input type="date" name="date">
                </div>
            </div>
            <p><button class="btn" type="submit">Search flights</button></p>
        </form>
    </div>
    <p class="muted">Sample routes in the database: Delhi → Mumbai (15 Sep 2026), Mumbai → Bengaluru, Delhi → Goa.</p>
</div>
<div class="footer">Week 1 · Spring Core + MVC · MySQL</div>
</body>
</html>
