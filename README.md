# NeoBank360

A full-stack digital banking platform designed for modern financial experiences. NeoBank360 brings together customer banking workflows, admin management, financial insights, and secure transaction handling in a unified application.

## Overview

NeoBank360 is built as a multi-module project with:

- Frontend: Angular application for the customer and admin experience
- Backend: Spring Boot REST API with JWT-based authentication and authorization
- Database: MySQL for banking, account, loan, and transaction data

The platform is designed to support account management, bill budgeting, reward tracking, loan applications, financial analytics, and administrative monitoring.

## Key Features

### Customer Features
- User registration and login
- Dashboard with account overview and financial summary
- Open new bank accounts
- View and manage transactions by account
- Budget and bill tracking
- Rewards and incentives dashboard
- Loan application and repayment workflows
- Personalized insights for spending and financial health

### Admin Features
- Admin dashboard
- Account request review and management
- User management
- Loan product creation and decisioning
- Analytics and system health monitoring
- Transaction and platform audit visibility

### Security and Architecture
- JWT-based authentication and role-based access control
- Route protection for authenticated and admin-specific pages
- RESTful API backend with Spring Security
- Hibernate/JPA persistence layer

## Tech Stack

### Frontend
- Angular 21
- TypeScript
- Material Design + Bootstrap
- Tailwind CSS
- Chart.js / ng2-charts

### Backend
- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- MySQL Connector

### Database
- MySQL

## Project Structure

```text
NeoBank360/
├── README.md
├── neobank-fe/                  # Angular frontend
│   ├── src/
│   ├── package.json
│   └── README.md
├── neobank-be/                 # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── mvnw
├── neobank-z-DB/               # SQL scripts and database setup
│   └── database_script.sql
└── .gitignore
```

## Prerequisites

Before running the project, make sure you have the following installed:

- Node.js 20+ and npm
- Angular CLI 21+
- Java 21
- Maven 3.9+
- MySQL 8+
- Git

## Database Setup

1. Create a MySQL database named `neobank_db`.
2. Update your MySQL credentials in the backend configuration file if needed.
3. Import or use the SQL scripts in `neobank-z-DB/database_script.sql`.

The backend configuration is defined in:

```properties
neobank-be/src/main/resources/application.properties
```

Default datasource configuration uses:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/neobank_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

### 1. Start the Backend

From the `neobank-be` folder:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

The Spring Boot API runs on:

```text
http://localhost:8080
```

### 2. Start the Frontend

From the `neobank-fe` folder:

```bash
npm install
npm start
```

Or use the Angular CLI directly:

```bash
npx ng serve
```

The frontend runs on:

```text
http://localhost:4200
```

## Default Admin Account

The SQL script includes an admin user setup:

```sql
UPDATE users SET role = 'Admin' WHERE email ='admin@gmail.com';
```

Use the admin email as a reference when testing admin flows:

```text
admin@gmail.com
```

## Main Application Routes

The frontend includes routes for:

- `/` — landing page
- `/login` — login
- `/register` — registration
- `/dashboard` — customer dashboard
- `/accounts` — account overview
- `/transactions/:accountId` — transaction details
- `/budget` and `/budget/create` — budgeting
- `/bills` and `/bills/create` — bill management
- `/loan/my-loans`, `/loan/apply`, `/loan/repayment/:id` — loan flows
- `/insights` — financial insights
- `/admin` — admin panel
- `/admin/dashboard` — admin analytics
- `/admin/account-requests` — account approvals
- `/admin/loan-products` and `/admin/loan-products/create` — loan product management

## Useful Commands

### Frontend
```bash
cd neobank-fe
npm install
npm start
npm run build
```

### Backend
```bash
cd neobank-be
./mvnw clean install
./mvnw spring-boot:run
```

## Notes

- The project is structured for a banking system demo and can be extended for production use.
- The backend uses Spring Security and JWT for protected endpoints.
- Database initialization and seed data can be managed through the SQL script in `neobank-z-DB`.

## Contribution

Contributions are welcome. For improvements, bug fixes, or feature additions:

1. Create a feature branch.
2. Make your changes.
3. Test locally.
4. Open a pull request with a clear description.

## License

No explicit license file is currently present in the repository. Please check with the project owner before commercial use or redistribution.

---

Built for secure, modern digital banking workflows with Angular + Spring Boot + MySQL.
