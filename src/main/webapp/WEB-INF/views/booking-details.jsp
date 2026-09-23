<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking details</title>
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
    <div class="card">
        <h1>Find your ticket</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-ok">${success}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/booking" method="post">
            <div class="field">
                <label>PNR</label>
                <input name="pnr" value="${pnr}" placeholder="e.g. FB1A2B3C4D" required>
            </div>
            <p><button class="btn" type="submit">Show details</button></p>
        </form>
    </div>

    <c:if test="${not empty booking}">
        <div class="card ticket">
            <h2>Ticket</h2>
            <p><strong>PNR:</strong> ${booking.pnr}
                <c:if test="${booking.status == 'CONFIRMED'}">
                    <span class="badge badge-ok">${booking.status}</span>
                </c:if>
                <c:if test="${booking.status == 'CANCELLED'}">
                    <span class="badge badge-no">${booking.status}</span>
                </c:if>
            </p>
            <p><strong>Passenger:</strong> ${booking.passengerName}</p>
            <p><strong>Email / phone:</strong> ${booking.email} · ${booking.phone}</p>
            <p><strong>Flight:</strong> ${booking.flight.airline} ${booking.flight.flightNumber}</p>
            <p><strong>Route:</strong> ${booking.flight.source} → ${booking.flight.destination}</p>
            <p><strong>Departs:</strong> ${booking.flight.departureDate} ${booking.flight.departureTime}
                · arrives ${booking.flight.arrivalTime}</p>
            <p><strong>Seats:</strong> ${booking.seatsBooked}</p>
            <p><strong>Booked at:</strong> ${booking.bookedAt}</p>

            <c:if test="${booking.status == 'CONFIRMED'}">
                <form action="${pageContext.request.contextPath}/cancel" method="post"
                      onsubmit="return confirm('Cancel this ticket? Seats will go back to the flight.');">
                    <input type="hidden" name="pnr" value="${booking.pnr}">
                    <button class="btn btn-danger" type="submit">Cancel ticket</button>
                </form>
            </c:if>
        </div>
    </c:if>
</div>
</body>
</html>
