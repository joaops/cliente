/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.mapper.converter;

import java.net.MalformedURLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class URLConverter implements CustomConverter {
    
    private static final Logger LOGGER = LogManager.getLogger(URLConverter.class);
    
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        java.net.URL source;
        java.net.URL destination = null;        
        if (sourceClass.isInstance(java.net.URL.class)) {
            source = (java.net.URL) sourceFieldValue;
            if (source == null) {
                return null;
            }
            if (destinationClass.isInstance(java.net.URL.class)) {
                if (existingDestinationFieldValue == null) {
                    try {
                        destination = new java.net.URL(source.toString());
                    } catch (MalformedURLException ex) {
                        LOGGER.error(ex);
                        throw new MappingException("URLConverter MalformedURLException: " + ex.getLocalizedMessage());
                    }
                } else {
                    throw new MappingException("URLConverter cannot use existing destination field value");
                }
            }
        }
        return destination;
    }
    
}