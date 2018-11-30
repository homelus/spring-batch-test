package jun.practice.partition;

import org.springframework.batch.item.ItemProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class JunItemProcessor implements ItemProcessor<List<String>, List<JunNo>> {
    @Override
    public List<JunNo> process(List<String> items) throws Exception {

        System.out.println("[" + Thread.currentThread().getName() + "] - PROCESSOR - jun process first : " + items.get(0));

        List<JunNo> junNos = items.stream().map(id -> {
            JunNo junNo = new JunNo();
            junNo.setId(id);
            junNo.setNo(id.split("_")[1]);
            return junNo;
        }).collect(Collectors.toList());


        return junNos;
    }
}
