package jun.springExample.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.concurrent.TimeUnit;

import static jun.springExample.tasklet.TaskletConfig.SLEEP_TIME;
import static jun.springExample.tasklet.TaskletConfig.TEST_COUNT;

public class GoodByeMinTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        for (int i = 0; i < TEST_COUNT; i++) {
            TimeUnit.SECONDS.sleep(SLEEP_TIME);
            System.out.println(Thread.currentThread().getName() + ": Good Bye Min ~!");
        }

        return RepeatStatus.FINISHED;
    }
}
