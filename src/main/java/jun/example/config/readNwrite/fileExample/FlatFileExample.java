package jun.example.config.readNwrite.fileExample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class FlatFileExample {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileJob() {
        return jobBuilderFactory.get("flatFileExecutor")
                .start(fileReadStep()).build();
    }

    @Bean
    public Step fileReadStep() {
        return stepBuilderFactory.get("playerFileReader")
                .<Player, String>chunk(2)
                .reader(playerReader())
                .processor(playerProcessor())
                .writer(playerWriter()).build();
    }

    @Bean
    public ItemReader playerReader() {
        FlatFileItemReader flatFileItemReader = new PlayerReader();
        flatFileItemReader.setResource(new FileSystemResource("C:\\Eclipse\\JUN_PROJECTS\\SpringBatchTestCase\\src\\main\\resources\\player.txt"));
        DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"ID","lastName","firstName","position","birthYear","debutYear"});
        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(new PlayerFieldSetMapper());
        lineMapper.setFieldSetMapper(fieldSetMapper());
        flatFileItemReader.setLineMapper(lineMapper);
        flatFileItemReader.open(new ExecutionContext());
        return flatFileItemReader;
    }

    @Bean
    public ItemProcessor<Player, String> playerProcessor() {
        return new PlayerProcessor();
    }

    @Bean
    public ItemWriter<String> playerWriter() {
        return new PlayerWriter();
    }

    @Bean
    public FieldSetMapper fieldSetMapper() {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setPrototypeBeanName("player");
        return fieldSetMapper;
    }

    @Bean
    @Scope("prototype")
    public Player player() {
        return new Player();
    }

}
