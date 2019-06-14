package jun.practice.partition;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepCompleteListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step Before! " + Thread.currentThread().getName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Step After! : " + stepExecution.getStepName() + " " + stepExecution.toString());
        return stepExecution.getExitStatus();
    }
}
