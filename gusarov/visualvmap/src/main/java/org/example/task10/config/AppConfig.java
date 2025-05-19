package org.example.task10.config;

import lombok.AllArgsConstructor;
import org.example.task10.config.filter.MultireadFilter;
import org.example.task10.validation.ValidationInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private ValidationInterceptor validationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validationInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean<MultireadFilter> userInsertingMdcFilterRegistrationBean() {
        FilterRegistrationBean<MultireadFilter> registrationBean = new FilterRegistrationBean<>();
        MultireadFilter multireadFilter = new MultireadFilter();
        registrationBean.setFilter(multireadFilter);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }
}