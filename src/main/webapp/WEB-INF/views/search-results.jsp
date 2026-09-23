<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search results</title>
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
        <h1>Flights: ${source} → ${destination}</h1>
        <c:if test="${empty flights}">
            <p class="muted">No flights found. Try another city pair or leave the date empty.</p>
        </c:if>
        <c:if test="${not empty flights}">
            <table>
                <tr>
                    <th>Flight</th>
                    <th>Airline</th>
                    <th>Date</th>
                    <th>Depart</th>
                    <th>Arrive</th>
                    <th>Price</th>
                    <th>Seats left</th>
                    <th></th>
                </tr>
                <c:forEach var="f" items="${flights}">
                    <tr>
                        <td>${f.flightNumber}</td>
                        <td>${f.airline}</td>
                        <td>${f.departureDate}</td>
                        <td>${f.departureTime}</td>
                        <td>${f.arrivalTime}</td>
                        <td>₹ ${f.price}</td>
                        <td>${f.availableSeats} / ${f.totalSeats}</td>
                        <td>
                            <c:if test="${f.availableSeats > 0}">
                                <a class="btn" href="${pageContext.request.contextPath}/book?flightId=${f.id}">Book</a>
                            </c:if>
                            <c:if test="${f.availableSeats == 0}">
                                <span class="muted">Sold out</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>
