package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutCompletePage extends BasePage {

    // Locators
    private final By completeHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isCompleteHeaderDisplayed() {
        return isElementDisplayed(completeHeader);
    }

    public String getCompleteHeaderText() {
        return getText(completeHeader);
    }
}
