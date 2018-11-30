package jun.partition.example;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class MyPartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        System.out.println(Thread.currentThread().getName() + " 호출");

        Map partitionMap = new HashMap();
        int startingIndex = 0;
        int endingIndex = 5;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext ctxMap = new ExecutionContext();
            ctxMap.putInt("startingIndex", startingIndex);
            ctxMap.putInt("endingIndex", endingIndex);

            startingIndex = endingIndex + 1;
            endingIndex += 5;

            partitionMap.put("Thread:-" + i, ctxMap);
        }

        return partitionMap;
    }
}
