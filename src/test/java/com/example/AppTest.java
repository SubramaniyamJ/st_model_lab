package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;
import java.io.FileInputStream;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    WebDriver driver;
    FileInputStream fis;
    public static Logger log = LogManager.getLogger(AppTest.class);
    @BeforeTest
    public void setup() throws Exception{
        driver = new ChromeDriver();
        driver.get("https://www.opentable.com/");
        PropertyConfigurator.configure("C:\\Users\\Subramaniyam J\\Desktop\\ST_MODELLAB_AN\\st_modellab_an\\src\\resources\\log4j.properties");
        driver.manage().window().maximize();
        fis = new FileInputStream("D:\\Software Testing\\credentails.xlsx");
    }

    @Test
    public void TestCase1() throws Exception
    {
        log.info("TestCase1 started...");
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheet("opentable");
        String city = sh.getRow(1).getCell(0).getStringCellValue();

        log.info("Data has been retrieved from the string");
        driver.findElement(By.xpath("//*[@id='home-page-autocomplete-input']")).sendKeys(city);
        driver.findElement(By.xpath("//*[@id='mainContent']/header/div/span/div/div/div[2]/div[2]/button")).click();

        WebElement cusine = driver.findElement(By.xpath("//*[@id='mainContent']/div/section/div[6]"));

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", cusine);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]/span[2]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id='mainContent']/div/div/div[2]/div/div[2]/div[1]/div[1]/a/h6")).click();

        log.info("Redirected to next tab");
        List<String>handlers = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(handlers.get(1));

        js.executeScript("window.scrollBy(0, 500)");
        Select partysize = new Select(driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpPartySizePicker\"]")));
        partysize.selectByVisibleText("4 people");

        driver.findElement(By.xpath("//*[@id='restProfileSideBarDtpDayPicker']/div/div")).click();

        Thread.sleep(3000);
        while(true){
            WebElement date = driver.findElement(By.xpath("//*[@id='restProfileSideBarDtpDayPicker-wrapper']/div/div/div/div"));
            String[] part = date.getText().split(" ");
            String month = part[0];
            String year = part[1];

            if(month.equals("November") && year.equals("2024")){
                driver.findElement(By.xpath("//*[@id='restProfileSideBarDtpDayPicker-wrapper']/div/div/div/table/tbody/tr[3]/td[2]/button")).click();
                break;
            }

            driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]")).click();
        }
        Thread.sleep(1000);
        Select datedd = new Select(driver.findElement(By.xpath("//*[@id=\"restProfileSideBartimePickerDtpPicker\"]")));
    
        datedd.selectByVisibleText("6:30 PM");

        log.info("Date and Time has been selected");

        driver.findElement(By.xpath("//*[@id='baseApp']/div/header/div[2]/div[2]/div[1]/button")).click();

        log.info("Testcase Exceuted Succesfullt");
    }

    @AfterTest
    public void setdown() throws Exception{
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }




}
