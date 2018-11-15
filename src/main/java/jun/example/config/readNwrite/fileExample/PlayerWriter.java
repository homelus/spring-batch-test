package jun.example.config.readNwrite.fileExample;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PlayerWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("The End");
    }
}
