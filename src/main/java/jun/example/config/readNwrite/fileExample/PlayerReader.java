package jun.example.config.readNwrite.fileExample;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;

public class PlayerReader extends FlatFileItemReader<Player> {

    @Override
    public Player read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return doRead();
    }
}
