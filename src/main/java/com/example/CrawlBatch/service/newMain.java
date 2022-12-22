package com.example.CrawlBatch.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class newMain {
    public static void main(String[] args) throws IOException, ParseException {
        String timeNow = "20221013";
        String URL = "https://www.fmkorea.com/index.php?mid=humor&category=486622&page=61";
        Connection conn = Jsoup.connect(URL);
        Document document = conn.get();
        Elements titleElements = document.select("table.bd_lst.bd_tb_lst.bd_tb > tbody");
        Elements titleElements2 = titleElements.select("td > a.hx");
        Elements timeElements = titleElements.select("td.time");
//        System.out.println(titleElements);
//        System.out.println(titleElements2);
//        System.out.println(titleElements2.get(0).attr("href"));
        for(int i=0;i<titleElements2.size();i++){
            String url = titleElements2.get(i).attr("href");
            String text = titleElements2.get(i).text();
            String time = timeElements.get(i).text();
            System.out.printf("url : %s, text : %s, time : %s%n", url, text, time);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("hhmm");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date date = formatter.parse(timeNow);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDateTime localDateTime2 = localDateTime.minusDays(1);
            Date out = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
            String dt = dtf2.format(LocalDateTime.now());
            System.out.println(timeNow);
            System.out.println(out);

        }
        System.out.println();
    }
}

