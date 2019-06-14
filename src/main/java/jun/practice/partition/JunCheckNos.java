package jun.practice.partition;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JunCheckNos implements Tasklet {

    @Autowired
    private SharedNos sharedNos;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<String> checks = sharedNos.getCheckNos();

        if (checks.size() > 0) {
            throw new RuntimeException("동기화 실패!! Checked Size: " + checks.size());
        }

        System.out.println("최종 값 데이터 : " + sharedNos.getCount().get());

        return RepeatStatus.FINISHED;
    }
}
