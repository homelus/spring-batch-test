package jun.example;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowExampleConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(stepZero())
                .next(stepA())
                .on("C*D").to(stepB())
                .from(stepA()).on("F*D").to(stepC())
                .end()
                .build();
    }

    @Bean
    public Step stepZero() {
        return stepBuilderFactory.get("stepZero").tasklet((c, s) -> {
            System.out.println("step Zero called!");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public JobExecutionDecider stepA() {
        return (j1, j2) -> {
            System.out.println("StepA Failed");
            return FlowExecutionStatus.FAILED;
        };
    }

    @Bean
    public JobExecutionDecider stepB() {
        return (j1, j2) -> {
            System.out.println("StepB Completed");
            return FlowExecutionStatus.COMPLETED;
        };
    }

    @Bean
    public JobExecutionDecider stepC() {
        return (j1, j2) -> {
            System.out.println("StepC Completed");
            return FlowExecutionStatus.COMPLETED;
        };
    }


}
