package ru.ag.heroes;

import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Класс содержит простые UI-тесты сайта https://heroes.ag.ru
 */
public class Tests {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://heroes.ag.ru/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Проверка гремлинов")
    public void GremlinsCheckTest() {
        //Переход по ссылке "Heroes V"
        driver.findElement(By.xpath("//a[text()='Heroes V']")).click();

        //Переход по ссылке "Существа"
        driver.findElement(By.xpath("//a[@href='creatures/index.html']")).click();

        //Переход по ссылке "Academy"
        driver.findElement(By.xpath("//a[text()='Academy']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("(//*[@class='header1'])[1]")).getText(), "1. Gremlin (Гремлины)", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[1]//td[@class='cells'][1]")).getText(), "2", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[2]//td[@class='cells'][1]")).getText(), "2", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[3]//td[@class='cells'][1]")).getText(), "1-2", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[4]//td[@class='cells'][1]")).getText(), "5", "Ошибка!");

        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[1]//td[@class='cells'][2]")).getText(), "3", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[2]//td[@class='cells'][2]")).getText(), "7", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[3]//td[@class='cells'][2]")).getText(), "5", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[4]//td[@class='cells'][2]")).getText(), "-", "Ошибка!");

        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[1]//td[@class='cells'][3]")).getText(), "20", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[2]//td[@class='cells'][3]")).getText(), "22", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[3]//td[@class='cells'][3]")).getText(), "", "Ошибка!");
        Assert.assertEquals(driver.findElement(By.xpath("(//td[@class='cbg2'])[1]//tr[4]//td[@class='cells'][3]")).getText(), "6", "Ошибка!");
    }

    @Test(description = "Получение информации о существах")
    public void getCreaturesInfoTest() {
        Map<String, Map<String, Map<String, String>>> mapOfFractions = new HashMap<>();
        List<String> listOfFractions = new ArrayList<>();
        Collections.addAll(listOfFractions, "Academy", "Dungeon", "Haven", "Inferno", "Necropolis", "Sylvan", "Fortress", "Stronghold");

        //Переход по ссылке "Heroes V"
        driver.findElement(By.xpath("//a[text()='Heroes V']")).click();

        //Переход по ссылке "Существа"
        driver.findElement(By.xpath("//a[@href='creatures/index.html']")).click();

        //Сохранение значений параметров всех существ всех фракций
        for (String fraction : listOfFractions)
            mapOfFractions.put(fraction, createMapOfValuesOfCreatures(driver.findElement(By.xpath("//a[text()='" + fraction + "']"))));

        JSONObject jsonObject = new JSONObject(mapOfFractions);

        //Сохранение информации о существах в json-файл
        try (FileOutputStream fileOutputStream = new FileOutputStream("Creatures.json");
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream)) {
            outputStreamWriter.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Вывод json в консоль
        System.out.println(jsonObject);
    }

    public Map<String, Map<String, String>> createMapOfValuesOfCreatures(WebElement webElement) {
        Map<String, Map<String, String>> mapOfValuesOfCreatures = new HashMap<>();

        //Переход по ссылке фракции
        webElement.click();

        //Сохранение значений параметров всех существ фракции
        for (int number = 1; number <= 21; number++) {
            Map<String, String> mapOfValuesOfCreaturesParameters = new HashMap<>();

            mapOfValuesOfCreaturesParameters.put("attack", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][1]")).getText());
            mapOfValuesOfCreaturesParameters.put("defense", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][1]")).getText());
            mapOfValuesOfCreaturesParameters.put("damage", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][1]")).getText());
            mapOfValuesOfCreaturesParameters.put("hitPoints", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][1]")).getText());
            mapOfValuesOfCreaturesParameters.put("speed", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][2]")).getText());
            mapOfValuesOfCreaturesParameters.put("initiative", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][2]")).getText());
            mapOfValuesOfCreaturesParameters.put("shots", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][2]")).getText());
            mapOfValuesOfCreaturesParameters.put("mana", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][2]")).getText());
            mapOfValuesOfCreaturesParameters.put("growthRate", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][3]")).getText());
            mapOfValuesOfCreaturesParameters.put("gold", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][3]")).getText());
            mapOfValuesOfCreaturesParameters.put("resource", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][3]")).getText());
            mapOfValuesOfCreaturesParameters.put("experience", driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][3]")).getText());

            mapOfValuesOfCreatures.put(driver.findElement(By.xpath("(//div[@class='header1'])[" + number + "]")).getText(), mapOfValuesOfCreaturesParameters);
        }
        return mapOfValuesOfCreatures;
    }
}