package com.example.CrawlBatch.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class newMain2 {
    public static void main(String[] args) throws IOException, ParseException {
        String timeNow = "20221013";
        String URL = "https://www.ilbe.com/view/11447167727";
        Connection conn = Jsoup.connect(String.format(URL,1));


        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "C:\\Users\\Jun Seung Hong\\OneDrive\\바탕 화면\\Coding\\project\\Crawling\\src\\main\\resources\\chromedriver.exe";
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless"); // 크롬 창 띄우지 않음
        WebDriver driver = new ChromeDriver(options);

        driver.get(URL);
        WebElement bodyElement = driver.findElement(By.xpath("//*[@id='content']"));
        System.out.println(driver.getPageSource());
        System.out.println("here!");
        System.out.println("bodyElement "+ bodyElement.getText());
        System.out.println("bodyElement "+ bodyElement.getAttribute("value"));
        String body = bodyElement.getAttribute("value")
                .replaceAll("http://[^\\s]+", "")
                .replaceAll("< ?img(.*?)>", "") // 이미지 제거
                .replaceAll("<[^>]*>", ""); // 태그 제거
        List<String> sentenceList = new ArrayList<>(driver.findElements(By.className("cmt"))
                .stream()
                .map(webElement -> webElement.getText()
                                .replaceAll("http[s|]://[^\\s]+", "")
                                .replaceAll("<[^>]*>", "")
                                )
                .toList());
        sentenceList.add(body);
        System.out.println(Arrays.toString(sentenceList.toArray()));

    }
}

