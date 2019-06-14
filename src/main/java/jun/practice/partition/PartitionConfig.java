package jun.practice.partition;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class PartitionConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job junPartitioningJob(@Qualifier("junPartitionStep") Step junPartitionStep) {
        return jobBuilderFactory.get("junPartitioningJob").start(new FlowBuilder<Flow>("flow").end())
                .split(new SimpleAsyncTaskExecutor("Jun_Split_"))
                .add(taskletFlow())
                .next(junPartitionStep)
                .next(checkNosOk())
                .end()
                .build();
    }

    public Flow taskletFlow() {
        return new FlowBuilder<Flow>("flow").from(stepBuilderFactory.get("initTasklet").tasklet(junInitTasklet()).build()).end();
    }

    @Bean
    Step checkNosOk() {
        return stepBuilderFactory.get("checkNos").tasklet(junCheckTasklet()).build();
    }

    @Bean
    public SharedNos sharedNos() {
        return new SharedNos();
    }

    @Bean
    public Tasklet junInitTasklet() {
        return new JunInitTasklet();
    }

    @Bean
    public Tasklet junCheckTasklet() {
        return new JunCheckNos();
    }

    @Bean
    @Qualifier("junPartitionStep")
    public Step junPartitionStep(@Qualifier("junNoPartitionHandler") PartitionHandler junNoPartitionHandler) {
        return stepBuilderFactory.get("junPartitionStep")
                .partitioner("junPartition", junPartition())
                .partitionHandler(junNoPartitionHandler)
                .build();
    }

    @Bean
    public JunPartition junPartition() {
        return new JunPartition();
    }

    @Bean
    public StepCompleteListener stepCompleteListener() {
        return new StepCompleteListener();
    }

    @Bean
    public PartitionHandler junNoPartitionHandler(@Qualifier("junRPWStep") Step junRPWStep) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setGridSize(10);
        handler.setTaskExecutor(junExecutor());
        handler.setStep(junRPWStep);

        return handler;
    }

    @Bean
    public TaskExecutor junExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Jun_P_E_");
        executor.setCorePoolSize(8);
        executor.setKeepAliveSeconds(600);
        executor.setQueueCapacity(1500);
        return executor;
    }

    @Bean
    @Qualifier("junRPWStep")
    public Step junRPWStep(JunItemReader junItemReader) {
        return stepBuilderFactory.get("junRPWStep").<List<String>, List<JunNo>>chunk(2)
                .reader(junItemReader)
                .processor(junItemProcessor())
                .writer(junItemWriter())
                .faultTolerant()
                .retryLimit(2).retry(RuntimeException.class)
                .listener(stepCompleteListener())
                .build();
    }

    @Bean
    @StepScope
    public JunItemReader junItemReader(
            @Value("#{stepExecutionContext[startNo]}") final Integer startNo,
            @Value("#{stepExecutionContext[endNo]}") final Integer endNo,
            SharedNos sharedNos) {
        return new JunItemReader(startNo, endNo, sharedNos);
    }

    @Bean
    public JunItemProcessor junItemProcessor() {
        return new JunItemProcessor();
    }

    @Bean
    public JunItemWriter junItemWriter() {
        return new JunItemWriter();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
