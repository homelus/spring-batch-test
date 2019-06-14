package jun.practice.partition;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static jun.practice.partition.SharedConfig.CONTROL_NO;

public class JunItemWriter implements ItemWriter<List<JunNo>> {

    @Autowired
    private SharedNos sharedNos;

    @Override
    public void write(List<? extends List<JunNo>> items) throws Exception {

        int con_no = CONTROL_NO++;

        System.out.println(con_no);

        if (con_no == 2) {
            System.out.println("ERROR 발생!!!");
            throw new RuntimeException("customizing");
        }

        int chunkSize = items.size();
        int individualSize = items.get(0).size();
        String firstId = items.get(0).get(0).getId();

        for (List<JunNo> item : items) {
            for (JunNo junNo : item) {
                if (sharedNos.getCheckNos().contains("JunNo_" + junNo.getNo())) {
                    sharedNos.getCheckNos().remove("JunNo_" + junNo.getNo());
                    sharedNos.getCount().incrementAndGet();
                }
            }
        }


        System.out.println("[" + Thread.currentThread().getName() + "] - WRITE - jun write item. chucnkSize: " + chunkSize + ", individualSize: " + individualSize + ", firstId: " + firstId + ", Check Size: " + sharedNos.getCheckNos().size());
    }
}
