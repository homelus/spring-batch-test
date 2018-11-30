package jun.springExample.config;

import jun.springExample.tasklet.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class FlowTestConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        Flow junFlow = new FlowBuilder<SimpleFlow>("jun-flow")
                .start(helloJunStep())
                .next(goodByeJunStep())
                .build();

        Flow minFlow = new FlowBuilder<SimpleFlow>("min-flow")
                .start(helloMinStep())
                .next(goodByeMinStep())
                .build();


        return jobBuilderFactory.get("every-flow")
                                .start(new FlowBuilder<SimpleFlow>("test").end())
                                .split(new SimpleAsyncTaskExecutor("Min"))
                                .add(minFlow)
                                .next(goodByeEverybodyStep())
                                .end()
                                .build();

    }

    @Bean
    public Step helloJunStep() {
        return stepBuilderFactory.get("helloJun").tasklet(new HelloJunTasklet()).build();
    }

    @Bean
    public Step goodByeJunStep() {
        return stepBuilderFactory.get("goodByeJun").tasklet(new GoodByeJunTasklet()).build();
    }

    @Bean
    public Step helloMinStep() {
        return stepBuilderFactory.get("helloMin").tasklet(new HelloMinTasklet()).build();
    }

    @Bean
    public Step goodByeMinStep() {
        return stepBuilderFactory.get("goodByeMin").tasklet(new GoodByeMinTasklet()).build();
    }

    @Bean
    public Step goodByeEverybodyStep() {
        return stepBuilderFactory.get("goodByeEverybody").tasklet(new GoodByeEverybodyTasklet()).build();
    }



}
