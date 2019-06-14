package practice.partition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.config.AbstractTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PartitionConfig.TestConfiguration.class)
public class PartitionConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job junPartitioningJob;

    @Test
    public void executeBatch() throws Exception {
        jobLauncher.run(junPartitioningJob, new JobParameters());
    }

    @ComponentScan(basePackages = {
            "jun.practice.partition"
    })
    @Configuration
    static class TestConfiguration extends AbstractTestConfiguration {}

}
