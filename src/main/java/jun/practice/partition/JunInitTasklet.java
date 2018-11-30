package jun.practice.partition;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class JunInitTasklet implements Tasklet {

    @Autowired
    private SharedNos sharedNos;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<Integer> junAllNos = IntStream.iterate(1, i -> i + 1).limit(10000).boxed().collect(toList());

        sharedNos.getNos().addAll(junAllNos);

        for (Integer junAllNo : junAllNos) {
            sharedNos.getCheckNos().add("JunNo_" + junAllNo);
        }

        System.out.println("[" + Thread.currentThread().getName() + "] - INITIALIZE - JunInitTasklet start! - add All Nos");

        return RepeatStatus.FINISHED;
    }
}
