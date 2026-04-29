# Walmart Automation

Base UI automation project using Java, Selenium WebDriver, Cucumber, and TestNG.

## Testing Strategy

A testing strategy was defined to validate critical business flows through end-to-end automation, prioritizing functional coverage, maintainability, and scalability.

## Approach

An end-to-end (E2E) approach was adopted to validate system behavior from the end user's perspective, covering the complete purchase flow.

Critical business functionality is prioritized by considering:

- High usage frequency.
- Impact on conversion.
- Risk associated with failures.

## Implemented Test Types

Automated functional tests were implemented to validate:

- Navigation between pages.
- Interaction with UI elements.
- Form validations.
- Shopping cart behavior.
- Complete checkout flow.

## Scenario Coverage

Different scenarios were defined within the main flows to increase coverage:

- Guest Checkout: purchase without registration.
- Existing User Checkout: reuse of existing user data.
- Register User Checkout: account creation during purchase.

This allows validating different user types and system paths.

## Framework Design

The Page Object Model (POM) pattern is applied to:

- Separate business logic from UI interaction.
- Improve maintainability.
- Enable code reuse.

The project is organized in layers:

- `pages`: UI interaction.
- `steps`: business step definitions with Cucumber.
- `features`: scenario definitions.
- `utils`: CSV data handling.
- `base`: reusable common methods.

## Synchronization Strategy

Static waits (`Thread.sleep`) are avoided, using instead:

- Explicit waits (`waitForVisibility`, `waitUntil`).
- DOM state validations.

This helps ensure stability and reduce test flakiness.

## Test Data Management

CSV files are used to decouple test data from test logic:

- `products.csv`: product data.
- `checkoutData.csv`: guest checkout data.
- `users.csv`: existing users.
- `registerUserData.csv`: dynamic registration data.

Dynamic data is also generated, for example timestamp-based email addresses, to avoid conflicts between executions.

## Applied Principles

Good development practices were applied:

- Single Responsibility: each class has a single responsibility.
- Code reuse: unnecessary duplication is avoided.
- Scalability: new scenarios can be added easily.
- Readability: code is clear and understandable.

## Execution Strategy

Flexible execution is supported through Cucumber tags:

- `@smoke`: critical scenarios for quick validation.
- `@regression`: broader functional coverage for regression validation.
- `@home`: home page validation scenarios.
- `@cart`: shopping cart scenarios.
- `@featured`: scenarios that add products from Featured products.
- `@search`: scenarios that add products through search results.
- `@checkout`: full checkout flow scenarios.
- `@guestUser`: checkout as a guest user.
- `@existingUser`: checkout with an existing user.
- `@registerUser`: checkout by registering a new user.

This enables execution for:

- Local development.
- Partial validations.
- CI/CD pipeline runs.

Current tag distribution:

- `Home page validation`: `@smoke @home`.
- `Add product from Featured and validate Shopping Cart`: `@smoke @cart @featured`.
- `Update product quantity and validate Shopping Cart totals`: `@regression @cart @featured`.
- `Add product from search and validate Shopping Cart`: `@regression @cart @search`.
- `Complete checkout as guest user`: `@smoke @regression @checkout @guestUser`.
- `Complete checkout with existing user`: `@regression @checkout @existingUser`.
- `Complete checkout by registering a new user`: `@regression @checkout @registerUser`.

## Requirements

- Java 17 or higher
- Maven 3.9+
- Available browser (Chrome, Firefox, or Edge)

## Run Tests

```bash
mvn clean test
```

## Configuration Properties

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true -DbaseUrl=https://opencart.abstracta.us
```

Supported properties:

- `browser`: `chrome`, `firefox`, `edge` (default: `chrome`)
- `headless`: `true` or `false` (default: `false`)
- `baseUrl`: application base URL (default: `https://opencart.abstracta.us`)

## Execution Commands

To run tests (PowerShell), from `walmart-automation`:

```powershell
# All tests
mvn clean test -Dbrowser=chrome -Dheadless=true

# Smoke
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@smoke"

# Regression
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@regression"

# Home only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@home"

# Cart only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@cart"

# Checkout only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@checkout"

# Search only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@search"

# Featured products only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@featured"

# Guest User only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@guestUser"

# Existing User only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@existingUser"

# Register User only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@registerUser"
```

Useful combination examples:

```powershell
# Regression excluding register user
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@regression and not @registerUser"

# Smoke home only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@smoke and @home"
```

## Sensitive Data

The `src/test/resources/testdata/users.csv` file is used for existing user credentials and is not versioned.
To create local test data, use this file as a base:

```bash
src/test/resources/testdata/users.example.csv
```

## Structure

- `base`: common page utilities.
- `config`: centralized execution configuration.
- `factory`: WebDriver creation and teardown.
- `hooks`: Cucumber setup and teardown.
- `model`: typed models for test data.
- `pages`: Page Objects.
- `steps`: Gherkin step definitions.
- `utils`: CSV readers and parsing support.
- `resources/features`: Gherkin scenarios.


## CI/CD Integration (Jenkins)

A Jenkins pipeline is implemented using a Jenkinsfile located at the root of the project.
This allows automated execution of tests based on Cucumber tags, enabling flexible and scalable test runs.

Jenkins Pipeline Overview

The pipeline performs the following steps:

Checkout the code from the repository.
Clean the project using Maven.
Execute tests based on the selected Cucumber tag.
Archive test results and reports.

## Jenkinsfile Configuration

The pipeline is parameterized to allow dynamic test execution:

parameters {
    choice(
        name: 'TEST_TAG',
        choices: [
            '@smoke',
            '@regression',
            '@home',
            '@cart',
            '@featured',
            '@search',
            '@checkout',
            '@guestUser',
            '@existingUser',
            '@registerUser'
        ],
        description: 'Select Cucumber tag to execute'
    )
}

Test execution is controlled by the selected tag:

bat "mvn test -Dcucumber.filter.tags=${params.TEST_TAG}"

## Execution Modes

## Manual Execution

Tests can be executed manually from Jenkins by selecting a tag from the dropdown menu.

Examples:

@smoke → quick validation of critical flows
@regression → full functional coverage
@checkout → complete checkout validation
@cart → shopping cart scenarios

## Automated Execution (Recommended)

In a real CI/CD setup, different tags are executed depending on the context:

Trigger	Execution
Pull Request	@smoke
Merge to main	@checkout
Nightly execution	@regression

This ensures fast feedback and full system validation without blocking development.


## Running ALL Tests in Jenkins

To execute all tests (without tag filtering), update the Jenkinsfile:

bat "mvn test"

Alternatively, a conditional logic can be implemented:

stage('Run Tests') {
    steps {
        script {
            if (params.TEST_TAG == 'ALL') {
                bat 'mvn test'
            } else {
                bat "mvn test -Dcucumber.filter.tags=${params.TEST_TAG}"
            }
        }
    }
}


## Benefits of the Pipeline

Flexible execution through tag-based filtering
Faster feedback using smoke tests
Full regression coverage in scheduled runs
Easy integration with CI/CD environments
Improved maintainability and scalability


## Recommended Workflow

Develop and test locally.
Push changes to GitHub.
Jenkins triggers execution automatically or manually.
Review results in Jenkins reports.
Fix issues and repeat the cycle.

## CI/CD FLow Summary
VS Code → Git → GitHub → Jenkins → Test Execution → Reports