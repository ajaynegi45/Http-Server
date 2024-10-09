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

- **Multi-Threaded Architecture**: Supports multiple concurrent TCP connections using threading, ensuring efficient handling of multiple requests simultaneously.
- **Lightweight**: The server is minimal in size, focusing on simplicity without unnecessary dependencies.
- **Highly Scalable**: Designed to scale effectively as the number of connections grows.
- **Secure**: Security best practices are baked into the core, providing a secure base for handling HTTP requests.
- **Customizable**: The codebase is structured to allow users to easily extend or customize functionality by adding new HTTP methods, request handlers, or other features.

## Core Components

- **Configuration** : Manages server configuration such as the port number and web root directory. The `Configuration` class allows you to specify parameters at runtime, enabling flexible deployment options.

- **HTTP Methods** : The server supports various HTTP methods, making it a fully functional HTTP server capable of handling:
  - **GET**: Retrieve information from the server.
  - **POST**: Submit data to the server.
  - **PUT**: Update resources on the server.
  - **DELETE**: Remove resources from the server.
  - **HEAD**: Retrieve headers for a resource without its body.
  - **CONNECT, OPTIONS, TRACE, PATCH**: These methods are supported to enhance server capability.

- **Request Parsing** : The `HttpParser` class is responsible for parsing incoming HTTP requests, including headers, methods, body content, and request targets. It ensures that the requests conform to the correct HTTP version and structure.

- **Threading for Performance** : Each incoming request is handled in a separate thread (`HttpConnectionWorkerThread`), ensuring that the server can handle multiple clients at once without blocking.

- **Error Handling** : Custom exceptions such as `HttpConfigurationException`, `HttpParsingException`, and `BadHttpVersionException` are used to handle various error scenarios gracefully, ensuring the server can recover or return appropriate error responses.

- **JSON Utilities** : The `Json` utility class simplifies JSON handling, allowing easy conversion between Java objects and JSON format. This is particularly useful for building APIs that need to return JSON responses.

- **Extensibility** : This server is designed with extensibility in mind. You can add custom HTTP methods, modify the request handling, or implement new features without affecting the existing codebase. This makes it perfect for projects that require custom handling of HTTP requests.

## How It Works

The server listens for incoming connections on a specified port (default is `8080`). When a client sends a request, the server parses the HTTP request, checks its validity, and responds accordingly. Each connection is handled in a separate thread, allowing multiple clients to interact with the server concurrently.

### Main Components

1. **ServerListenerThread**: Listens for incoming connections and creates new worker threads for each request.
2. **HttpConnectionWorkerThread**: Handles the actual processing of the HTTP request, including parsing and responding.
3. **HttpParser**: Parses incoming HTTP requests, handling the method, headers, body, and version.
4. **ConfigurationManager**: Manages server configuration, such as port number and webroot directory.
5. **HttpRequest**: Represents the parsed HTTP request, including method, headers, body, and version.
   
### Flow Diagram
<img src="https://github.com/ajaynegi45/Http-Server/blob/main/project-structure/httpserver.png" height="500px" alt="Diagram" />


# How to Contribute to this Project

We welcome contributions! Before you start, please read the [Contributing Guidelines](https://github.com/ajaynegi45/Http-Server/blob/main/contributing.md) file, which contains important guidelines to make the contribution process smoother, especially for newcomers.
Here's a quick overview:

- **Clone the repository**
  
   ``` bash
  git clone https://github.com/ -yourusername- /Http-Server.git

  cd Http-Server
  ```
  
# Acknowledgements

This project wouldn't be possible without the contributions of our amazing community. Thank you for being part of our journey! 🙌

<a href = "https://github.com/ajaynegi45/Http-Server/graphs/contributors">
  <img src = "https://contrib.rocks/image?repo=ajaynegi45/Http-Server"/>
</a>

<br/>


