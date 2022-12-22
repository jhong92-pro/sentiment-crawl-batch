package com.example.CrawlBatch.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Slf4j
public class newMain3 {
    public static void main(String[] args) throws IOException, ParseException {
        String a = "2012*12*12";
        String b = a.substring(2,4) + a.substring(5,7) + a.substring(8,10);
        System.out.println(b);

    }
}