<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="nav">
    <div class="brand">SkyReserve Admin</div>
    <div>
        <a href="${pageContext.request.contextPath}/admin">Flights</a>
        <a href="${pageContext.request.contextPath}/admin/logout">Logout</a>
    </div>
</div>
<div class="wrap">
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-ok">${success}</div>
    </c:if>

    <div class="card">
        <h1>Add a flight</h1>
        <form action="${pageContext.request.contextPath}/admin/flights/add" method="post">
            <div class="row">
                <div class="field">
                    <label>Flight number</label>
                    <input name="flightNumber" placeholder="6E-777" required>
                </div>
                <div class="field">
                    <label>Airline</label>
                    <input name="airline" placeholder="IndiGo" required>
                </div>
            </div>
            <div class="row">
                <div class="field">
                    <label>From</label>
                    <input name="source" required>
                </div>
                <div class="field">
                    <label>To</label>
                    <input name="destination" required>
                </div>
            </div>
            <div class="row">
                <div class="field">
                    <label>Date</label>
                    <input type="date" name="departureDate" required>
                </div>
                <div class="field">
                    <label>Depart time</label>
                    <input type="time" name="departureTime" required>
                </div>
                <div class="field">
                    <label>Arrive time</label>
                    <input type="time" name="arrivalTime" required>
                </div>
            </div>
            <div class="row">
                <div class="field">
                    <label>Price (₹)</label>
                    <input type="number" step="0.01" name="price" required>
                </div>
                <div class="field">
                    <label>Total seats</label>
                    <input type="number" min="1" name="totalSeats" value="180" required>
                </div>
            </div>
            <p><button class="btn" type="submit">Add flight</button></p>
        </form>
    </div>

    <div class="card">
        <h2>All flights</h2>
        <table>
            <tr>
                <th>Flight</th>
                <th>Route</th>
                <th>Date / time</th>
                <th>Price</th>
                <th>Seats</th>
                <th></th>
            </tr>
            <c:forEach var="f" items="${flights}">
                <tr>
                    <td>${f.flightNumber}<br><span class="muted">${f.airline}</span></td>
                    <td>${f.source} → ${f.destination}</td>
                    <td>${f.departureDate}<br>${f.departureTime}–${f.arrivalTime}</td>
                    <td>₹ ${f.price}</td>
                    <td>${f.availableSeats} / ${f.totalSeats}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/flights/delete" method="post"
                              onsubmit="return confirm('Delete this flight and its bookings?');">
                            <input type="hidden" name="id" value="${f.id}">
                            <button class="btn btn-danger" type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
