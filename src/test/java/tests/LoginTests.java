package tests;

import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.CSVHelper;
import utils.LangReader;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {
    @DataProvider(name = "loginSuccessData")
    public Object[][] getLoginSuccessData() {
        return CSVHelper.readCSV("dataTest/loginData/login_success.csv");
    }

    @DataProvider(name = "loginFailData")
    public Object[][] getLoginFailData() {
        return CSVHelper.readCSV("dataTest/loginData/login_fail.csv");
    }

    @Test(dataProvider = "loginSuccessData", description = "Verify login with valid credentials")
    public void testValidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo(SAUCEDEMO_URL);
        loginPage.loginWith(username, password);

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page failed to load after valid login.");
    }

    @Test(dataProvider = "loginFailData", description = "Verify login fails with invalid credentials")
    public void testInvalidLogin(String username, String password, String expectedErrorKey) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo(SAUCEDEMO_URL);
        loginPage.loginWith(username, password);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message was not displayed.");
        
        String expectedMessage = LangReader.get(expectedErrorKey);
        Assert.assertTrue(loginPage.getErrorMessageText().contains(expectedMessage),
                "Error message text did not match expected message. Got: " + loginPage.getErrorMessageText()
                + " but expected to contain: " + expectedMessage);
    }
}
