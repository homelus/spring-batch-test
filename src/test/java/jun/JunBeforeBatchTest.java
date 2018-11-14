package jun;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = "classpath:jobs/jun-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JunBeforeBatchTest {

    private SimpleDateFormat YMDHMS;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private Job job;

    @Before
    public void setUp() {
        YMDHMS = new SimpleDateFormat("YYYYMMddHHmmss");
    }

    @Test
    public void junBatchTest() throws Exception {
        JobParametersBuilder parameter = new JobParametersBuilder();
        parameter.addString("date", YMDHMS.format(Calendar.getInstance().getTime()));
        parameter.addLong("count", 7L);

        JobExecution jobExecution = jobLauncher.run(job, parameter.toJobParameters());
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    public void jobExplorerTest() throws NoSuchJobException {

        String jobName = jobExplorer.getJobNames().get(0);
        long lastJobExcutionId = jobExplorer.getJobInstanceCount(jobName);
        JobExecution jobExecution = jobExplorer.getJobExecution(lastJobExcutionId);
        ExitStatus status = jobExecution.getExitStatus();
        ZonedDateTime lastStartTime = ZonedDateTime.ofInstant(jobExecution.getStartTime().toInstant(), ZoneId.of("Asia/Seoul"));

        assertEquals(jobName, "jun-index");
        assertEquals(lastJobExcutionId, 2);
        assertEquals(status, ExitStatus.COMPLETED);

        System.out.println(lastStartTime);

    }

}
