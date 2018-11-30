package jun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.config.AbstractTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JunAfterBatchTest.TestConfiguration.class)
public class JunAfterBatchTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job junBatchJob;

    @Test
    public void executeBatch() throws Exception {
        JobParametersBuilder builder = new JobParametersBuilder();
        jobLauncher.run(junBatchJob, builder.toJobParameters());
    }


    @ComponentScan(basePackages = {
            "jun.springExample.config"
    })
    @Configuration
    static class TestConfiguration extends AbstractTestConfiguration {}

}
