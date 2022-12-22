package com.example.CrawlBatch.service;

import com.example.CrawlBatch.dao.CrawlResultDao;
import com.example.CrawlBatch.model.CategoryCode;
import com.example.CrawlBatch.model.CrawlResult;
import com.example.CrawlBatch.model.DomainCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlDailyIlbeService {
    private final CrawlResultDao crawlResultDao;
    String userAgent = "Mozilla/5.0";
    @Value("${time.now}")
    private String timeNow;
    @Value("${page.end}")
    private int end;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hhmm");
    @Transactional
    public void crawlIlbeDaily() throws IOException, InterruptedException, ParseException {
        String URL = "https://www.ilbe.com/list/politics?page=%d&listStyle=list";
        int start = 0;
        while(start<end) {
            saveCrawlResult(String.format(URL,start));
            Thread.sleep(5000); // 크롤링하는 서버의 과부하를 줄이기 위해
            start+=1;
        }
    }

    public void saveCrawlResult(String URL) throws IOException, ParseException {
        Connection conn = Jsoup.connect(URL)
                .userAgent(userAgent);
        Document document = conn.get();
        Elements titleElements = document.select("ul.board-body > li:not([class])");
//        Elements timeElements = titleElements.select("td.time");
        List<CrawlResult> crawlResultList = new ArrayList<>();
        log.info(String.valueOf(titleElements));
        for(int i=0;i<titleElements.size();i++){
            String url = titleElements.get(i).select("span.title > a").get(0).attr("href");
            String title = titleElements.get(i).select("span.title > a").get(0).text();
            String time = titleElements.get(i).select("span.date").get(0).text();
            String ymd;
            if (crawlResultDao.existsByUrl(url)){
                continue;
            }
            if (time.contains(":")){
                ymd = timeNow;
            }
            else{
                ymd = time.substring(0,4) + time.substring(5,7) + time.substring(8,10);
            }
            System.out.printf("url : %s, text : %s, time : %s%n", url, title, time);
            CrawlResult crawlResult = CrawlResult.builder()
                    .title(title)
                    .ymd(ymd)
                    .category(CategoryCode.POLITICS)
                    .url(url)
                    .domain(DomainCode.ILBE)
                    .build();
            crawlResultList.add(crawlResult);
        }
        System.out.println(Arrays.toString(crawlResultList.toArray()));
        crawlResultDao.saveAll(crawlResultList);
    }
}
