package ru.ag.heroes;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс содержит простые UI-тесты сайта https://heroes.ag.ru
 */
public class Tests {
    private WebDriver driver;
    private final List<String> listOfCreatures = new ArrayList<>();
    private final List<String> listOfAttack = new ArrayList<>();
    private final List<String> listOfDefense = new ArrayList<>();
    private final List<String> listOfDamage = new ArrayList<>();
    private final List<String> listOfHitPoints = new ArrayList<>();
    private final List<String> listOfSpeed = new ArrayList<>();
    private final List<String> listOfInitiative = new ArrayList<>();
    private final List<String> listOfShots = new ArrayList<>();
    private final List<String> listOfMana = new ArrayList<>();
    private final List<String> listOfGrowthRate = new ArrayList<>();
    private final List<String> listOfGold = new ArrayList<>();
    private final List<String> listOfResource = new ArrayList<>();
    private final List<String> listOfExperience = new ArrayList<>();

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
        List<String> listOfFractions = new ArrayList<>();
        Collections.addAll(listOfFractions, "Academy", "Dungeon", "Haven", "Inferno", "Necropolis", "Sylvan", "Fortress", "Stronghold");

        //Переход по ссылке "Heroes V"
        driver.findElement(By.xpath("//a[text()='Heroes V']")).click();

        //Переход по ссылке "Существа"
        driver.findElement(By.xpath("//a[@href='creatures/index.html']")).click();

        for (int i = 0; i < 8; i++) {
            //Вызываем метод получения информации о существах для каждой фракции
            getCreaturesInfo(driver.findElement(By.xpath("//a[text()='" + listOfFractions.get(i) + "']")));
            //Сохранение информации о существах в файл
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(listOfFractions.get(i) + "Creatures.txt");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(listOfCreatures.toString());
                outputStreamWriter.close();
                listOfCreatures.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCreaturesInfo(WebElement webElement) {
        //Переход по ссылке фракции
        webElement.click();

        for (int number = 1; number <= 21; number++) {
            WebElement creature = driver.findElement(By.xpath("(//div[@class='header1'])[" + number + "]"));

            WebElement attack = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][1]"));
            WebElement defense = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][1]"));
            WebElement damage = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][1]"));
            WebElement hitPoints = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][1]"));

            WebElement speed = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][2]"));
            WebElement initiative = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][2]"));
            WebElement shots = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][2]"));
            WebElement mana = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][2]"));

            WebElement growthRate = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[1]//td[@class='cells'][3]"));
            WebElement gold = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[2]//td[@class='cells'][3]"));
            WebElement resource = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[3]//td[@class='cells'][3]"));
            WebElement experience = driver.findElement(By.xpath("(//td[@class='cbg2'])[" + number + "]//tr[4]//td[@class='cells'][3]"));

            listOfCreatures.add(creature.getText());
            listOfAttack.add(attack.getText());
            listOfDefense.add(defense.getText());
            listOfDamage.add(damage.getText());
            listOfHitPoints.add(hitPoints.getText());
            listOfSpeed.add(speed.getText());
            listOfInitiative.add(initiative.getText());
            listOfShots.add(shots.getText());
            listOfMana.add(mana.getText());
            listOfGrowthRate.add(growthRate.getText());
            listOfGold.add(gold.getText());
            listOfResource.add(resource.getText());
            listOfExperience.add(experience.getText());
        }
        System.out.println("Существо " + listOfCreatures);
        System.out.println("Атака " + listOfAttack);
        System.out.println("Защита " + listOfDefense);
        System.out.println("Урон " + listOfDamage);
        System.out.println("Здоровье " + listOfHitPoints);
        System.out.println("Скорость " + listOfSpeed);
        System.out.println("Инициатива " + listOfInitiative);
        System.out.println("Выстрелы " + listOfShots);
        System.out.println("Мана " + listOfMana);
        System.out.println("Прирост " + listOfGrowthRate);
        System.out.println("Золото " + listOfGold);
        System.out.println("Ресурс " + listOfResource);
        System.out.println("Опыт " + listOfExperience);
    }
}