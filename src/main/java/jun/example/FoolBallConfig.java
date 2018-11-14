package jun.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FoolBallConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job footballJob() {
        return jobBuilderFactory.get("footballJob")
                .start(playerLoad())
                .next(gameLoad())
                .next(playerSummarization())
                .build();
    }

//    @Bean
    public Step playerLoad() {
        return this.stepBuilderFactory.get("playerLoad")
                .<String, String>chunk(10)
//                .reader(playerFileItemReader())
//                .writer(playerWriter())
                .build();
    }

//    @Bean
    public Step gameLoad() {
        return this.stepBuilderFactory.get("gameLoad")
                .allowStartIfComplete(true)
                .<String, String>chunk(10)
//                .reader(gameFileItemReader())
//                .writer(gameWriter())
                .build();
    }

//    @Bean
    public Step playerSummarization() {
        return this.stepBuilderFactory.get("playerSummarization")
                .startLimit(3)
                .<String, String>chunk(10)
//                .reader(playerSummarizationSource())
//                .writer(summaryWriter())
                .build();
    }

}
