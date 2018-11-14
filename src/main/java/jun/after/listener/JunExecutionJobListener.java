package jun.after.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JunExecutionJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Jun Job Listener Before");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Jun Job Listener After");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Job Complete");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            System.out.println("Job Failed");
        }

    }
}
