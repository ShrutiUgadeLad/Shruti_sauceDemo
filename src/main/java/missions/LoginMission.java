package missions;

import constants.Constants;
import context.Context;
import dataModel.LoginDataModel;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import uiPages.Login;
import utilities.ConfigFileReader;
import utilities.Jsonreader;

public class LoginMission extends ScenarioSteps {
    private Login login;

    @Steps
    Jsonreader Jsonreader;

    @Steps
    Context context;

    @Step
    public void launchUrl(String url) {
        waitABit(30000);
        login.launchUrl(url);
        getDriver().manage().window().maximize();
    }

    @Step
    public void verifyTitleOfWebPage() {
        String actualTitle = getDriver().getTitle();
        Assert.assertTrue("Sauce Lab is not loaded", actualTitle.contains("Swag Labs"));
    }

    @Step
    public void verifyUserNameField() {
        Assert.assertTrue("Username field is present", login.isUsernamePresent());
        Assert.assertTrue("Username field is enable", login.isUsernameEnable());
    }

    @Step
    public void verifyPasswordField() {
        Assert.assertTrue("Password field is present", login.isPasswordPresent());
        Assert.assertTrue("Password field is enable", login.isPasswordEnable());
    }

    @Step
    public void verifyLoginButton() {
        Assert.assertTrue("Login Button field is present", login.isLoginButtonPresent());
        Assert.assertTrue("Login Button field is clickable", login.isLoginButtonClickable());
    }

    @Step
    public void enterDataInUserName(String dataType) {
        if(dataType.equalsIgnoreCase("alphaNumeric")) {
            String userName = RandomStringUtils.randomAlphanumeric(10);
            login.enterUserName(userName);
        }
        if(dataType.equalsIgnoreCase("specialCharacter")) {
            String userName = "&*&*Q*Q";
            login.enterUserName(userName);
        }
        if(dataType.equalsIgnoreCase("blank")) {
            login.enterUserName("");
        }
        if(dataType.equalsIgnoreCase("valid")) {
            String username = ConfigFileReader.getPropertyValue("username") ;
            login.enterUserName(username);
        }
    }

    @Step
    public void verifyErrorMessageForInvalidUserNamePassword() {
        LoginDataModel loginElementsData = Jsonreader.getDataForSwagLabs(Constants.USER);
        context.setLoginDataModel(loginElementsData);

        String expectedErrorMessage = context.getLoginDataModel().getInvalidUsernamePassword();
        String actualErrorMessag = login.getErrorMessage();
        Assert.assertEquals("Error Message is not displayed", expectedErrorMessage,
                actualErrorMessag);
    }

    @Step
    public void enterDataInPassword(String dataType) {
        if(dataType.equalsIgnoreCase("alphaNumeric")) {
            String userName = RandomStringUtils.randomAlphanumeric(10);
            login.enterPassword(userName);
        }
        if(dataType.equalsIgnoreCase("specialCharacter")) {
            String userName = "&*&*Q*Q";
            login.enterPassword(userName);
        }
        if(dataType.equalsIgnoreCase("blank")) {
            login.enterPassword("");
        }
        if(dataType.equalsIgnoreCase("valid")) {
            String password = ConfigFileReader.getPropertyValue("password") ;
            login.enterUserName(password);
        }
    }

    @Step
    public void clickOnLoginButton() {
        login.clickOnLoginButton();
    }

    @Step
    public void verifyErrorMessageForBlankUserNamePassword() {
        LoginDataModel loginElementsData = Jsonreader.getDataForSwagLabs(Constants.USER);
        context.setLoginDataModel(loginElementsData);

        String expectedErrorMessage = context.getLoginDataModel().getUserNameMissing();
        String actualErrorMessag = login.getErrorMessage();
        Assert.assertEquals("Error Message is not displayed", expectedErrorMessage,
                actualErrorMessag);
    }

    @Step
    public void verifySwagLabMainPage() {
        LoginDataModel loginElementsData = Jsonreader.getDataForSwagLabs(Constants.USER);
        context.setLoginDataModel(loginElementsData);

        String expectedTitle = context.getLoginDataModel().getSwagLabHeader();
        String actualTitle = login.getSwagLabHeaderTitle();
        Assert.assertEquals("Swag Labs main page is not loaded", expectedTitle, actualTitle);
    }

    @Step
    public void loginSwagLabs() {
        String username = ConfigFileReader.getPropertyValue("username") ;
        String password = ConfigFileReader.getPropertyValue("password") ;
        login.enterUserName(username).enterPassword(password).clickOnLoginButton();
    }

    @Step
    public void userAddProductInCart() {
        login.addProductInCart().clickOnCart();
    }

    @Step
    public void userClickOnCheckoutButton() {
        login.clickOnCheckoutButton();
    }

    @Step
    public LoginMission enterCheckoutDetails() {
        String postalcode = RandomStringUtils.randomNumeric(6);
        String firstname = RandomStringUtils.randomAlphabetic(6);
        String lastname = RandomStringUtils.randomAlphabetic(6);

        login.enterDetailsForCheckout(firstname, lastname, postalcode);
        return this;
    }

    @Step
    public LoginMission clickOnContinueButton() {
        login.clickOnContinueButton();
        return this;
    }

    @Step
    public LoginMission verifyPaymentDetails() {
        LoginDataModel loginElementsData = Jsonreader.getDataForSwagLabs(Constants.USER);
        context.setLoginDataModel(loginElementsData);

        String expectedTitle = context.getLoginDataModel().getPaymentConfirmation();
        String actualTitle = login.getPaymentDetails();
        Assert.assertEquals("Payment Information is not loaded", expectedTitle, actualTitle);
        return this;
    }

    public LoginMission clickOnFinishButton() {
        login.clickOnFinishButton();
        return this;
    }

    @Step
    public LoginMission verifyOrderSuccessfulDetails() {
        LoginDataModel loginElementsData = Jsonreader.getDataForSwagLabs(Constants.USER);
        context.setLoginDataModel(loginElementsData);

        String actualTitle = login.getOrderSuccessfulText();
        String expectedTitle = context.getLoginDataModel().getSuccessfulCheckout();

        Assert.assertEquals("Order did not get completed", expectedTitle, actualTitle);
        return this;
    }
}