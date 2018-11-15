package jun.example.config.readNwrite.processExample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class ProcessorExample {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ioSampleJob() {
        return this.jobBuilderFactory.get("ioSampleJob")
                .start(step2()).build();

    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<Foo, Bar>chunk(2)
                .reader(fooReader())
                .processor(fooProcessor())
                .writer(barWriter())
                .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
                .<Foo, Foobar>chunk(2)
                .reader(fooReader())
                .processor(compositeItemProcessor())
                .writer(foobarWriter())
                .build();
    }

    @Bean
    public FooReader fooReader() {
        return new FooReader();
    }

    @Bean
    public FooProcessor fooProcessor() {
        return new FooProcessor();
    }

    @Bean
    public BarWriter barWriter() {
        return new BarWriter();
    }

    @Bean
    public FoobarWriter foobarWriter() {
        return new FoobarWriter();
    }

    @Bean
    public CompositeItemProcessor compositeItemProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>();
        delegates.add(new FooProcessor());
        delegates.add(new BarProcessor());

        CompositeItemProcessor processor = new CompositeItemProcessor();
        processor.setDelegates(delegates);

        return processor;
    }
}

class Foo {

    public Foo(int no) {
        this.no = no;
    }

    private int no;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}

class Bar {
    int no;
    public Bar(Foo foo) {
        no = foo.getNo();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "no=" + no +
                '}';
    }
}

class Foobar {

    private int no;
    public Foobar(Bar bar) {
        no = bar.getNo();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Foobar{" +
                "no=" + no +
                '}';
    }
}

class FooReader implements ItemReader<Foo> {

    private int count = 100;

    @Override
    public Foo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        while (count > 0) {
            return new Foo(count--);
        }

        return null;
    }
}

class FooProcessor implements ItemProcessor<Foo, Bar> {
    @Override
    public Bar process(Foo foo) throws Exception {
        return new Bar(foo);
    }
}

class BarProcessor implements ItemProcessor<Bar, Foobar> {
    @Override
    public Foobar process(Bar bar) throws Exception {
        bar.setNo(bar.getNo() + 1);
        return new Foobar(bar);
    }
}

class BarWriter implements ItemWriter<Bar> {
    @Override
    public void write(List<? extends Bar> list) throws Exception {
        for (Bar bar : list) {
            System.out.println(bar.toString());
        }
        System.out.println("BarWriter End");
    }
}

class FoobarWriter implements ItemWriter<Foobar> {
    @Override
    public void write(List<? extends Foobar> list) throws Exception {
        for (Foobar foobar : list) {
            System.out.println(foobar.toString());
        }
        System.out.println("FoobarWriter End");
    }
}

