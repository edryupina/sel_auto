package urok1.klass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        click(By.xpath("//*[contains(text(),'Застраховать себя')]"));
        click(By.xpath("//*[contains(text(),'Страхование путешественников')]"));
        // тута чего-то неправильно проверяю
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
        fillField(By.name("insured0_surname"),"Dryupina");
        fillField(By.name("insured0_name"),"Ekaterina");
        fillField(By.name("insured0_birthDate"),"23.03.1985");
        //•       Данные страхователя: Фамилия, Имя, Отчество, Дата рождения, Пол
        fillField(By.name("surname"),"Дрюпина");
        fillField(By.name("name"),"Екатерина");
        fillField(By.name("middlename"),"Игоревна");
        fillField(By.name("birthDate"),"23.03.1985");
        fillField(By.name("female"), "1");


  //      <h4 class="b-form-label b-text-field-label">Пол</h4>
//<span ng-class="{'b-radio-field': 1, 'b-checked-radio-field': formdata.insurer.GENDER == 1}" data-field="true" class="b-radio-field b-checked-radio-field">
  //      <input ng-model="formdata.insurer.GENDER" type="radio" name="female" class="b-radio-field-entity ng-valid ng-dirty ng-touched ng-valid-parse" value="1"></span>
//<label class="b-form-label b-inline-lable">женский</label>
//<span ng-class="{'b-radio-field': 1, 'b-checked-radio-field': formdata.insurer.GENDER == 1}" data-field="true" class="b-radio-field b-checked-radio-field">
  //     <input ng-model="formdata.insurer.GENDER" type="radio" name="female" class="b-radio-field-entity ng-valid ng-dirty ng-touched ng-valid-parse" value="1"></span>

        //<input ng-model="formdata.insurer.GENDER" type="radio" name="male" class="b-radio-field-entity ng-valid ng-dirty ng-touched" value="0">
        //•       Паспортные данные
        fillField(By.name("passport_series"),"36 09");
        fillField(By.name("passport_number"),"123456");
        fillField(By.name("issueDate"),"01.01.2001");
        fillField(By.name("issuePlace"),"ОУФМС России по Москве");

        //•       Контактные данные не заполняем
        //9.       Проверить, что все поля заполнены правильно



        //10.   Нажать продолжить
        WebElement continueBatton = driver.findElement(By.xpath("//span [contains(text(),'Продолжить')]"));
        wait.until(ExpectedConditions.visibilityOf(continueBatton)).click();

        //11.   Проверить, что появилось сообщение - Заполнены не все обязательные поля


        WebElement countTravelBtn =  driver.findElement(By.xpath("//*[contains(text(),'в течение года')]/.."));
        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(text(),'Расчет')]")));
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(countTravelBtn)).click();
        fillField(By.xpath("//*[contains(text(),'Куда едем')]/ancestor::div[contains(@class,'form-group')]//input"), "Шенген");
        driver.findElement(By.xpath("//*[contains(text(),'Куда едем')]/ancestor::div[contains(@class,'form-group')]//div[@role='listbox']")).click();
        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);",  driver.findElement(By.name("ArrivalCountryList")));
        driver.findElement(By.name("ArrivalCountryList")).click();
        driver.findElement(By.xpath("//option[text()='Испания']")).click();
        fillField(By.xpath("//*[contains(text(),'Дата первой поездки')]/parent::div//input"), "12122017");
        new Actions(driver).sendKeys( driver.findElement(By.xpath("//*[contains(text(),'Дата первой поездки')]/parent::div//input")), Keys.TAB);
        fillField(By.xpath("(//input[@data-test-name='FullName'])[1]"), "IVAN IVANOV");
        fillField(By.xpath("//input[@data-test-name='BirthDate']"), "12121990");
        click(By.xpath("//*[contains(text(),'активный отдых')]/ancestor::div/div[@class='toggle off toggle-rgs']"));
        click(By.xpath("//div[@class='form-footer']//input[@data-test-name='IsProcessingPersonalData']/.."));
        click(By.xpath("//button[@data-test-name='NextButton'][contains(text(),'Рассчитать')]"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class,'programs')]"))));
        assertEquals("Многократные поездки в течение года",
        driver.findElement(By.xpath("//*[contains(text(),'Условия страхования')]/parent::div//strong")).getText());
        assertEquals("Шенген",
        driver.findElement(By.xpath("//*[contains(text(),'Территория')]/parent::div//strong")).getText());
        assertEquals("12.12.1990",
        driver.findElement(By.xpath("//*[contains(text(),'Застрахованный')]/parent::div//strong[contains(@data-bind,'BirthDate')]")).getText());
        assertEquals("IVAN IVANOV",
        driver.findElement(By.xpath("//*[contains(text(),'Застрахованный')]/parent::div//strong[contains(@data-bind,'LastName')]")).getText());
        assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Активный отдых')]/ancestor::div[@class='summary-row']//strong")).getText().contains("Включен"));
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




