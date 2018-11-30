package jun.partition.example;

import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.List;

public class CustomFlatFileItemWriter extends FlatFileItemWriter {

    @Override
    public void write(List items) throws Exception {
        System.out.println(Thread.currentThread().getName() + ": " + items.size() + " Write!");
        super.write(items);
    }
}
