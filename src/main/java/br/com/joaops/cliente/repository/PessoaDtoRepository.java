package br.com.joaops.cliente.repository;

import br.com.joaops.cliente.dto.PessoaDto;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    private static Long ID_GENERATOR = 0L; 
    private static final Map<Long, PessoaDto> MAP = new HashMap<>();
    
    @PostConstruct
    private void initial() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1994); 
        c.set(Calendar.MONTH, Calendar.AUGUST); 
        c.set(Calendar.DAY_OF_MONTH, 10);
        save(new PessoaDto(0L, "João Paulo", c.getTime()));
        c.set(Calendar.YEAR, 1995); 
        c.set(Calendar.MONTH, Calendar.SEPTEMBER); 
        c.set(Calendar.DAY_OF_MONTH, 11);
        save(new PessoaDto(0L, "Ciclano", c.getTime()));
        save(new PessoaDto(0L, "Beltrano", new Date()));
    }
    
    public PessoaDto save(PessoaDto pessoaDto) {
        PessoaDto dto;
        if (pessoaDto.getId() > 0) {
            MAP.put(pessoaDto.getId(), pessoaDto);
            dto = MAP.get(pessoaDto.getId());
        } else {
            pessoaDto.setId(++ID_GENERATOR);
            MAP.put(pessoaDto.getId(), pessoaDto);
            dto = MAP.get(pessoaDto.getId());
        }
        return dto;
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