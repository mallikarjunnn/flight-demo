-- Run this once in MySQL Workbench or: mysql -u root -p < database/schema.sql

CREATE DATABASE IF NOT EXISTS flight_booking;
USE flight_booking;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS admins;

CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE flights (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    airline VARCHAR(80) NOT NULL,
    source VARCHAR(80) NOT NULL,
    destination VARCHAR(80) NOT NULL,
    departure_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL
);

CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pnr VARCHAR(20) NOT NULL UNIQUE,
    flight_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    seats_booked INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

INSERT INTO admins (username, password) VALUES ('admin', 'admin123');

INSERT INTO flights
(flight_number, airline, source, destination, departure_date, departure_time, arrival_time, price, total_seats, available_seats)
VALUES
('AI-101', 'Air India',     'Delhi',     'Mumbai',    '2026-09-15', '08:00:00', '10:15:00', 4500.00, 180, 180),
('6E-202', 'IndiGo',        'Mumbai',    'Bengaluru', '2026-09-15', '11:30:00', '13:10:00', 3200.00, 180, 180),
('SG-303', 'SpiceJet',      'Delhi',     'Goa',       '2026-09-16', '06:45:00', '09:20:00', 5100.00, 150, 150),
('UK-404', 'Vistara',       'Bengaluru', 'Delhi',     '2026-09-16', '17:00:00', '19:40:00', 6200.00, 160, 160),
('AI-505', 'Air India',     'Chennai',   'Hyderabad', '2026-09-17', '09:15:00', '10:30:00', 2800.00, 140, 140),
('6E-606', 'IndiGo',        'Delhi',     'Mumbai',    '2026-09-15', '19:00:00', '21:10:00', 3900.00, 180, 180);
