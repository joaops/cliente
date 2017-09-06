package br.com.joaops.cliente.config;

import br.com.joaops.cliente.mapper.converter.LocaleConverter;
import br.com.joaops.cliente.mapper.converter.TimeZoneConverter;
import br.com.joaops.cliente.mapper.converter.URLConverter;
import br.com.joaops.cliente.mapper.converter.UUIDConverter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.CustomConverter;
import org.dozer.spring.DozerBeanMapperFactoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author João Paulo
 */
@Configuration
public class MapperConfig {
    
    @Bean
    public List<CustomConverter> getCustomConverters() {
        List<CustomConverter> converters = new ArrayList<>();
        converters.add(new LocaleConverter());
        converters.add(new TimeZoneConverter());
        converters.add(new URLConverter());
        converters.add(new UUIDConverter());
        return converters;
    }
    
    @Bean
    public DozerBeanMapperFactoryBean mapper() {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        mapper.setCustomConverters(getCustomConverters());
        return mapper;
    }
    
}