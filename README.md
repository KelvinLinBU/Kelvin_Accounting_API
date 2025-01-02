
# Kelvin Accounting API

The **Kelvin Accounting API** is a RESTful application built with **Spring Boot** to manage and dynamically generate accounting balance sheets. Following the **Model-View-Controller (MVC)** design pattern, the application integrates with a **MySQL** database for data persistence and provides professional **PDF generation** for financial statements.

## Features

- **CRUD Operations**: Perform Create, Read, Update, and Delete operations on:
  - Balance Sheets
  - Assets
  - Liabilities
  - Equities
- **PDF Generation**: Generate professional balance sheet PDFs dynamically based on database entries.
- **Spring Boot Framework**: Ensures robust and scalable implementation.
- **MySQL Database Integration**: Stores all accounting data persistently.
- **Future Plans**:
  - Add support for other financial statements, such as:
    - Income Statements
    - Cash Flow Statements
    - Statements of Retained Earnings
  - Introduce user authentication and role-based access control.

## Installation and Setup

### Prerequisites
- Java 17 or later
- Maven
- MySQL

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/Kelvin_Accounting_API.git
   cd Kelvin_Accounting_API
   ```

2. Configure the database:
   - Update the `src/main/resources/application.properties` file with your MySQL credentials:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/accounting_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the API at `http://localhost:8080`.

## Endpoints

### Balance Sheet Endpoints
- `POST /balancesheets`: Create a new balance sheet.
- `GET /balancesheets`: Retrieve all balance sheets.
- `GET /balancesheets/{id}`: Retrieve a specific balance sheet by ID.
- `PUT /balancesheets/{id}`: Update an existing balance sheet.
- `DELETE /balancesheets/{id}`: Delete a balance sheet by ID.
- `GET /balancesheets/{id}/pdf`: Generate a PDF for the specified balance sheet.

## Testing the API

### Using `curl`
1. Create a new balance sheet:
   ```bash
   curl -X POST http://localhost:8080/balancesheets    -H "Content-Type: application/json"    -d '{
       "date": "2025-01-02",
       "assets": [{"name": "Cash", "value": 5000}],
       "liabilities": [{"name": "Loan", "value": 3000}],
       "equities": [{"name": "Owner's Equity", "value": 2000}]
   }'
   ```

2. Generate a PDF for a balance sheet with ID 4:
   ```bash
   curl -X GET http://localhost:8080/balancesheets/4/pdf    -H "Accept: application/pdf" --output BalanceSheet_4.pdf
   ```

## Project Structure

- `src/main/java`
  - `controller`: Handles API requests and responses.
  - `model`: Contains entity classes for Balance Sheets, Assets, Liabilities, and Equities.
  - `repository`: Interfaces for database operations.
  - `service`: Implements business logic.
  - `util`: Utility classes, including PDF generation.
- `src/main/resources`: Configuration files, including `application.properties`.

## Future Enhancements
- Add support for other financial statements:
  - Income Statements
  - Cash Flow Statements
  - Statements of Retained Earnings
- Implement authentication and role-based access.
- Add audit logging for changes in financial data.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---

### Author
KelvinLinBU
