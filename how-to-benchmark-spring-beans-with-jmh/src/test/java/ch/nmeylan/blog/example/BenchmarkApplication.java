package ch.nmeylan.blog.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// Here I suggest to use @ComponentScan and exclude any bean which are not used to run the benchmark,
// in order to make startup of benchmark faster
@ComponentScan(basePackages = "ch.nmeylan.blog.example"
//        excludeFilters = {
//          @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BeanToExclude.class),
//          @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigurationToExclude.class)
//        }
)
public class BenchmarkApplication {
}
