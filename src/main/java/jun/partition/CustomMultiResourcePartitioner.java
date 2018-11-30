package jun.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMultiResourcePartitioner implements Partitioner {

    private Resource[] resources;

    private String keyName = "TEST_PARTITION";

    private String PARTITION_KEY = "PAR_";

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0, k = 1;

        for (Resource resource : resources) {
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: " + resource);
            context.putString(keyName, resource.getFilename());
            context.putString("opFileName", "output" + k++ + ".xml");
            map.put(PARTITION_KEY + i, context);
            i++;
        }

        return map;
    }


    public void setResources(Resource[] resources) {
        this.resources = resources;
    }
}
