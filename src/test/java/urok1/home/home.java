package urok1.home;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

public class home {
    private WebDriver driver;
    private String baseUrl;
    @Before
    //1. Перейти на страницу http://www.sberbank.ru/ru/person
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @Test
    public void testInsurance() throws Exception {
        Wait<WebDriver> wait = new WebDriverWait(driver,10, 1000);
        driver.get(baseUrl + "/");

        //2. Нажать на – Застраховать себя и имущество
        click(By.xpath("//*[contains(text(),'Застраховать себя')]"));

        //3. Выбрать – Страхование путешественников
        click(By.xpath("//*[contains(text(),'Страхование путешественников')]"));

        //4. Проверить наличие на странице заголовка – Страхование путешественников
        // тута чего-то неправильно проверяю???
        assertEquals("Страхование путешественников",
        driver.findElement(By.xpath("//div[@class='sbrf-rich-outer']")).getText());

        //5. Нажать на – Оформить Онлайн (самой кнопки нет, есть только информационный блок)
        click(By.xpath("//a//img [contains(@src,'/portalserver/content/atom/contentRepository/content/person/travel/banner-zashita-traveler.jpg?id=f6c836e1-5c5c-4367-b0d0-bbfb96be9c53')]"));
        ArrayList <String> NewTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(NewTab.get(1));

        //6. На вкладке – Выбор полиса  выбрать сумму страховой защиты – Минимальная
        click(By.xpath("//*[contains(text(),'Минимальная')]"));

        //7. Нажать Оформить
        click(By.xpath("//span [contains(text(),'Оформить')]"));

        //8.       На вкладке Оформить заполнить поля:
        //•       Фамилию и Имя, Дату рождения застрахованных
        fillField(By.name("insured0_surname"),"Fadeewa");
        fillField(By.name("insured0_name"),"Ekaterina");
        fillField(By.name("insured0_birthDate"),"23.03.1985");
        //•       Данные страхователя: Фамилия, Имя, Отчество, Дата рождения, Пол ((((сообразила как пол сменить :-))))))))
        fillField(By.name("surname"),"Фадеева");
        fillField(By.name("name"),"Екатерина");
        fillField(By.name("middlename"),"Игоревна");
        fillField(By.name("birthDate"),"23.03.1985");
        driver.findElement(By.name("female")).click();
        //•       Паспортные данные
        fillField(By.name("passport_series"),"3609");
        fillField(By.name("passport_number"),"123456");
        fillField(By.name("issueDate"),"01.01.2010");
        fillField(By.name("issuePlace"),"ОУФМС России по Москве");
        //•       Контактные данные не заполняем
     //   fillField(By.name("phone"),"(999) 999-9999");
     //   fillField(By.name("email"),"nom@km.ru");
     //   fillField(By.name("emailValid"),"nom@km.ru");

        //9.       Проверить, что все поля заполнены правильно
        assertEquals("Fadeewa",driver.findElement(By.name("insured0_surname")).getAttribute("value"));
        assertEquals("Ekaterina",driver.findElement(By.name("insured0_name")).getAttribute("value"));
        assertEquals("23.03.1985",driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));
        assertEquals("Фадеева",driver.findElement(By.name("surname")).getAttribute("value"));
        assertEquals("Екатерина",driver.findElement(By.name("name")).getAttribute("value"));
        assertEquals("Игоревна",driver.findElement(By.name("middlename")).getAttribute("value"));
        assertEquals("23.03.1985",driver.findElement(By.name("birthDate")).getAttribute("value"));
        assertEquals("1",driver.findElement(By.name("female")).getAttribute("value"));
        assertEquals("3609",driver.findElement(By.name("passport_series")).getAttribute("value"));
        assertEquals("123456",driver.findElement(By.name("passport_number")).getAttribute("value"));
        assertEquals("01.01.2010",driver.findElement(By.name("issueDate")).getAttribute("value"));
        assertEquals("ОУФМС России по Москве",driver.findElement(By.name("issuePlace")).getAttribute("value"));

        //10.   Нажать продолжить
        WebElement continueBatton = driver.findElement(By.xpath("//span [contains(text(),'Продолжить')]"));
        wait.until(ExpectedConditions.visibilityOf(continueBatton)).click();

        //11.   Проверить, что появилось сообщение - Заполнены не все обязательные поля
        assertEquals("Заполнены не все обязательные поля", driver.findElement(By.xpath("//*[text()='Заполнены не все обязательные поля']")).getText());
        }
    @After
        public void tearDown() throws Exception {
            driver.quit();
        }
        private void fillField(By locator, String value){
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(value);
        }
        private void click(By locator){
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(locator));
            Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}




