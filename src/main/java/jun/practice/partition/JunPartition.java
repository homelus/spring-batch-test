package jun.practice.partition;

import com.google.common.collect.Lists;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class JunPartition implements Partitioner {

    @Autowired
    private SharedNos sharedNos;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        List<Integer> allNos = sharedNos.getNos();

        System.out.println("[" + Thread.currentThread().getName() + "] - PARTITION - Partitioning Start!! gridSize: " + gridSize + ", allNos Size: " + allNos.size());
        List<List<Integer>> partitionNos = Lists.partition(allNos, allNos.size() / Math.min(allNos.size(), gridSize));

        List<ExecutionContext> executionContexts = partitionNos.stream().map(nos -> {
           ExecutionContext executionContext = new ExecutionContext();
           executionContext.putInt("startNo", nos.get(0));
           executionContext.putInt("endNo", nos.get(nos.size() - 1));
           return executionContext;
        }).collect(toList());

        Map<String, ExecutionContext> result = new HashMap<>();
        int i = 0;
        for (ExecutionContext executionContext : executionContexts) {
            result.put("Partition" + (++i), executionContext);
        }
        return result;
    }
}
