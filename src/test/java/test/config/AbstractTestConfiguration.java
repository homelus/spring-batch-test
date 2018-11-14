package test.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

public class AbstractTestConfiguration {

    @Bean
    @Primary
    public BatchConfigurer batchConfigurer() {
        final DefaultBatchConfigurer dbc = new DefaultBatchConfigurer() {};

        try {
            dbc.initialize();
        } catch (Exception ignore) {};

        return new BatchConfigurer() {
            @Override
            public JobRepository getJobRepository() throws Exception {
                return dbc.getJobRepository();
            }

            @Override
            public PlatformTransactionManager getTransactionManager() throws Exception {
                return dbc.getTransactionManager();
            }

            @Override
            public JobLauncher getJobLauncher() throws Exception {
                return dbc.getJobLauncher();
            }

            @Override
            public JobExplorer getJobExplorer() throws Exception {
                return dbc.getJobExplorer();
            }
        };

    }

}
