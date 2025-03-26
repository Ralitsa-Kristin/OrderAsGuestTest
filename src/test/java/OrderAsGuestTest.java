import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrderAsGuestTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://cosmetic-express.bg/product/kocostar-sunscreen-capsule-mask-spf50-pa/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void OrderAsGuest() {
        WebElement addToCartButton = driver.findElement(By.name("add-to-cart"));
        addToCartButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ic-cart")));
        WebElement cartButton = driver.findElement(By.className("ic-cart"));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button.checkout-button")));
        WebElement checkoutButton = driver.findElement(By.cssSelector(".button.checkout-button"));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("billing_first_name")));
        WebElement firstNameInputField = driver.findElement(By.id("billing_first_name"));
        firstNameInputField.sendKeys("Test");
        WebElement lastNameInputField = driver.findElement(By.id("billing_last_name"));
        lastNameInputField.sendKeys("Test Test");
        WebElement billingAddressInputField = driver.findElement(By.id("billing_address_1"));
        billingAddressInputField.sendKeys("str.Test1");
        WebElement billingCityInputField = driver.findElement(By.id("billing_city"));
        billingCityInputField.sendKeys("Sofia");
        WebElement postcodeInputField = driver.findElement(By.id("billing_postcode"));
        postcodeInputField.sendKeys("1000");
        WebElement phoneInputField = driver.findElement(By.id("billing_phone"));
        phoneInputField.sendKeys("0881122334");
        String randomPrefix = RandomStringUtils.secure().nextAlphabetic(7);
        String randomDomain = RandomStringUtils.secure().nextAlphabetic(5);

        String randomEmail = randomPrefix + "@" + randomDomain + ".com";
        WebElement emailInputField = driver.findElement(By.id("billing_email"));
        emailInputField.sendKeys(randomEmail);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement shippingSelection = driver.findElement(By.id("shipping_selection_address"));
        js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", shippingSelection);
        WebElement placeOrderButton = driver.findElement(By.id("place_order"));
        //placeOrderButton.click();

        WebElement confirmationMessage = driver.findElement(By.cssSelector("#header_container .title"));
        String confirmationMessageText = confirmationMessage.getText();

        Assert.assertEquals(confirmationMessageText,"Thank you for your order!");

    }
}
