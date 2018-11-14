package jun.before.listener;

import jun.before.model.SharedBean;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JunJobListener extends JobExecutionListenerSupport {

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private SharedBean sharedBean;

    @Override
    public void beforeJob(JobExecution jobExecution) {

        System.out.println("Start Job");

        Map<String, JobParameter> jobParameters = jobExecution.getJobParameters().getParameters();

        String date = jobParameters.get("date").getValue().toString();
        Long count = (Long) jobParameters.get("count").getValue();

        sharedBean.setLastStartTime(date);
        sharedBean.setCount(count);

        System.out.println("Parameters: " + jobParameters.toString());


    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        System.out.println("End Job");

    }


}
