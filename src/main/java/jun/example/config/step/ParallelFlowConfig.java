package jun.example.config.step;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class ParallelFlowConfig {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job parallelFlowJob() {

        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(stepA())
                .next(stepB())
                .build();

        Flow flow2 = new FlowBuilder<SimpleFlow>("flow2")
                .start(stepC())
                .build();

        return this.jobBuilderFactory.get("parallelFlowJob")
                .start(flow1)
                .split(new SimpleAsyncTaskExecutor())
                .add(flow2)
                .next(stepD())
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
    public Step stepA() {
        return stepBuilderFactory.get("stepA").tasklet((c, s) -> {
            System.out.println("Step A !! : " + Thread.currentThread().getName());
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB").tasklet((c, s) -> {
            System.out.println("Step B !! : " + Thread.currentThread().getName());
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepC() {
        return stepBuilderFactory.get("stepC").tasklet((j1, j2) -> {
            System.out.println("StepC Completed : "  + Thread.currentThread().getName());
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepD() {
        return stepBuilderFactory.get("stepD").tasklet((j1, j2) -> {
            System.out.println("StepD Completed :" + Thread.currentThread().getName());
            return RepeatStatus.FINISHED;
        }).build();
    }

}
