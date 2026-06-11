package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LangReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryPage extends BasePage {
    // Locators
    private final By titleSpan = By.cssSelector("span.title");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By addToCartBtn = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By productName = By.xpath("//div[@class = 'inventory_item_name ']");
    private final By productPrice = By.xpath("//div[@class = 'inventory_item_price']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            return getText(titleSpan).equals(LangReader.get("title.products"));
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String,String> addRandomNumberOfProductAndReturnProductAdded(Integer numberOfProduct) {
        Map<String,String> productPriceMap = new HashMap<>();
        // Wait for all "Add to cart" buttons to be visible and get them as a list
        List<WebElement> listAddCartBtn = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartBtn));
        List<WebElement> listName = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productName));
        List<WebElement> listPrice = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrice));

        // Click up to numberOfProduct buttons (or available count if fewer)
        int toClick = Math.min(numberOfProduct, listAddCartBtn.size());
        for (int i = 0; i < toClick; i++) {
            productPriceMap.put(listName.get(i).getText(),listPrice.get(i).getText());
            listAddCartBtn.get(i).click();
        }
        return productPriceMap;
    }

    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(getText(cartBadge).strip());
        } catch (Exception e) {
            return 0; // badge is not displayed if cart is empty
        }
    }

    public void clickCart() {
        clickElement(cartLink);
    }
}
