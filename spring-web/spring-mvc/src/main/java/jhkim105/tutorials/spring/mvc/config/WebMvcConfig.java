package jhkim105.tutorials.spring.mvc.config;

import jhkim105.tutorials.spring.mvc.interceptor.SampleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ServiceProperties serviceProperties;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
        configurer.setUseRegisteredSuffixPatternMatch(true);
//        configurer.setUseSuffixPatternMatch(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/upload/**")
            .addResourceLocations(String.format("file:%s/upload/", serviceProperties.getStoragePath()));

    }

    @Bean
    public SampleInterceptor sampleInterceptor() {
        return new SampleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sampleInterceptor());
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        List<Class<? extends Enum>> enums = Arrays.asList(StorageType.class, IdpType.class);
//        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
//            new CaseInsensitiveEnumConverter<>(enumClass)));
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
    }

}