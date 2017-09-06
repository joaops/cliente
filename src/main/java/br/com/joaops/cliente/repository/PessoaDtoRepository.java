package br.com.joaops.cliente.repository;

import br.com.joaops.cliente.dto.PessoaDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

/**
 *
 * @author João Paulo
 */
@Repository
public class PessoaDtoRepository {
    
    private static final Map<Long, PessoaDto> MAP = new HashMap<>();
    
    @PostConstruct
    private void initial() {
        MAP.put(1L, new PessoaDto(1L, "Fulano", new Date()));
        MAP.put(2L, new PessoaDto(2L, "Ciclano", new Date()));
        MAP.put(3L, new PessoaDto(3L, "Beltrano", new Date()));
    }
    
    public void save(PessoaDto pessoaDto) {
        MAP.put(pessoaDto.getId(), pessoaDto);
    }
    
    public void remove(Long id) {
        MAP.remove(id);
    }
    
    public PessoaDto findOne(Long id) {
        return MAP.get(id);
    }
    
    public List<PessoaDto> findAll() {
        return new ArrayList<>(MAP.values());
    }
    
}