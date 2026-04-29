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

- `@checkout`: full checkout flow execution.
- `@existingUser`: isolated existing user execution.
- `@registerUser`: specific registration validation.

This enables execution for:

- Local development.
- Partial validations.
- CI/CD pipeline runs.

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

# Cart only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@cart"

# Checkout only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@checkout"

# Search only
mvn clean test -Dbrowser=chrome -Dheadless=true "-Dcucumber.filter.tags=@search"

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
