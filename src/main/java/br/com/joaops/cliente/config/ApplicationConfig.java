package br.com.joaops.cliente.config;

import br.com.joaops.cliente.Main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author João Paulo
 */
@EnableAsync
@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = Main.class)
public class ApplicationConfig {
    
}