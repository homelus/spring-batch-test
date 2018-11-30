package jun.partition;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class PartitionConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    ApplicationContext context;

    @Bean
    public Job partitionerJob() {
        return jobBuilderFactory.get("partitioningJob")
                .start(partitionStep())
                .build();
    }

    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("slaveStep", partitioner())
                .step(slaveStep())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step slaveStep() {
        return null;
    }

    @Bean
    public CustomMultiResourcePartitioner partitioner() {
        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
        Resource[] resources;

        try {
            resources = context.getResources("file:src/main/resources/input/*.csv");
        } catch (IOException e) {
            throw new RuntimeException("I/O problems when resolving the input file Pattern", e);
        }

        partitioner.setResources(resources);
        return partitioner;
    }


}
