🏪 IIT Super Shop Management System

Efficiency in Every Transaction

IIT Super Shop is a desktop-based JavaFX project developed as part of the Design Patterns course. It provides a streamlined platform for managing products, orders, and users with role-based access. The system includes features such as product management, order tracking, and report generation, offering a fast, secure, and user-friendly experience for admins, employees, and customers.

⚙️ Features
🧑‍💼 User Authentication

Role-based login system (Admin, Employee)

Secure password storage

No registration — credentials managed by the admin

Role-specific dashboards

🛒 Product & Inventory Management

Add, edit, delete products

Update stock quantity using + and – buttons

Prevent duplicate product entries

Real-time product list updates

📦 Order Management



Employees can view and process orders

Automatic calculation of total price and order details

📊 Report Generation

Generate sales reports between two selected dates

Export reports as PDF or CSV

Automatically filter orders from the database using the date range

Professionally formatted PDF reports with titles, headers, and total summaries

📁 Dashboard Systems

Admin Dashboard: Manage products, users, and reports

Employee Dashboard: View and handle orders

🧠 Tech Stack
Frontend (UI Layer)

JavaFX

FXML Layouts

CSS Styling

MVC Architecture

Backend (Logic & Database Layer)

Java

SQLite Database

JDBC (Java Database Connectivity)

iText PDF Library (for PDF generation)

OpenCSV (for CSV export)

📂 Project Structure
IITSuperShop/
├── src/
│   ├── com/ecommerce/
│   │   ├── controller/      # JavaFX Controllers
│   │   ├── model/           # Data Models (Product, Order, User)
│   │   ├── database/        # Database connection logic
│   │   ├── report/          # Report generation (PDF/CSV)
│   │   └── main/            # Application entry point
│   └── resources/
│       ├── fxml/            # FXML layout files
│       ├── css/             # Stylesheets
│       └── images/          # UI assets
├── database/
│   └── shop.db              # SQLite database file
└── pom.xml                  # Maven build configuration

🚀 Getting Started
Prerequisites

Java 17+

Maven

JavaFX SDK

SQLite

Installation
1️⃣ Clone the Repository
git clone https://github.com/your-username/IITSuperShop.git
cd IITSuperShop

2️⃣ Build the Project
mvn clean install

3️⃣ Run the Application

Via IDE:
Run Main.java directly.

Via Terminal:

mvn javafx:run

⚙️ Environment Configuration

No external .env file is required.
However, ensure that the SQLite database file (shop.db) is placed in the correct directory.

🧾 Features in Detail
👥 For Admins

Manage products (add, update, remove)

Adjust stock quantities

Generate and export reports (PDF/CSV)

Manage employee and customer records

👨‍💻 For Employees

View product list

Process customer orders

View order list and details

🔒 Security Features

Role-based access control

Input validation and SQL protection

Restricted access for employees and customers

Date validation for report generation

🧭 Report Generation
📅 How It Works

User selects From Date and To Date using JavaFX DatePicker.

Orders are filtered from the database where

order_date BETWEEN fromDate AND toDate


Results are formatted and exported as:

PDF Report — Styled table format with header and totals.

CSV Report — Spreadsheet-friendly data output.


🖥️ Demo UI Highlights
Login Screen

Simple login with role-based redirection.

Admin Dashboard

Product management and report generation tools.


📈 Future Enhancements

Graphical sales analytics (bar/line charts)

Password encryption using BCrypt

Cloud-based synchronization

Enhanced UI with animations and themes

🤝 Contributing

Fork the repository

Create a new feature branch:

git checkout -b feature/AmazingFeature


Commit your changes:

git commit -m "Add AmazingFeature"


Push to your branch and open a pull request

🪪 License

This project is licensed under the MIT License — see the LICENSE
 file for details.

👏 Acknowledgments

Built using JavaFX, Maven, and SQLite

PDF generation powered by iText

CSV export using OpenCSV

UI designed with FXML and CSS

Developed under the supervision of Dr. Mohammad Shoyaib, Professor, IIT, DU

🧑‍💻 Developed By

Nafiz Mahmud Fardin
Institute of Information Technology (IIT),
University of Dhaka
