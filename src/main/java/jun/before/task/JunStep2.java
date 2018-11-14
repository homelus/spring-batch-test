package jun.before.task;

import jun.before.model.SharedBean;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class JunStep2 implements Tasklet {

    @Autowired
    SharedBean sharedBean;

    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        System.out.println("Step 2 Started");

        System.out.println("parameter count: " + sharedBean.getCount());

        System.out.println("lastStartTime: " + sharedBean.getLastStartTime());

        return RepeatStatus.FINISHED;
    }

}
