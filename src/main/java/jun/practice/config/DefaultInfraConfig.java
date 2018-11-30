package jun.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:applicationPropoerty.properties")
@ComponentScan(basePackages = "jun.practice.config.bean")
@ImportResource("classpath:config/applicationContext-*.xml")
public class DefaultInfraConfig {

    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
