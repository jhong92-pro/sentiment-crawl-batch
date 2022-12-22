package com.example.CrawlBatch.service;
import com.example.CrawlBatch.dao.CrawlResultDao;
import com.example.CrawlBatch.model.CategoryCode;
import com.example.CrawlBatch.model.CrawlResult;
import com.example.CrawlBatch.model.DomainCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlDailyService {
    private final CrawlResultDao crawlResultDao;
    String userAgent = "Mozilla/5.0";
    String timeNow = "20221013";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hhmm");
    @Transactional
    public void crawlDaily() throws IOException, InterruptedException, ParseException {
        String URL = "https://www.fmkorea.com/index.php?mid=humor&category=486622&page="; // TODO: 펨코 크롤링 막혀있음
        int cnt = 1;
        while(cnt<100) {
            Thread.sleep(10000);
            saveCrawlResult(URL+cnt);
            cnt+=1;
        }
    }

    public void saveCrawlResult(String URL) throws IOException, ParseException {
        Connection conn = Jsoup.connect(URL)
                .userAgent(userAgent);
        Document document = conn.get();
        Elements titleElements = document.select("table.bd_lst.bd_tb_lst.bd_tb > tbody");
        Elements titleElements2 = titleElements.select("td > a.hx");
        Elements timeElements = titleElements.select("td.time");

        List<CrawlResult> crawlResultList = new ArrayList<>();

        for(int i=0;i<titleElements2.size();i++){
            String url = titleElements2.get(i).attr("href");
            String title = titleElements2.get(i).text();
            String time = timeElements.get(i).text();
            String ymd = time.replace(".","");
            if (crawlResultDao.existsByUrl(url)){
                continue;
            }
            if (ymd.length()==4){
                if (Integer.parseInt(timeNow) < Integer.parseInt(ymd)){ // or <
                    ymd = minusOneDay(ymd);
                }
            }
            System.out.printf("url : %s, text : %s, time : %s%n", url, title, time);
            CrawlResult crawlResult = CrawlResult.builder()
                    .title(title)
                    .ymd(ymd)
                    .category(CategoryCode.HUMOR_HUMOR)
                    .url(url)
                    .domain(DomainCode.FMKOREA)
                    .build();
            crawlResultList.add(crawlResult);
        crawlResultDao.saveAll(crawlResultList);
        }
    }

    private String minusOneDay(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date1 = formatter.parse(date);
        LocalDateTime currDate = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
        LocalDateTime yesterdayDate = currDate.minusDays(1);
        Date out = Date.from(yesterdayDate.atZone(ZoneId.systemDefault()).toInstant());
        return formatter.format(out);
    }
}
