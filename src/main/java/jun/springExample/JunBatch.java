package jun.springExample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
public class JunBatch {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("myJob").start(stepBuilderFactory.get("firstTaskJob").tasklet(firstTask()).build())
                .next(jobExecutionDecider())
                .on("COMPLETED").to(junFlow()).next(junStep())
                .from(jobExecutionDecider())
                .on("FAILED").to(junStep()).end().build();
    }

    @Bean
    public Flow junFlow() {
        return new FlowBuilder<Flow>("junFlow").build();
    }

    @Bean
    @StepScope
    public Tasklet firstTask() {
        return new FirstTask();
    }

    @Bean
    public JobExecutionDecider jobExecutionDecider() {
        return new JunJobDecider();
    }

    @Bean
    public Step junStep() {
        return stepBuilderFactory.get("junStep").tasklet(new JunStep()).build();
    }

}
