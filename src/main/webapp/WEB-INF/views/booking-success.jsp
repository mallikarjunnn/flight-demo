<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking confirmed</title>
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
    <div class="card ticket">
        <h1>Booking confirmed</h1>
        <p>Save your PNR. You will need it to view or cancel this ticket.</p>
        <p><strong>PNR:</strong> ${booking.pnr}</p>
        <p><strong>Passenger:</strong> ${booking.passengerName}</p>
        <p><strong>Flight:</strong> ${booking.flight.airline} ${booking.flight.flightNumber}</p>
        <p><strong>Route:</strong> ${booking.flight.source} → ${booking.flight.destination}</p>
        <p><strong>Date / time:</strong> ${booking.flight.departureDate} ${booking.flight.departureTime}</p>
        <p><strong>Seats:</strong> ${booking.seatsBooked}</p>
        <p><span class="badge badge-ok">${booking.status}</span></p>
        <p>
            <a class="btn" href="${pageContext.request.contextPath}/booking">View booking later</a>
            <a class="btn btn-light" href="${pageContext.request.contextPath}/">Search again</a>
        </p>
    </div>
</div>
</body>
</html>
