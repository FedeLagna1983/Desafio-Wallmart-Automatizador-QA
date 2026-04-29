package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import walmart.base.BasePage;
import walmart.config.TestConfig;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    // --- STEP 1: OPTIONS ---
    private final By guestCheckoutRadio = By.cssSelector("input[value='guest']");
    private final By registerAccountRadio = By.cssSelector("input[value='register']");
    private final By accountContinueButton = By.id("button-account");

    private final By loginEmailInput = By.id("input-email");
    private final By loginPasswordInput = By.id("input-password");
    private final By loginButton = By.id("button-login");

    // --- STEP 2: BILLING / REGISTER ---
    private final By firstNameInput = By.id("input-payment-firstname");
    private final By lastNameInput = By.id("input-payment-lastname");
    private final By emailInput = By.id("input-payment-email");
    private final By telephoneInput = By.id("input-payment-telephone");
    private final By passwordInput = By.id("input-payment-password");
    private final By confirmPasswordInput = By.id("input-payment-confirm");

    private final By address1Input = By.id("input-payment-address-1");
    private final By address2Input = By.id("input-payment-address-2");
    private final By cityInput = By.id("input-payment-city");
    private final By postCodeInput = By.id("input-payment-postcode");
    private final By countryDropdown = By.id("input-payment-country");
    private final By regionDropdown = By.id("input-payment-zone");

    private final By privacyPolicyCheckbox = By.cssSelector("#collapse-payment-address input[name='agree']");
    private final By registerContinueButton = By.id("button-register");
    private final By billingContinueButton = By.id("button-guest");

    // --- EXISTING USER FLOW ---
    private final By existingBillingAddressRadio = By.cssSelector("input[name='payment_address'][value='existing']");
    private final By billingAddressContinueButton = By.id("button-payment-address");

    private final By existingDeliveryAddressRadio = By.cssSelector("input[name='shipping_address'][value='existing']");
    private final By deliveryAddressContinueButton = By.id("button-shipping-address");

    private final By deliveryMethodContinueButton = By.id("button-shipping-method");

    // --- PAYMENT ---
    private final By bankTransferRadio = By.cssSelector("input[value='bank_transfer']");
    private final By termsCheckbox = By.cssSelector("#collapse-payment-method input[name='agree']");
    private final By paymentContinueButton = By.id("button-payment-method");
    private final By checkoutWarningAlert = By.cssSelector(".alert.alert-danger");

    // --- CONFIRM ---
    private final By confirmOrderButton = By.id("button-confirm");

    // --- SUCCESS ---
    private final By successTitle = By.cssSelector("#content h1");
    private final By successMessage = By.cssSelector("#content p");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ======================
    // GUEST
    // ======================

    public void selectGuestCheckout() {
        click(guestCheckoutRadio);
        click(accountContinueButton);
        waitForVisibility(firstNameInput);
    }

    public void completeBillingDetails(
            String firstName,
            String lastName,
            String email,
            String telephone,
            String address1,
            String address2,
            String city,
            String postCode,
            String country,
            String region
    ) {
        clearAndType(firstNameInput, firstName);
        clearAndType(lastNameInput, lastName);
        clearAndType(emailInput, email);
        clearAndType(telephoneInput, telephone);
        clearAndType(address1Input, address1);
        clearAndType(address2Input, address2);
        clearAndType(cityInput, city);
        clearAndType(postCodeInput, postCode);

        selectCountryAndRegion(country, region);

        click(billingContinueButton);
        waitForVisibility(bankTransferRadio);
    }

    // ======================
    // REGISTER USER
    // ======================

    public void selectRegisterAccount() {
        click(registerAccountRadio);
        click(accountContinueButton);
        waitForVisibility(firstNameInput);
    }

    public void completeRegistrationDetails(
            String firstName,
            String lastName,
            String email,
            String telephone,
            String password,
            String address1,
            String address2,
            String city,
            String postCode,
            String country,
            String region
    ) {
        clearAndType(firstNameInput, firstName);
        clearAndType(lastNameInput, lastName);
        clearAndType(emailInput, email);
        clearAndType(telephoneInput, telephone);
        clearAndType(passwordInput, password);
        clearAndType(confirmPasswordInput, password);
        clearAndType(address1Input, address1);
        clearAndType(address2Input, address2);
        clearAndType(cityInput, city);
        clearAndType(postCodeInput, postCode);

        selectCountryAndRegion(country, region);

        waitForVisibility(privacyPolicyCheckbox);

        if (!driver.findElement(privacyPolicyCheckbox).isSelected()) {
            click(privacyPolicyCheckbox);
        }

        click(registerContinueButton);

        waitUntil(driver -> isDisplayedAndEnabled(deliveryAddressContinueButton)
                || isDisplayedAndEnabled(deliveryMethodContinueButton)
                || isDisplayedAndEnabled(bankTransferRadio));
    }

    // ======================
    // EXISTING USER
    // ======================

    public void loginDuringCheckout(String email, String password) {
        clearAndType(loginEmailInput, email);
        clearAndType(loginPasswordInput, password);
        click(loginButton);

        waitForVisibility(billingAddressContinueButton);
    }

    public void continueWithExistingBillingAddress() {
        if (isDisplayed(existingBillingAddressRadio)) {
            click(existingBillingAddressRadio);
        }

        click(billingAddressContinueButton);
        waitForVisibility(deliveryAddressContinueButton);
    }

    public void continueWithExistingDeliveryAddress() {
        if (isDisplayed(existingDeliveryAddressRadio)) {
            click(existingDeliveryAddressRadio);
        }

        click(deliveryAddressContinueButton);
        waitForVisibility(deliveryMethodContinueButton);
    }

    public void continueWithDeliveryMethod() {
        waitUntil(driver -> isDisplayedAndEnabled(deliveryAddressContinueButton)
                || isDisplayedAndEnabled(deliveryMethodContinueButton)
                || isDisplayedAndEnabled(bankTransferRadio));

        if (isDisplayedAndEnabled(deliveryAddressContinueButton)) {
            click(deliveryAddressContinueButton);
            waitUntil(driver -> isDisplayedAndEnabled(deliveryMethodContinueButton)
                    || isDisplayedAndEnabled(bankTransferRadio));
        }

        if (isDisplayedAndEnabled(deliveryMethodContinueButton)) {
            click(deliveryMethodContinueButton);
        }

        waitForVisibility(bankTransferRadio);
    }

    // ======================
    // PAYMENT
    // ======================

    public void selectBankTransferPaymentMethod() {
        WebElement bankTransfer = waitForVisibility(bankTransferRadio);

        if (!bankTransfer.isSelected()) {
            try {
                click(bankTransfer);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bankTransfer);
            }
        }

        if (!bankTransfer.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                    bankTransfer
            );
        }

        waitUntil(webDriver -> {
            try {
                return webDriver.findElement(bankTransferRadio).isSelected();
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void acceptTermsAndConditions() {
        WebElement terms = waitForVisibility(termsCheckbox);

        if (!terms.isSelected()) {
            try {
                click(terms);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);
            }
        }

        if (!terms.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                    terms
            );
        }

        waitUntil(webDriver -> {
            try {
                return webDriver.findElement(termsCheckbox).isSelected();
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void continuePaymentMethod() {
        for (int attempt = 0; attempt < 3; attempt++) {
            selectBankTransferPaymentMethod();
            acceptTermsAndConditions();

            click(paymentContinueButton);
            acceptUnexpectedSuccessAlertIfPresent();

            if (isConfirmStepAvailable()) {
                return;
            }
        }

        if (isDisplayedNow(checkoutWarningAlert)) {
            throw new RuntimeException("Unable to continue payment method: " + textOf(checkoutWarningAlert));
        }

        waitUntil(driver -> isConfirmStepAvailable());
    }

    // ======================
    // CONFIRM
    // ======================

    public void confirmOrder() {
        if (driver.getCurrentUrl().contains("route=checkout/success")) {
            waitForVisibility(successTitle);
            return;
        }

        click(confirmOrderButton);
        acceptUnexpectedSuccessAlertIfPresent();
        waitForUrlContains("route=checkout/success");
        waitForVisibility(successTitle);
    }

    public boolean isOrderPlacedSuccessfully() {
        return isDisplayed(successTitle)
                && textOf(successTitle).contains("Your order has been placed")
                && isDisplayed(successMessage)
                && textOf(successMessage).contains("successfully processed");
    }

    private void selectCountryAndRegion(String country, String region) {
        selectVisibleTextWhenReady(countryDropdown, country);
        selectVisibleTextWhenReady(regionDropdown, region);
    }

    private void selectVisibleTextWhenReady(By locator, String visibleText) {
        waitUntil(driver -> {
            try {
                WebElement dropdown = driver.findElement(locator);

                if (!dropdown.isDisplayed() || !dropdown.isEnabled()) {
                    return false;
                }

                Select select = new Select(dropdown);
                boolean optionExists = select.getOptions().stream()
                        .anyMatch(option -> option.getText().trim().equals(visibleText));

                if (!optionExists) {
                    return false;
                }

                select.selectByVisibleText(visibleText);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private boolean isDisplayedAndEnabled(By locator) {
        try {
            return driver.findElements(locator).stream()
                    .anyMatch(element -> element.isDisplayed() && element.isEnabled());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isConfirmStepAvailable() {
        return isDisplayedNow(confirmOrderButton)
                || driver.getCurrentUrl().contains("route=checkout/success");
    }

    private boolean isDisplayedNow(By locator) {
        try {
            return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    private void acceptUnexpectedSuccessAlertIfPresent() {
        try {
            String alertText = new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent())
                    .getText();

            driver.switchTo().alert().accept();

            if (alertText.contains("route=checkout/success")) {
                driver.get(TestConfig.getBaseUrl() + "/index.php?route=checkout/success");
            }
        } catch (TimeoutException e) {
            // No alert was displayed, continue with the normal checkout flow.
        }
    }
}
