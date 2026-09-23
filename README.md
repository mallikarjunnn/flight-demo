# SkyReserve — Flight Reservation (Week 1)

A working **Spring Core + Spring MVC** web app with **MySQL**, basic **HTML/CSS**, and **JSP** pages. Built so you can import it into **Eclipse** and learn the stack by tracing one request from the browser to the database.

## What you can do in the app

| Who | Feature |
|-----|---------|
| User | Search flights by from / to / date |
| User | Book seats (seat count goes down) |
| User | Look up a ticket by PNR |
| User | Cancel a ticket (seat count goes back up) |
| Admin | Login (`admin` / `admin123`) |
| Admin | Add a flight |
| Admin | Delete a flight |

---

## 1. The tech stack (plain English)

You are **not** using Spring Boot. This is the older, assignment-style stack: you wire things in XML and deploy a `.war` to Tomcat.

### Java + Eclipse

- **JDK 8 or 11** compiles your `.java` files.
- **Eclipse IDE for Enterprise Java (EE)** knows how to run a web app on Tomcat.
- This project is a **Maven WAR**. Maven downloads Spring and the MySQL driver for you (see `pom.xml`).

### Spring Core

Spring Core is an **IoC container** (Inversion of Control).

Without Spring you would write: `FlightDao dao = new FlightDao();` everywhere.

With Spring you:

1. Mark a class `@Repository`, `@Service`, or `@Controller` (or declare a `<bean>` in XML).
2. Write `@Autowired` on a field.
3. Spring **creates** the object and **injects** the dependency.

That is **Dependency Injection**. The XML file `WEB-INF/spring-mvc-servlet.xml` is the container config: it also creates `dataSource` and `jdbcTemplate` as beans.

### Spring MVC

MVC = Model, View, Controller.

1. Browser hits a URL.
2. `web.xml` sends **every** request to Spring’s **DispatcherServlet** (front controller).
3. A `@Controller` method with `@GetMapping` / `@PostMapping` runs.
4. The method puts data in `Model` and returns a view name like `"index"`.
5. The view resolver turns that into `/WEB-INF/views/index.jsp`.
6. JSP + HTML/CSS is what the user sees.

```
Browser  →  DispatcherServlet  →  Controller  →  Service  →  DAO  →  MySQL
                 ↓
               JSP page (HTML/CSS)
```

### MySQL

MySQL stores rows in tables. Java does not speak MySQL natively. The **MySQL Connector/J** driver in `pom.xml` does.

**JdbcTemplate** (Spring JDBC) is the helper: you write SQL, it opens a connection, runs the query, maps each row into a Java object (`Flight`, `Booking`), then closes the connection.

Three tables (see `database/schema.sql`):

- `flights` — schedule + `total_seats` + `available_seats`
- `bookings` — PNR, passenger, how many seats, `CONFIRMED` / `CANCELLED`
- `admins` — login

Seat rule (the important bit):

- Book: `UPDATE flights SET available_seats = available_seats - ? WHERE available_seats >= ?`
- Cancel: add those seats back and set booking status to `CANCELLED`

`@Transactional` on `BookingService.book` / `cancel` means both SQL updates succeed or both roll back.

---

## 2. Install these first (Windows)

Do this **before** opening Eclipse.

1. **JDK 8 or 11**  
   https://adoptium.net/  
   After install, confirm in Command Prompt: `java -version`

2. **Eclipse IDE for Enterprise Java and Web Developers**  
   https://www.eclipse.org/downloads/packages/  
   Pick the **Enterprise Java** package, not the tiny “Java” only package.

3. **Apache Tomcat 9** (not 10)  
   https://tomcat.apache.org/download-90.cgi  
   Unzip somewhere like `C:\tomcat9`.  
   **Why 9?** This project uses `javax.servlet` (Spring 5). Tomcat 10 uses `jakarta.servlet` and will fail.

4. **MySQL Server 8** + **MySQL Workbench**  
   https://dev.mysql.com/downloads/installer/  
   Remember the **root password** you set.

5. **Maven** is already inside Eclipse (m2e). You do not have to install Maven separately.

---

## 3. Create the database

1. Open MySQL Workbench and connect as `root`.
2. Open `database/schema.sql` from this folder.
3. Run the whole script. You should see database `flight_booking` with sample flights.
4. Open `src/main/resources/db.properties` and set **your** password:

```properties
jdbc.password=YOUR_MYSQL_ROOT_PASSWORD
```

If username is not `root`, change `jdbc.username` too.

---

## 4. Import into Eclipse and run

1. **File → Import → Maven → Existing Maven Projects**
2. Browse to `f:\downloads\flight-booking` → Finish. Wait until Maven finishes downloading (bottom-right).
3. **Window → Show View → Servers**. If the Servers view is empty:
   - Right-click → **New → Server → Apache → Tomcat v9.0**
   - Point to your Tomcat 9 folder.
4. Right-click the project → **Properties → Project Facets**. Dynamic Web Module should be on (Maven usually sets this).
5. Right-click **Tomcat** in Servers → **Add and Remove** → add `flight-booking` → Finish.
6. Start the server (green play on Tomcat).
7. Browser:

```
http://localhost:8080/flight-booking/
```

If you get 404, the context path might differ. In Servers, expand Tomcat, click the module, check the path. Sometimes it is `http://localhost:8080/flight-booking-1.0.0/` — we set `<finalName>flight-booking</finalName>` so it should be `/flight-booking`.

### Common Eclipse errors

| Error | Fix |
|--------|-----|
| `Communications link failure` / Access denied | Wrong password in `db.properties`. Restart Tomcat after changing it. |
| `Unknown database 'flight_booking'` | You did not run `schema.sql`. |
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Maven did not copy dependencies. Right-click project → **Maven → Update Project**. Also: project **Properties → Deployment Assembly** should include **Maven Dependencies**. |
| `NoClassDefFoundError: javax/servlet/...` | You used Tomcat 10. Use Tomcat **9**. |
| Page has no CSS | Context path is wrong; CSS is at `/flight-booking/css/style.css`. |

---

## 5. Demo script (for your lab)

1. Home: search **Delhi** → **Mumbai**, date **2026-09-15**.
2. Book **1** seat. Copy the **PNR**.
3. Search again: **Seats left** dropped by 1.
4. **My booking** → paste PNR → see ticket.
5. **Cancel ticket** → search again: seats went back up.
6. **Admin** → `admin` / `admin123` → add a flight → it appears in search → delete it.

---

## 6. How to read this code (learning path)

Open files in this order. After each file, say out loud what it does.

1. `database/schema.sql` — tables = your data model.
2. `pom.xml` — libraries Maven downloads.
3. `web.xml` — “send `/` to DispatcherServlet”.
4. `spring-mvc-servlet.xml` — IoC: scan package, JSP resolver, MySQL `dataSource`, `jdbcTemplate`.
5. `model/Flight.java` — a Java object that matches one row.
6. `dao/FlightDao.java` — SQL lives here. `@Autowired JdbcTemplate`.
7. `service/BookingService.java` — business rules (PNR, seats, cancel).
8. `controller/HomeController.java` — URL `/search` → view `search-results`.
9. `WEB-INF/views/index.jsp` — HTML form `method="get"` to `/search`.

### Annotations cheat sheet

| Annotation | Meaning |
|------------|---------|
| `@Controller` | This class handles HTTP URLs |
| `@Service` | Business logic |
| `@Repository` | Database access |
| `@Autowired` | “Spring, inject this bean” |
| `@GetMapping("/search")` | Handle GET `/search` |
| `@PostMapping("/book")` | Handle form submit |
| `@RequestParam` | Read `?source=Delhi` or form field |
| `@Transactional` | One DB transaction |

### Layers (why so many folders?)

- **Controller** talks to HTTP (never put SQL here).
- **Service** talks to rules (seats, PNR).
- **DAO** talks to SQL only.
- **Model** is just data.

That split is what examiners mean by “Spring MVC layered architecture”.

---

## 7. Project map

```
flight-booking/
├── pom.xml
├── database/schema.sql
├── src/main/java/com/flightbooking/
│   ├── controller/   Home, Booking, Admin
│   ├── service/
│   ├── dao/
│   └── model/
├── src/main/resources/db.properties
└── src/main/webapp/
    ├── css/style.css
    └── WEB-INF/
        ├── web.xml
        ├── spring-mvc-servlet.xml
        └── views/*.jsp
```

This is **not** Spring Boot. There is no `main()` method. Tomcat starts the app by reading `web.xml`.
