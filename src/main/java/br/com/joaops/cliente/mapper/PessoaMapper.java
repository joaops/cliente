package br.com.joaops.cliente.mapper;

import br.com.joaops.cliente.dto.PessoaDto;
import br.com.joaops.cliente.json.domain.PessoaJson;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo
 */
public class PessoaMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(PessoaDto.class, PessoaJson.class);
    }
    
}