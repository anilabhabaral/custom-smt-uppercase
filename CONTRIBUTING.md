
# Contributing to Custom SMT Uppercase

First off, thank you for considering contributing to `custom-smt-uppercase`! It's people like you that make the open-source community such a great place to learn, inspire, and create. 

This project is a Custom Single Message Transform (SMT) for Kafka Connect designed to convert string fields from lowercase to uppercase.

## How to Contribute

### 1. Reporting Bugs
If you find a bug in the source code or a mistake in the documentation, you can help by submitting an issue to the [GitHub Repository](https://github.com/anilabhabaral/custom-smt-uppercase/issues). 
Please include:
- A clear and descriptive title.
- Steps to reproduce the issue.
- The expected behavior versus the actual behavior.
- Relevant Kafka Connect logs or connector configuration JSON snippets.

### 2. Suggesting Enhancements
Enhancement suggestions are tracked as GitHub issues. When creating an enhancement issue, please provide:
- A clear description of the proposed feature.
- Use cases and examples of how this enhancement would be utilized in a data pipeline.

### 3. Submitting Pull Requests
Pull requests are always welcome for bug fixes, documentation improvements, and new features.

#### Development Setup
This project uses **Java** and **Maven**. To set up your local development environment:

1. **Fork the repository** and clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/custom-smt-uppercase.git
   cd custom-smt-uppercase


2. **Add the upstream remote** to keep your fork synced:
```bash
git remote add upstream https://github.com/anilabhabaral/custom-smt-uppercase.git

```


3. **Build the project** to ensure all dependencies are downloaded:
```bash
mvn clean install

```



#### Making Changes

1. Create a new branch for your feature or bugfix:
```bash
git checkout -b feature/your-feature-name

```


2. Make your changes in the codebase. The main transformation logic resides in the `src/main/java/com/anilabha/kafka/connect/transforms/` directory.
3. **Write Tests:** Ensure you add or update corresponding unit tests in the `src/test/java/` directory. Testing is crucial for verifying that the transformation handles various schemas, schemaless data, and edge cases correctly.
4. **Format your code:** Ensure your code adheres to standard Java conventions.
5. **Verify the build:** Run the test suite to ensure everything passes successfully before committing:
```bash
mvn clean test

```



#### Commit Guidelines

* Write clear, concise commit messages.
* Use the imperative mood (e.g., "Add test cases for schemaless records" instead of "Added test cases").
* Reference issue numbers in your commit messages if applicable (e.g., `Fixes #12`).

#### Submitting

1. Push your branch to your forked repository:
```bash
git push origin feature/your-feature-name

```


2. Open a Pull Request against the `main` branch of the original repository.
3. Provide a detailed description of the changes in the PR, including what was changed and why.

## Code of Conduct

By participating in this project, you agree to abide by standard open-source community guidelines. Be respectful, constructive, and welcoming to all contributors.

## License

By contributing to `custom-smt-uppercase`, you agree that your contributions will be licensed under the same open-source license that covers the project.
