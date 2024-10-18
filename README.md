# Own HTTP Server

This project implements a lightweight and highly scalable HTTP server written in Java. It aims to handle basic HTTP requests and responses while providing an easily extensible and customizable architecture.

The HTTP server is designed with performance, security, and simplicity in mind. It supports the most common HTTP methods like GET, POST, PUT, DELETE, and more, allowing developers to add new features or extend functionality without modifying the core structure.

Our primary goal is to keep this server lightweight and efficient, ensuring minimal resource usage without sacrificing functionality.

## Table of Contents

- [Key Features](#key-features)
- [Core Components](#core-components)
- [How It Works](#how-it-works)
- [Main Components](#main-components)
- [Flow Diagram](#flow-diagram)
- [How To Contribute](#how-to-contribute-to-this-project)
- [Acknowledgements](#acknowledgements)

## Key Features

### Multi-Threaded Architecture

* **Multi-Threaded Architecture**: Supports multiple concurrent TCP connections using threading, ensuring efficient handling of multiple requests simultaneously.

- The server is built using a  **multi-threaded architecture** , which allows it to handle multiple tasks simultaneously. In simpler terms, think of multi-threading as having multiple "workers" available to take on different tasks at the same time. Each incoming request is assigned to a separate thread (a lightweight unit of a program), enabling the server to manage multiple requests at once without waiting for one task to finish before starting another.

### Lightweight Design

The server's **lightweight** nature means it is designed to be minimalistic and focused. This project deliberately avoids using heavy frameworks or libraries that can make the code more complex and difficult to understand for beginners.

* **Minimal Dependencies:** There are no unnecessary external libraries or frameworks used in the server. This makes it easier to set up, understand, and maintain. It also reduces the memory and processing power required to run the server, making it ideal for situations where resources are limited.
* **Simplified Codebase:** The code is written in a straightforward manner, focusing on core functionality without added complexity. This is helpful for newcomers who want to understand the fundamentals of how an HTTP server operates without being overwhelmed by advanced or abstract concepts.

### Highly Scalable

The server is designed to be  **highly scalable** , meaning it can handle an increasing number of requests without a significant drop in performance.

* **What is Scalability?** Scalability refers to the ability of a system to accommodate growing amounts of work or an increasing number of users. In this server, scalability is achieved through efficient use of resources, such as memory and processor time, as well as through the multi-threaded architecture that allows for parallel request processing.
* **Handling Large Numbers of Connections:** As the number of users or devices sending requests to the server increases, the server’s design ensures that it can continue to process these requests efficiently. The multi-threaded approach, combined with optimized algorithms for handling requests, helps maintain a smooth user experience even under heavy load.

### Secure

**Security** is an essential aspect of web servers, and this server integrates fundamental security best practices to provide a reliable and secure environment for handling HTTP requests.

* **Basic Security Features:** The server includes mechanisms for validating incoming requests to ensure they follow the correct structure and meet certain criteria before being processed. This helps prevent malicious or malformed requests from causing issues.
* **Preventing Common Vulnerabilities:** Security features such as input validation, error handling, and secure connection handling are built into the core server design. These practices protect against common web security issues, such as injection attacks or buffer overflows.

### Customizable

The server's codebase is designed to be  **customizable** , making it easy for developers to extend its functionality or modify existing features according to their needs.

* **Adding New Features:** You can easily introduce new HTTP methods (like PATCH) or add custom request handlers for specific types of data processing. The server's modular design allows you to make these changes without altering the core components, ensuring that the basic functionality remains intact.
* **Modifying Request Handling:** If you need special processing for certain types of requests, the server's architecture allows you to tweak the way requests are handled. For example, you could add custom headers, implement authentication checks, or change the response format to suit your application's requirements.
* **Flexible Integration:** The server can be adapted to work with various back-end services, databases, or third-party APIs. This makes it suitable for different use cases, from educational projects to real-world applications.

## Core Components

### Configuration

The **Configuration** component plays a crucial role in managing the server's customizable settings. It allows users to define various parameters that dictate the server's behavior, such as the **port number** where the server listens for incoming requests and the **web root directory** that serves as the base folder for static files or resources. The `Configuration` class provides an easy-to-use interface for specifying these parameters at runtime, allowing for flexible and dynamic deployment.

#### Key Configuration Settings

1. **Port Number:**
   * The **port number** is a fundamental setting that determines which network port the server uses to listen for incoming HTTP requests.
   * By default, many HTTP servers run on port `80` for regular HTTP traffic or port `443` for HTTPS traffic. For development purposes, common alternative ports like `8080` or `3000` are often used.
   * In the `Configuration` class, the port number can be set programmatically or through a configuration file, making it easy to adjust for different environments (e.g., development, testing, production) without changing the code.
   * **Example Use Case:** You might set the port number to `8080` for local development and `80` for production deployment. The configuration allows for easy switching between these setups.
2. **Web Root Directory:**
   * The **web root directory** specifies the base folder from which the server serves static files (such as HTML, CSS, images, and JavaScript files).
   * This directory is essential for delivering web content to clients. It can be configured to point to different folders, enabling a flexible file structure.
   * You can set the web root to a specific directory path through the `Configuration` class, which allows for different setups based on the deployment environment.

#### Why Is Configuration Important?

The configuration is vital because it makes the server adaptable to various environments and use cases. By externalizing these settings, you can easily:

* **Change Server Settings Without Code Changes:** Adjusting the port number or web root directory doesn't require modifying the server's source code. This separation of configuration and code follows best practices, making the server more maintainable.
* **Support Multiple Deployment Environments:** Easily switch settings between development, testing, and production environments. This is especially useful in continuous integration/continuous deployment (CI/CD) pipelines, where different environments require different configurations.
* **Enable Dynamic Parameter Adjustments:** The `Configuration` class can read settings from environment variables, configuration files, or even command-line arguments. This flexibility is beneficial for containerized deployments (e.g., Docker), where environment variables are often used to configure applications.

#### How to Use the `Configuration` Class

To make use of the `Configuration` class in the server, follow these steps:

1. **Setting Parameters Programmatically:**
   * You can directly set configuration parameters in the code. For example:
   * This approach is suitable for basic testing or development.

     ```java
     Configuration config = new Configuration();
     config.setPort(8080);
     config.setWebRoot("src/main/resources/static");
     ```
2. **Loading Configuration from a File:**
   * The server can read settings from an external configuration file (e.g., `config.properties`), making it easy to adjust parameters without recompiling the code:
   * ```properties
     port=8080
     webRoot=src/main/resources/static
     ```
   * The `Configuration` class can be extended to load these settings, allowing for dynamic adjustments.
3. **Environment Variables or Command-Line Arguments:**
   * For more advanced setups, you can use environment variables or command-line arguments to configure the server:
   * ```bash
     java -jar HttpServer.jar --port=8080 --webRoot=src/main/resources/static
     ```
   * This is particularly useful for deployment scenarios where different environments require different configurations.

- 
- **Request Parsing** : The `HttpParser` class is responsible for parsing incoming HTTP requests, including headers, methods, body content, and request targets. It ensures that the requests conform to the correct HTTP version and structure.
- **Threading for Performance** : Each incoming request is handled in a separate thread (`HttpConnectionWorkerThread`), ensuring that the server can handle multiple clients at once without blocking.
- **Error Handling** : Custom exceptions such as `HttpConfigurationException`, `HttpParsingException`, and `BadHttpVersionException` are used to handle various error scenarios gracefully, ensuring the server can recover or return appropriate error responses.
- **JSON Utilities** : The `Json` utility class simplifies JSON handling, allowing easy conversion between Java objects and JSON format. This is particularly useful for building APIs that need to return JSON responses.
- **Extensibility** : This server is designed with extensibility in mind. You can add custom HTTP methods, modify the request handling, or implement new features without affecting the existing codebase. This makes it perfect for projects that require custom handling of HTTP requests.

### HTTP Methods

The HTTP server supports a variety of HTTP methods, making it a fully functional server capable of handling different types of requests. Each HTTP method represents a specific operation to be performed on a given resource. Understanding these methods is essential for contributors who want to extend the server's functionality or create custom request handlers.

#### 1.  **GET** : Retrieve Information

* **Purpose** : The `GET` method is used to request data from a specified resource. It is the most commonly used HTTP method and is considered a "safe" method because it does not modify the resource on the server.
* **Idempotent** : Multiple identical `GET` requests should have the same effect as a single request.
* **Cacheable** : Responses to `GET` requests can be cached by the browser to improve performance.
* **Use Cases** :
* Fetching a web page's content.
* Retrieving a list of items from a server (e.g., a list of users or products).
* **Example** : Requesting a user profile page.

```vbnet
GET /users/123 HTTP/1.1
Host: example.com
```

#### 2.  **POST** : Submit Data to the Server

* **Purpose** : The `POST` method is used to send data to the server to create a new resource or submit form data. It is commonly used for actions like submitting a form, uploading a file, or posting a comment.
* **Not Idempotent** : Sending the same `POST` request multiple times may result in different outcomes (e.g., creating multiple resources).
* **Supports Large Payloads** : Can include a request body with data in various formats (e.g., JSON, form data).
* **Use Cases** :
* Creating a new user account.
* Submitting a form on a website.
* Uploading a file or sending complex data to the server.
* **Example** : Submitting a new blog post.

```bash
POST /posts HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "title": "New Post",
  "content": "This is the content of the new post."
}
```

#### 3.  **PUT** : Update or Replace a Resource

* **Purpose** : The `PUT` method is used to update an existing resource or create a new resource if it does not already exist. It typically replaces the entire content of the specified resource.
* **Idempotent** : Repeating the same `PUT` request will have the same effect as making it once.
* **Full Replacement** : Generally replaces the entire content of the target resource.
* **Use Cases** :
* Updating user information (e.g., changing an email address).
* Replacing an entire document or resource with new data.
* **Example** : Updating user information.

```bash
PUT /users/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "name": "John Doe",
  "email": "johndoe@example.com"
}
```

#### 4.  **DELETE** : Remove a Resource

* **Purpose** : The `DELETE` method is used to delete a specified resource from the server. Once deleted, the resource is no longer accessible.
* **Idempotent** : Performing a `DELETE` request multiple times results in the same state (resource deleted).
* **Use Cases** :
* Deleting a user account.
* Removing an item from a list.
* **Example** : Deleting a blog post.

```bash
DELETE /posts/123 HTTP/1.1
Host: example.com
```

#### 5.  **HEAD** : Retrieve Headers Only

* **Purpose** : The `HEAD` method is similar to `GET`, but it retrieves only the headers of a resource, not the body. It is useful for checking resource metadata (like content type or length) without downloading the content.
* **Idempotent and Safe** : Like `GET`, it does not modify the resource.
* **Use Cases** :
* Checking if a resource exists before making a `GET` request.
* Determining the size of a file to be downloaded.
* **Example** : Checking if a file exists on the server.

```bash
HEAD /files/report.pdf HTTP/1.1
Host: example.com
```

### Flow Diagram

## How It Works

The HTTP server operates by continuously listening for incoming connections on a specified port, which defaults to `8080`. Upon receiving a request from a client, the server executes several critical steps to ensure a smooth interaction:

1. **Listening for Connections** :

* The server initializes a socket on the designated port and enters a loop to accept incoming connections. This is achieved through the `ServerListenerThread`, which actively listens for client requests.

1. **Request Handling** :

* Once a connection is established, the server reads the incoming data stream. It processes the HTTP request by invoking the `HttpParser`, which interprets the request line, headers, and body.
* The server performs validation checks to ensure the request adheres to the HTTP protocol specifications, including correct method usage and header formatting.

1. **Response Generation** :

* Based on the parsed information and any required business logic, the server constructs an appropriate HTTP response. This response is then sent back to the client over the same connection.

1. **Multi-Threading** :

* To accommodate multiple simultaneous connections, each client request is handled in a separate thread. The `HttpConnectionWorkerThread` is responsible for processing individual requests without blocking other incoming requests, enabling efficient concurrent handling.

### Main Components

1. **ServerListenerThread** :

* **Function** : This component is responsible for establishing the initial point of contact for clients. It listens for incoming TCP connections and creates a new `HttpConnectionWorkerThread` for each connection.
* **Details** : The thread operates in a loop, using methods such as `ServerSocket.accept()` to accept connections, ensuring that the server can handle multiple requests in parallel.

1. **HttpConnectionWorkerThread** :

* **Function** : This thread takes charge of processing individual HTTP requests once a connection is accepted.
* **Details** : It invokes the `HttpParser` to dissect the request, manages the response generation, and sends the output back to the client. By dedicating a thread to each connection, the server ensures responsiveness and scalability.

1. **HttpParser** :

* **Function** : This utility class is designed to parse incoming HTTP requests.
* **Details** : It breaks down the request into its components: the HTTP method, request URI, headers, and body. It handles various scenarios, including malformed requests, by throwing appropriate exceptions.

1. **ConfigurationManager** :

* **Function** : This component manages server configuration parameters.
* **Details** : It allows for dynamic setting of configurations such as the port number, webroot directory, and other runtime parameters, enabling flexible deployment options. The settings can be loaded from configuration files or specified at startup.

1. **HttpRequest** :

* **Function** : This class represents the parsed HTTP request.
* **Details** : It encapsulates the HTTP method, headers, body content, and version information. This object-oriented design allows for easy access and manipulation of request data throughout the processing lifecycle.

<img src="https://github.com/ajaynegi45/Http-Server/blob/main/project-structure/httpserver.png" height="500px" alt="Diagram" />

# How to Contribute to This Project

We welcome contributions from developers of all levels! Your involvement can significantly enhance the capabilities of this project and foster a collaborative community. Before you start contributing, please take a moment to read our [Contributing Guidelines](https://github.com/ajaynegi45/Http-Server/blob/main/contributing.md) file. This document outlines essential practices and policies to ensure a smooth contribution process, especially for newcomers.

## Getting Started

Here’s a quick overview of the steps to contribute:

### 1. Fork the Repository

* Visit the [GitHub repository](https://github.com/ajaynegi45/Http-Server) and click on the "Fork" button in the upper right corner. This action creates a personal copy of the repository under your GitHub account, allowing you to make changes without affecting the main codebase.

### 2. Clone the Repository

* After forking the repository, you need to clone it to your local machine for development. Use the following command in your terminal:

  ```bash
  git checkout -b feature/your-feature-name
  Replace `yourusername` with your actual GitHub username. This command creates a local copy of the repository.
  ```

### 3. Navigate to the Project Directory

* Change to the project directory to begin working on it:
  ```bash
  cd Http-Server
  ```

### 4. Create a New Branch

* Before making any changes, create a new branch for your feature or bug fix. This helps keep your work organized and makes it easier to submit your changes:

  ```bash
  git checkout -b feature/your-feature-name
  Replace your-feature-name with a descriptive name related to your changes.
  ```

### 5. Make Your Changes

* Open the project in your preferred IDE or text editor. Implement your changes, and ensure to follow the coding standards and conventions outlined in the project documentation. Aim for clear, maintainable code, and add comments where necessary to explain complex logic.

### 6. Test Your Changes

* Run the server and execute any relevant tests to verify that your changes work as expected. Make sure to adhere to the project's testing guidelines and ensure that existing functionality remains intact. If new features are added, include corresponding unit tests to validate their functionality.

### 7. Commit Your Changes

* Once you are satisfied with your modifications, stage your changes and commit them to your branch:

  ```bash
  git add .
  git commit -m "Brief description of your changes"
  Use a clear and concise commit message that explains the purpose of your changes.
  ```

### 8. Push Your Changes

* Push your branch to your forked repository on GitHub:
  ```bash
  git push origin feature/your-feature-name
  ```

### 9. Create a Pull Request

* Navigate to the original repository on GitHub. You will see a prompt to create a pull request for your recently pushed branch. Click on "Compare & pull request," fill out the form with relevant information, and submit your pull request for review.

### 10. Participate in the Review Process

* Engage with the project maintainers and other contributors during the review process. Be open to feedback and make any requested changes to improve your contribution.

### 11. Stay Updated

* Keep your forked repository updated with the main project by regularly pulling changes from the original repository to avoid merge conflicts:

  ```bash
  git remote add upstream https://github.com/ajaynegi45/Http-Server.git
  git fetch upstream
  git merge upstream/main
  ```

## Additional Notes

* Always respect the project’s code of conduct and adhere to any coding standards set forth in the project documentation.
* Before submitting significant features or changes, consider discussing them in the project's issue tracker or communication channels to ensure alignment with the project goals.

By following these steps, you can contribute effectively to the HTTP server project. Your contributions help enhance the server's functionality and support the community of developers who rely on it.

# Acknowledgements

This project wouldn't be possible without the contributions of our amazing community. Thank you for being part of our journey! 🙌

`<a href = "https://github.com/ajaynegi45/Http-Server/graphs/contributors">`
  `<img src = "https://contrib.rocks/image?repo=ajaynegi45/Http-Server"/>`
`</a>`

<br/>
