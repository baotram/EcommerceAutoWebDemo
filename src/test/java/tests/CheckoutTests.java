package tests;

import listeners.TestListener;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Listeners(TestListener.class)
public class CheckoutTests extends BaseTest {

    @DataProvider(name = "checkoutLoginData")
    public Object[][] getCheckoutLoginData() {
        return CSVHelper.readCSV("dataTest/checkoutData/checkoutData.csv");
    }

    @Test(dataProvider = "checkoutLoginData", description = "Verify product purchase end-to-end flow")
    public void testProductPurchaseE2E(String username, String password, String firstname, String lastname, String postalCode){
        //  1. Login success
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo(SAUCEDEMO_URL);
        loginPage.loginWith(username, password);
        // 2. Click items and get map item-price
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        int numberOfItemAdded = 2;
        Map<String, String> itemPriceAdded = inventoryPage.addRandomNumberOfProductAndReturnProductAdded(numberOfItemAdded);
        BigDecimal expectedSubtotal = itemPriceAdded.values().stream()
                .map(price -> new BigDecimal(price.replace("$", "").trim()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), numberOfItemAdded);
        inventoryPage.clickCart();
        //  3. Verify cart page
        CartPage cartPage = new CartPage(getDriver());
        Map<String, String> itemInCart = cartPage.itemAdded();
        Assert.assertEquals(itemPriceAdded, itemInCart, "2 maps are different");
        cartPage.clickCheckout();
        //  4. Input checkout information
        CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(getDriver());
        checkoutInformationPage.fillInformation(firstname,lastname,postalCode);
        checkoutInformationPage.clickContinue();
        // 5. Verify Checkout Overview
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(getDriver());
        BigDecimal actualSubtotal = overviewPage.getSubtotalValue();
        Assert.assertEquals(actualSubtotal, expectedSubtotal, "Subtotal does not match sum of item prices.");
        overviewPage.clickFinish();
        // 6. Finish Order
        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isCompleteHeaderDisplayed(), "Complete header is not displayed.");
        Assert.assertEquals(completePage.getCompleteHeaderText().toLowerCase(), "thank you for your order!",
                "Order complete header text did not match.");
        String browserName = ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName();
        captureScreenshot("Test Successful on "+browserName+"!");
    }

}
