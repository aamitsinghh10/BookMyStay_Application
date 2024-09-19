# BookMyStay_Application

## Overview
BookMyStay is a Spring Boot REST API web service designed for an online hotel booking application. Developed using Core Java and the Spring Framework, this application leverages a MySQL database for data storage and CRUD operations. It enables customers to browse and book hotels while providing comprehensive management functionalities for inventory and customer orders.

## Tech Stack and Tools
- **Core Java:** Programming language used for development.
- **Spring Framework:** Provides infrastructure support for Java applications.
- **Spring Boot:** Simplifies setup and development of Spring applications.
- **Spring Data JPA:** Facilitates database access with repository abstraction.
- **Spring Validation:** Handles validation of user inputs and data.
- **Hibernate:** ORM tool for mapping Java objects to database tables.
- **MySQL:** Relational database management system for data storage.
- **Lombok:** Reduces boilerplate code in Java.
- **JUnit:** Framework for unit testing Java applications.
- **ER-Diagram:** Visual representation of the database schema.

## Modules
- **Login & Logout Module:** Manages user authentication and session management.
- **Admin Module:** Allows administrators to manage hotels and rooms, view all entities.
- **Customer Module:** Enables customers to register, log in, search for hotels, view details, and make reservations.
- **Hotel Module:** Lets hotels manage profiles, including amenities, room types, and promotions.
- **Room Module:** Handles room-specific operations, such as displaying details and availability.
- **Reservation Module:** Manages booking operations, allowing customers to reserve rooms.

## Features

### Admin Features
- **Add Hotel/Room:** Admins can add new hotels and rooms.
- **Delete Hotel/Room:** Admins can remove hotels and rooms.
- **View All Hotels/Rooms:** Admins can view details of all listed hotels and rooms.

### Customer Features
- **Register Account:** Customers can create new accounts.
- **Login/Logout:** Customers can log in and log out of their accounts.
- **Delete Account:** Customers can delete their accounts.
- **Search Hotels/Rooms:** Customers can search for hotels and rooms based on various criteria.
- **View Details:** Customers can view detailed information about hotels and rooms.
- **Make Reservation:** Customers can book rooms at selected hotels.

### Hotel Features
- **Register Account:** Hotels can create accounts for profile management.
- **Login/Logout:** Hotels can log in and manage their details.
- **Display Amenities:** Hotels can showcase available amenities.
- **Showcase Room Types:** Hotels can provide information about different room types.
- **Provide Location Information:** Hotels can display their location.
- **Display Ratings and Reviews:** Hotels can show customer ratings and reviews.
- **Showcase Photos and Virtual Tours:** Hotels can add photos and virtual tours.
- **Offer Promotions and Discounts:** Hotels can set up special promotions and discounts.

## Installation & Running Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/aamitsinghh10/BookMyStay.git
