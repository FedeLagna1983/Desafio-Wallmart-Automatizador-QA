package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.JavascriptException;
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
    private final By sameDeliveryAndBillingAddressCheckbox = By.cssSelector(
            "#collapse-payment-address input[name='shipping_address']"
    );
    private final By registerContinueButton = By.id("button-register");
    private final By billingContinueButton = By.id("button-guest");

    // --- EXISTING USER FLOW ---
    private final By existingBillingAddressRadio = By.cssSelector("input[name='payment_address'][value='existing']");
    private final By billingAddressContinueButton = By.id("button-payment-address");

    private final By existingDeliveryAddressRadio = By.cssSelector("input[name='shipping_address'][value='existing']");
    private final By deliveryAddressContinueButton = By.id("button-shipping-address");

    private final By deliveryMethodStepToggle = By.cssSelector("a[href='#collapse-shipping-method']");
    private final By deliveryMethodContinueButton = By.id("button-shipping-method");

    // --- PAYMENT ---
    private final By bankTransferRadio = By.cssSelector(
            "#collapse-payment-method input[name='payment_method'][value='bank_transfer']"
    );
    private final By bankTransferLabel = By.xpath(
            "//div[@id='collapse-payment-method']//input[@name='payment_method' and @value='bank_transfer']/ancestor::label"
    );
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

        selectCheckboxIfPresent(sameDeliveryAndBillingAddressCheckbox);

        waitForVisibility(privacyPolicyCheckbox);

        if (!driver.findElement(privacyPolicyCheckbox).isSelected()) {
            click(privacyPolicyCheckbox);
        }

        click(registerContinueButton);

        waitUntil(driver -> isDisplayedAndEnabled(deliveryAddressContinueButton)
                || isDisplayedAndEnabled(deliveryMethodContinueButton)
                || isDisplayedAndEnabled(bankTransferRadio));
        waitForAjaxToFinish();
        reloadCheckoutPageAfterRegistration();
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
        waitUntil(driver -> isDisplayedAndEnabled(billingAddressContinueButton)
                || isDisplayedAndEnabled(deliveryAddressContinueButton)
                || isDisplayedAndEnabled(deliveryMethodContinueButton)
                || isDisplayedAndEnabled(bankTransferRadio));

        if (isDisplayedAndEnabled(billingAddressContinueButton)) {
            click(billingAddressContinueButton);
            waitForAjaxToFinish();
            waitUntil(driver -> isDisplayedAndEnabled(deliveryAddressContinueButton)
                    || isDisplayedAndEnabled(deliveryMethodContinueButton)
                    || isDisplayedAndEnabled(bankTransferRadio));
        }

        if (isDisplayedAndEnabled(deliveryAddressContinueButton)) {
            click(deliveryAddressContinueButton);
            waitForAjaxToFinish();
            waitUntil(driver -> isDisplayedAndEnabled(deliveryMethodContinueButton)
                    || isDisplayedAndEnabled(bankTransferRadio));
        }

        if (isDisplayedAndEnabled(deliveryMethodContinueButton)) {
            click(deliveryMethodContinueButton);
            waitForAjaxToFinish();
        }

        waitForVisibility(bankTransferRadio);
        waitForAjaxToFinish();
    }

    // ======================
    // PAYMENT
    // ======================

    public void selectBankTransferPaymentMethod() {
        selectPaymentRadio(bankTransferRadio, bankTransferLabel);
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
            waitForAjaxToFinish();
            acceptUnexpectedSuccessAlertIfPresent();

            if (isConfirmStepAvailable()) {
                return;
            }

            if (isPaymentMethodWarningDisplayed()) {
                reloadPaymentMethodsFromDeliveryStep();
            }
        }

        if (isDisplayedNow(checkoutWarningAlert)) {
            throw new RuntimeException(
                    "Unable to continue payment method: "
                            + textOf(checkoutWarningAlert)
                            + System.lineSeparator()
                            + paymentMethodDebugState()
            );
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

    private void selectCheckboxIfPresent(By locator) {
        if (!isDisplayedAndEnabled(locator)) {
            return;
        }

        WebElement checkbox = findDisplayedElement(locator);

        if (!checkbox.isSelected()) {
            clickElement(checkbox);
        }
    }

    private void selectPaymentRadio(By radioLocator, By labelLocator) {
        waitUntil(driver -> isDisplayedAndEnabled(radioLocator));
        waitForAjaxToFinish();

        WebElement radio = findDisplayedElement(radioLocator);
        scrollIntoView(radio);

        if (!radio.isSelected()) {
            clickElement(radio);
        }

        if (!isDisplayedInputSelected(radioLocator)) {
            clickElement(findDisplayedElement(labelLocator));
        }

        if (!isDisplayedInputSelected(radioLocator)) {
            markInputAsSelected(findDisplayedElement(radioLocator));
        }

        dispatchInputEvents(findDisplayedElement(radioLocator));
        waitUntil(driver -> isDisplayedInputSelected(radioLocator));
    }

    private WebElement findDisplayedElement(By locator) {
        return wait.until(driver -> driver.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));
    }

    private void clickElement(WebElement element) {
        try {
            waitForClickability(element).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    private boolean isDisplayedInputSelected(By locator) {
        try {
            return driver.findElements(locator).stream()
                    .filter(WebElement::isDisplayed)
                    .anyMatch(WebElement::isSelected);
        } catch (Exception e) {
            return false;
        }
    }

    private void markInputAsSelected(WebElement input) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].checked = true;" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                input
        );
    }

    private void dispatchInputEvents(WebElement input) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                input
        );
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    private void reloadPaymentMethodsFromDeliveryStep() {
        try {
            if (!isDisplayedAndEnabled(deliveryMethodContinueButton)) {
                clickElement(findDisplayedElement(deliveryMethodStepToggle));
            }

            waitUntil(driver -> isDisplayedAndEnabled(deliveryMethodContinueButton));
            click(deliveryMethodContinueButton);
            waitForAjaxToFinish();
            waitForVisibility(bankTransferRadio);
            waitForAjaxToFinish();
        } catch (Exception e) {
            // Keep the original payment-method warning as the failure reason.
        }
    }

    private void reloadCheckoutPageAfterRegistration() {
        driver.get(TestConfig.getBaseUrl() + "/index.php?route=checkout/checkout");
        waitForPageReady();
        waitUntil(driver -> isDisplayedAndEnabled(billingAddressContinueButton)
                || isDisplayedAndEnabled(deliveryAddressContinueButton)
                || isDisplayedAndEnabled(deliveryMethodContinueButton)
                || isDisplayedAndEnabled(bankTransferRadio));
        waitForAjaxToFinish();
    }

    private String paymentMethodDebugState() {
        Object debugState = ((JavascriptExecutor) driver).executeScript(
                "const panel = document.querySelector('#collapse-payment-method');" +
                        "if (!panel) return 'payment panel not found';" +
                        "const radios = Array.from(panel.querySelectorAll('input[type=radio]')).map((input) => ({" +
                        "name: input.name," +
                        "value: input.value," +
                        "checked: input.checked," +
                        "visible: !!(input.offsetWidth || input.offsetHeight || input.getClientRects().length)" +
                        "}));" +
                        "const checkboxes = Array.from(panel.querySelectorAll('input[type=checkbox]')).map((input) => ({" +
                        "name: input.name," +
                        "value: input.value," +
                        "checked: input.checked," +
                        "visible: !!(input.offsetWidth || input.offsetHeight || input.getClientRects().length)" +
                        "}));" +
                        "const checkedValues = Array.from(panel.querySelectorAll('input[type=radio]:checked')).map((input) => input.value);" +
                        "const selectedFields = panel.querySelectorAll('input[type=radio]:checked, input[type=checkbox]:checked, textarea');" +
                        "const serialized = Array.from(selectedFields).map((input) => encodeURIComponent(input.name) + '=' + encodeURIComponent(input.value)).join('&');" +
                        "return JSON.stringify({radios, checkboxes, checkedValues, serialized, panelText: panel.innerText});"
        );

        return String.valueOf(debugState);
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

    private boolean isPaymentMethodWarningDisplayed() {
        return isDisplayedNow(checkoutWarningAlert)
                && textOf(checkoutWarningAlert).contains("Payment method required");
    }

    private void waitForAjaxToFinish() {
        waitUntil(driver -> {
            try {
                Object isIdle = ((JavascriptExecutor) driver).executeScript(
                        "return !window.jQuery || window.jQuery.active === 0;"
                );

                return Boolean.TRUE.equals(isIdle);
            } catch (JavascriptException e) {
                return true;
            }
        });
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
