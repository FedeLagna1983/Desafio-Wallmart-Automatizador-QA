package walmart.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import walmart.context.TestContext;
import walmart.model.CheckoutData;
import walmart.model.RegisterUserData;
import walmart.model.UserData;
import walmart.utils.CheckoutCsvReader;
import walmart.utils.RegisterUserCsvReader;
import walmart.utils.UsersCsvReader;

public class CheckoutSteps {

    private final TestContext context;

    public CheckoutSteps(TestContext context) {
        this.context = context;
    }

    @When("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {
        context.pages().cartPage().continueToCheckout();
    }

    @When("the user selects Guest Checkout")
    public void theUserSelectsGuestCheckout() {
        context.pages().checkoutPage().selectGuestCheckout();
    }

    @When("the user completes the billing details form using {string}")
    public void theUserCompletesTheBillingDetailsFormUsing(String scenario) {
        CheckoutData checkoutData = CheckoutCsvReader.getCheckoutData(scenario);

        context.pages().checkoutPage().completeBillingDetails(
                checkoutData.firstName(),
                checkoutData.lastName(),
                checkoutData.email(),
                checkoutData.telephone(),
                checkoutData.address1(),
                checkoutData.address2(),
                checkoutData.city(),
                checkoutData.postCode(),
                checkoutData.country(),
                checkoutData.region()
        );
    }

    @When("the user logs in during checkout using {string}")
    public void theUserLogsInDuringCheckoutUsing(String userKey) {
        UserData userData = UsersCsvReader.getUserData(userKey);

        context.pages().checkoutPage().loginDuringCheckout(
                userData.email(),
                userData.password()
        );
    }

    @When("the user continues with the existing billing address")
    public void theUserContinuesWithTheExistingBillingAddress() {
        context.pages().checkoutPage().continueWithExistingBillingAddress();
    }

    @When("the user continues with the existing delivery address")
    public void theUserContinuesWithTheExistingDeliveryAddress() {
        context.pages().checkoutPage().continueWithExistingDeliveryAddress();
    }

    @When("the user selects Register Account during checkout")
    public void theUserSelectsRegisterAccountDuringCheckout() {
        context.pages().checkoutPage().selectRegisterAccount();
    }

    @When("the user completes the registration details using {string}")
    public void theUserCompletesTheRegistrationDetailsUsing(String userKey) {
        RegisterUserData registerUserData = RegisterUserCsvReader.getRegisterUserData(userKey);

        context.pages().checkoutPage().completeRegistrationDetails(
                registerUserData.firstName(),
                registerUserData.lastName(),
                registerUserData.email(),
                registerUserData.telephone(),
                registerUserData.password(),
                registerUserData.address1(),
                registerUserData.address2(),
                registerUserData.city(),
                registerUserData.postCode(),
                registerUserData.country(),
                registerUserData.region()
        );
    }

    @When("the user continues with the delivery method")
    public void theUserContinuesWithTheDeliveryMethod() {
        context.pages().checkoutPage().continueWithDeliveryMethod();
    }

    @When("the user selects Bank Transfer as payment method")
    public void theUserSelectsBankTransferAsPaymentMethod() {
        context.pages().checkoutPage().selectBankTransferPaymentMethod();
    }

    @When("the user accepts the terms and conditions")
    public void theUserAcceptsTheTermsAndConditions() {
        context.pages().checkoutPage().acceptTermsAndConditions();
    }

    @When("the user continues with the payment method")
    public void theUserContinuesWithThePaymentMethod() {
        context.pages().checkoutPage().continuePaymentMethod();
    }

    @When("the user confirms the order")
    public void theUserConfirmsTheOrder() {
        context.pages().checkoutPage().confirmOrder();
    }

    @Then("the order should be placed successfully")
    public void theOrderShouldBePlacedSuccessfully() {
        Assert.assertTrue(context.pages().checkoutPage().isOrderPlacedSuccessfully(),
                "Order was not placed successfully");
    }
}
