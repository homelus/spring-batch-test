package jun.practice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job junJob() {
        return jobBuilderFactory.get("batchConfig")
                .repository(jobRepository)
                .start(testStep())
                .build();
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("testStep").tasklet(testTasklet()).build();
    }

    @Bean
    public Tasklet testTasklet() {
        return new TestTasklet();
    }

    @Bean
    public DefaultInfraConfig batchInfraConfiguration() {
        return new DefaultInfraConfig();
    }

    public String getTablePrefix() {
        return "BATCH_JUN_";
    }

    @Bean
    public BatchConfigurer batchConfigurer() {
        return new BatchConfigurer() {

            private JobRepository jobRepository;
            private JobLauncher jobLauncher;
            private JobExplorer jobExplorer;
            private PlatformTransactionManager transactionManager;

            @Override
            public JobRepository getJobRepository() throws Exception {
                if (this.jobRepository == null) {
                    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
                    factory.setDataSource(batchInfraConfiguration().getDataSource());
                    factory.setTablePrefix(getTablePrefix());
                    factory.setTransactionManager(this.getTransactionManager());
                    factory.afterPropertiesSet();
                    return this.jobRepository = factory.getObject();
                } else {
                    return this.jobRepository;
                }
            }

            @Override
            public PlatformTransactionManager getTransactionManager() throws Exception {
                if (this.transactionManager == null) {
                    return this.transactionManager = new ResourcelessTransactionManager();
                } else {
                    return this.transactionManager;
                }
            }

            @Override
            public JobLauncher getJobLauncher() throws Exception {
                if (this.jobLauncher == null) {
                    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
                    jobLauncher.setJobRepository(this.getJobRepository());
                    jobLauncher.afterPropertiesSet();
                    return this.jobLauncher = jobLauncher;
                } else {
                    return this.jobLauncher;
                }
            }

            @Override
            public JobExplorer getJobExplorer() throws Exception {
                if (this.jobExplorer == null) {
                    JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
                    jobExplorerFactoryBean.setDataSource(batchInfraConfiguration().getDataSource());
                    jobExplorerFactoryBean.setTablePrefix(getTablePrefix());
                    jobExplorerFactoryBean.afterPropertiesSet();
                    return this.jobExplorer = jobExplorerFactoryBean.getObject();
                } else {
                    return this.jobExplorer;
                }
            }
        };
    }


}
