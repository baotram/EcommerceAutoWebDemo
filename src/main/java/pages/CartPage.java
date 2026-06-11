package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage{
    private final By checkoutButton = By.id("checkout");
    private final By productName = By.xpath("//div[@class = 'inventory_item_name']");
    private final By productPrice = By.xpath("//div[@class = 'inventory_item_price']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckout() {
        clickElement(checkoutButton);
    }

    public Map<String,String> itemAdded() {
        Map<String,String> productPriceMap = new HashMap<>();
        // Wait for all "Add to cart" buttons to be visible and get them as a list
        List<WebElement> listName = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productName));
        List<WebElement> listPrice = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrice));
        for (int i = 0; i < listName.size() ; i++) {
            productPriceMap.put(listName.get(i).getText(),listPrice.get(i).getText());
        }
        return productPriceMap;
    }

}
