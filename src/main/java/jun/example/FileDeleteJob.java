package jun.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class FileDeleteJob {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileDeleteJob() {
        return this.jobBuilderFactory.get("fileDeleteJob")
                .start(deleteFilesInDir())
                .build();
    }

    @Bean
    public Step deleteFilesInDir() {
        return this.stepBuilderFactory.get("deleteFileInDir")
                .tasklet(fileDeletingTasklet())
                .build();
    }

    @Bean
    public Tasklet fileDeletingTasklet() {
        FileDeletingTasklet tasklet = new FileDeletingTasklet();
        tasklet.setDirectoryResource(new FileSystemResource("target/test-outputs/test-dir"));
        return tasklet;
    }


}

