package com.example.CrawlBatch.job;

import com.example.CrawlBatch.service.CrawlDailyIlbeService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IlbeJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CrawlDailyIlbeService crawlDailyIlbeService;

    @Bean("ilbeJob")
    public Job ilbeJob(Step ilbeStep){
        return jobBuilderFactory.get("ilbeJob")
                .incrementer(new RunIdIncrementer())
                .start(ilbeStep)
                .build();
    }


    @JobScope
    @Bean("ilbeStep")
    public Step ilbeStep(Tasklet ilbeTasklet) {
        return stepBuilderFactory.get("ilbeStep")
                .tasklet(ilbeTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet ilbeTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("ilbe Spring Batch");
            crawlDailyIlbeService.crawlIlbeDaily();
            return RepeatStatus.FINISHED;
        };
    }
}
