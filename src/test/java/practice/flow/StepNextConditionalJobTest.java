package practice.flow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.config.AbstractTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StepNextConditionalJobTest.TestConfiguration.class)
public class StepNextConditionalJobTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job stepNextConditionalJob;

    @Test
    public void executeSimpleJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("version", 2L).toJobParameters();

        jobLauncher.run(stepNextConditionalJob, jobParameters);
    }

    @ComponentScan(basePackages = "jun.practice.flow")
    @Configuration
    static class TestConfiguration extends AbstractTestConfiguration {};

}
