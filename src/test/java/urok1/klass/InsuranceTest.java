package urok1.klass;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InsuranceTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        baseUrl = "https://www.rgs.ru/";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @Test
    public void testInsurance() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//ol[contains(@class,'rgs-menu')]/li/*[contains(text(),'Страхование')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'ДМС')]")).click();


        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]"))));


        driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]")).click();


        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));


        assertEquals("Заявка на добровольное медицинское страхование",
                driver.findElement(By.xpath("//h4[@class='modal-title']")).getText());


        fillField(By.name("lastName"), "Иванов");
        fillField(By.name("firstName"), "Иван");
        fillField(By.name("middleName"), "Иванович");


        new Select(driver.findElement(By.name("region"))).selectByVisibleText("Москва");


        fillField(By.name("email"), "йцукенqwery");
        fillField(By.name("comment"), "test");




        driver.findElement(By.cssSelector("input.checkbox")).click();
        driver.findElement(By.id("button-m")).click();


        assertEquals("Иванов", driver.findElement(By.name("lastName")).getAttribute("value"));
        assertEquals("Иванович", driver.findElement(By.name("middleName")).getAttribute("value"));
        assertEquals("Иван", driver.findElement(By.name("firstName")).getAttribute("value"));
        assertEquals("йцукенqwery", driver.findElement(By.name("email")).getAttribute("value"));
        assertEquals("test", driver.findElement(By.name("comment")).getAttribute("value"));


        assertEquals("Москва",
                new Select(driver.findElement(By.name("region"))).getAllSelectedOptions().get(0).getText());
        assertEquals("Введите адрес электронной почты",
                driver.findElement(By.xpath("//*[text()='Эл. почта']/..//span[@class='validation-error-text']")).getText());
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
}
