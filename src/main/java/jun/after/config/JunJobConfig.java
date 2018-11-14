package jun.after.config;

import jun.after.listener.JunExecutionJobListener;
import jun.after.JunNumberTask;
import jun.support.JunTaskletInitializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableBatchProcessing
public class JunJobConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    @DependsOn(value = {"junTaskletInitializer"})
    public Job junBatchJob() {
        return jobBuilderFactory.get("junJobBatch")
                .listener(junExecutionJobListener())
                .start(new FlowBuilder<Flow>("junFlow").end())
                .next(getJunNumber())
                .end().build();
    }

    @Bean
    @JobScope
    public JunExecutionJobListener junExecutionJobListener() {
        return new JunExecutionJobListener();
    }

    @Bean
    public Step getJunNumber() {
        return stepBuilderFactory.get("getJunNumber").tasklet(new JunNumberTask()).build();
    }

    @Bean
    public JunTaskletInitializer junTaskletInitializer() {
        return new JunTaskletInitializer();

    }

}
