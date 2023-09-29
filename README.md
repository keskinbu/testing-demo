# Library Project

![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)
[![codecov](https://codecov.io/gh/keskinbu/testing-demo/graph/badge.svg?token=B64JLFOU49)](https://codecov.io/gh/keskinbu/testing-demo)

Library Project is an open-source project that demonstrates a simple library management system, focusing on book
operations. It's implemented with Kotlin, Spring Boot, and uses PostgreSQL as its database.

## Features

- CRUD operations for books:
    - List all books
    - Get a book by its ID
    - Add a new book
    - Update an existing book
    - Delete a book

- API Documentation using Swagger UI.

## Getting Started

### Prerequisites

- JDK 1.8 or higher
- Docker (for PostgreSQL setup)

### Setting up the Project

1. **Clone the repository:**
    ```bash
    git clone git@github.com:keskinbu/testing-demo.git
    ```

2. **Navigate to the project directory and start the database using Docker:**
    ```bash
    cd testing-demo
    docker-compose up
    ```

3. **Run the Spring Boot application:**
    ```bash
    ./gradlew bootRun
    ```

4. **Access the application:**
   Open a browser and navigate to [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/) to see the API
   documentation and try out the endpoints.

## Contributions

We welcome contributions from the community. If you'd like to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add a new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a Pull Request.

## License

This project is open-source and available under the [MIT License](LICENSE).
