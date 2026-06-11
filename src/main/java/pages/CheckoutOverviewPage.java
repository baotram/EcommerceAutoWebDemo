package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;

public class CheckoutOverviewPage extends BasePage {

    // Locators
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getSubtotalText() {
        return getText(subtotalLabel);
    }

    public BigDecimal getSubtotalValue() {
        String text = getSubtotalText();
        String price = text.replace("Item total: $", "").trim();
        return BigDecimal.valueOf(Double.valueOf(price));
    }

    public String getTotalPriceText() {
        return getText(totalLabel);
    }

    public double getTotalPriceValue() {
        String text = getTotalPriceText();
        String price = text.replace("Total: $", "").trim();
        return Double.parseDouble(price);
    }

    public void clickFinish() {
        clickElement(finishButton);
    }
}
