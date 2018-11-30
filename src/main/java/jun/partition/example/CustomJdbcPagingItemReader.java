package jun.partition.example;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcPagingItemReader;

public class CustomJdbcPagingItemReader extends JdbcPagingItemReader {

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException {
        System.out.println(Thread.currentThread().getName() + " Read!");
        return super.read();
    }
}
