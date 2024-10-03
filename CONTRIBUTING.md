# Contributing to Http-Server

Thank you for considering contributing to the Http-Server project! We welcome contributions from everyone and aim to make the process as smooth as possible. This document outlines the steps for setting up the development environment, contributing to the project, and some best practices to follow.

## Table of Contents

- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Setting Up the Development Environment](#setting-up-the-development-environment)
- [Contributing Guidelines](#contributing-guidelines)
- [Code of Conduct](#code-of-conduct)


## Getting Started

To get started with the Http-Server project, please follow these instructions to set up your local development environment.

## Project Structure

The Http-Server project is organized into several key modules:

- `com.httpserver.config`: Contains configuration management classes.
- `com.httpserver.core`: Contains core server functionalities, including handling HTTP connections.
- `com.httpserver.utils`: Contains utility classes for JSON processing and other functionalities.
- `com.httpserver.http`: Handles parsing of HTTP requests, including methods, headers, version management, and the parser itself.

## Setting Up the Development Environment

1. **Prerequisites**:
    - Java Development Kit (JDK) 11 or later.
    - Apache Maven for building the project.
    - An IDE like IntelliJ IDEA or Eclipse (optional but recommended).

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/Http-Server.git
   cd Http-Server
   ```
   
3. **Build the Project**: You can build the project using Maven:
    ```bash
    mvn clean install
   ```
4. **Start the Server**: Run the server using the command:   
   ```bash
   java -jar target/httpserver-0.0.1-SNAPSHOT.jar src/main/resources/http.json
   ```

## Contributing Guidelines

1. **Create a New Branch**: Create a new branch for your feature or fix:
   ```bash
   git checkout -b my-feature-branch
   ```

2. **Make Your Changes**: Implement your changes, ensuring they adhere to the project’s coding standards.

3. **Run Tests**: Ensure that all tests pass by running:
   ```bash
   mvn test
   ```

4. **Commit Your Changes**: Commit your changes with a clear and concise commit message:
   ```bash
   git commit -m "Add feature XYZ"
   ```

5. **Push to Your Fork**: Push your changes to your forked repository:
   ```bash
   git push origin my-feature-branch
   ```

6. **Create a Pull Request**: Go to the original repository and create a pull request, describing your changes and why they are beneficial.


## Code of Conduct

### Our Pledge

We pledge to make participation in our project a harassment-free experience for everyone.

### Our Standards

**Positive behavior:**
- Use inclusive language
- Respect differing viewpoints
- Accept constructive criticism
- Show empathy

**Unacceptable behavior:**
- Harassment or abuse
- Insulting comments
- Personal attacks
- Publishing private information

### Responsibilities

Maintainers will clarify acceptable behavior and take action against violations. They may remove contributions that don’t align with this Code.

### Scope

This Code applies in project spaces and public spaces when representing the project.

### Acknowledgement

Adapted from the [Contributor Covenant](https://www.contributor-covenant.org).
