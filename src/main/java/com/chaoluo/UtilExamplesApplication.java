package com.chaoluo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class UtilExamplesApplication {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DemoClass {

        private List<Map<String, Object>> list = new ArrayList<>();
    }

    @Bean
    DemoClass demoClass() {
        return new DemoClass();
    }

    @Bean
    CommandLineRunner demo(DemoClass demoClass) {
        return args -> {
            Assert.notNull(demoClass.getList(), "the list can't be null");
            beans(demoClass);
            classUtils();
        };
    }

    private void classUtils() {
        Constructor<DemoClass> constructor = ClassUtils.getConstructorIfAvailable(DemoClass.class);
        log.info("demoClassConstructor: {}", constructor);

        try {
            DemoClass demoClass = constructor.newInstance();
            log.info("demoClass' instance: {}", demoClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void beans(DemoClass demoClass) {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(DemoClass.class);
        for (PropertyDescriptor pd : descriptors) {
            log.info("pd name: {}", pd.getName());
            log.info("pd read method name: {}", pd.getReadMethod().getName());
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(UtilExamplesApplication.class, args);
	}
}
