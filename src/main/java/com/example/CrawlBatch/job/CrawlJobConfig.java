//package com.example.CrawlBatch.job;
//
//import com.example.CrawlBatch.service.CrawlDailyIlbeService;
//import com.example.CrawlBatch.service.CrawlDailyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class CrawlJobConfig {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final CrawlDailyService crawlDailyService;
//
//    @Bean("crawlJob")
//    public Job crawlJob(Step crawlStep){
//        return jobBuilderFactory.get("crawlJob")
//                .incrementer(new RunIdIncrementer())
//                .start(crawlStep)
//                .build();
//    }
//
//
//    @JobScope
//    @Bean("helloStep")
//    public Step helloStep(Tasklet tasklet) {
//        return stepBuilderFactory.get("helloStep")
//                .tasklet(tasklet)
//                .build();
//    }
//
//    @StepScope
//    @Bean
//    public Tasklet tasklet() {
//        return (contribution, chunkContext) -> {
//            System.out.println("Hello Spring Batch");
//            crawlDailyService.crawlDaily();
//            return RepeatStatus.FINISHED;
//        };
//    }
//}
