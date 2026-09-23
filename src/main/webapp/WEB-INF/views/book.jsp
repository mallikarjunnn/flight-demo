<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book flight</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="nav">
    <div class="brand">SkyReserve</div>
    <div>
        <a href="${pageContext.request.contextPath}/">Search</a>
        <a href="${pageContext.request.contextPath}/booking">My booking</a>
    </div>
</div>
<div class="wrap">
    <div class="card">
        <h1>Passenger details</h1>
        <p><strong>${flight.airline} ${flight.flightNumber}</strong> ·
            ${flight.source} → ${flight.destination} · ${flight.departureDate}
            ${flight.departureTime} · Seats left: ${flight.availableSeats}</p>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="flightId" value="${flight.id}">
            <div class="row">
                <div class="field">
                    <label>Full name</label>
                    <input name="passengerName" required>
                </div>
                <div class="field">
                    <label>Email</label>
                    <input type="email" name="email" required>
                </div>
            </div>
            <div class="row">
                <div class="field">
                    <label>Phone</label>
                    <input name="phone" required>
                </div>
                <div class="field">
                    <label>Seats</label>
                    <input type="number" name="seats" min="1" max="${flight.availableSeats}" value="1" required>
                </div>
            </div>
            <p><button class="btn" type="submit">Confirm booking</button></p>
        </form>
    </div>
</div>
</body>
</html>
