# Contributing to Http-Server

Welcome! 🎉 Whether you're a seasoned developer or just getting started, we appreciate your interest in contributing to our project. Let's work together to make this lightweight, scalable HTTP server even better! 💻🚀

If you have any questions, doubts, or suggestions about contributing (or anything else), feel free to ping me on [LinkedIn](https://www.linkedin.com/in/ajaynegi45/) or [Twitter](https://x.com/ajaynegi45) anytime. I'll do my best to reply as quickly as possible.

> Our primary goal is to keep this server lightweight and efficient, ensuring minimal resource usage without sacrificing functionality.

## How to Contribute

You can contribute in a number of ways, depending on your skills and interests:

### 1. Code Contributions

- **Fix a Bug** 🐛  
  If you encounter any bugs, please check if an issue already exists. If not, feel free to open a new one, or if you’re up for it, submit a pull request with a fix.

- **Suggest a Feature** 🌟  
  Have an idea for a new feature? Open an issue to discuss it! Or, if you’re ready, implement the feature and submit a pull request.

- **Improve Performance** ⚡  
  This server is all about efficiency. If you can optimize performance, contribute your code and help us scale better.

### 2. Non-Code Contributions

- **Documentation** 📚  
  If writing is your strength, help us improve the documentation to make the project more accessible. Better documentation helps everyone!

- **Community Engagement**  
  Answer questions, share the project, or help review issues and pull requests. Every bit helps!

---

## Development Setup

Before you start contributing, here’s how you can set up your environment:

### Prerequisites

Make sure you have the following installed on your local machine:
- **Java JDK 11+**  
- **Maven**  
- An IDE like **IntelliJ IDEA** or **Eclipse** (optional but recommended)
- ⭐ Star the repository and show your support. ***(Very Important)***

### Getting Started

1. **Fork the repository**  
   Go to the GitHub repo and click **Fork** to create a copy on your GitHub account.

2. **Clone your fork locally**  
   Clone your fork to your local machine:
   ```bash
   git clone https://github.com/<your-username>/Http-Server.git
   cd Http-Server
   ```

3. **Build the project**  
   Use Maven to build the project:
   ```bash
   mvn clean install
   ```

4. **Run the server**  
   Start the server:
   ```bash
   java -jar target/httpserver-0.0.1-SNAPSHOT.jar src/main/resources/http.json
   ```

5. **Run tests**  
   Verify that everything is working:
   ```bash
   mvn test
   ```

### Keeping Your Fork Updated

To keep your fork updated with the main project:
1. Add the original repository as a remote:
   ```bash
   git remote add upstream https://github.com/ajaynegi45/Http-Server.git
   ```

2. Fetch changes from the original repository:
   ```bash
   git pull upstream main
   ```

### Submitting Changes

1. Create a new branch for your feature or bugfix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes, then commit:
   ```bash
   git commit -m "Your descriptive commit message"
   ```

3. Push your changes to GitHub:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Submit a pull request through GitHub.

---

## Guidelines

- **Write Meaningful Commits**  
  Please keep your commit messages descriptive and focused.

- **Follow the Code Style**  
  Stick to the established coding conventions in the project for consistency.

- **Testing is Key**  
  Make sure your changes pass existing tests and add new tests if necessary.

---

## Code of Conduct

We are committed to fostering an inclusive and welcoming environment. Please read our [Code of Conduct](code_of_conduct.md) to understand how to contribute respectfully.

---

## Thank You 🙌

Thank you for considering contributing to this project. We value every contribution—big or small! Let's build something great together. 💪
