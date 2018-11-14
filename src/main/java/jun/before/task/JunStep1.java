package jun.before.task;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

        public class JunStep1 implements Tasklet {

            private int SEQ = 0;

            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                System.out.println("Step 1 Repeated Started. Seq: " + SEQ);

        if (SEQ == 10) {
            System.out.println("STEP 1 Finished");
            return RepeatStatus.FINISHED;
        }

        SEQ++;

        return RepeatStatus.CONTINUABLE;
    }
}
