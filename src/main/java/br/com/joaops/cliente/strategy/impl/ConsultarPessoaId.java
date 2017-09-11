package br.com.joaops.cliente.strategy.impl;

import br.com.joaops.cliente.dto.PessoaDto;
import br.com.joaops.cliente.json.domain.PessoaJson;
import br.com.joaops.cliente.json.request.PessoaRequest;
import br.com.joaops.cliente.json.response.PessoaResponse;
import br.com.joaops.cliente.repository.PessoaDtoRepository;
import br.com.joaops.cliente.strategy.Command;
import br.com.joaops.cliente.util.CONSTANTES;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
@Component(CONSTANTES.COMANDOS.CONSULTAR_PESSOA_ID)
public class ConsultarPessoaId implements Command {
    
    @Autowired
    private Mapper mapper;
    
    @Autowired
    private StompSession stompSession;
    
    @Autowired
    private PessoaDtoRepository pessoaDtoRepository;
    
    @Override
    public void executar(Object ... objects) {
        try {
            PessoaRequest request = (PessoaRequest) objects[0];
            PessoaResponse response = new PessoaResponse();
            response.setId(request.getId());
            List<PessoaJson> pessoasJson = new ArrayList<>();
            PessoaJson aux = request.getPessoas().get(0);
            PessoaJson pessoaJson = new PessoaJson();
            PessoaDto pessoaDto = pessoaDtoRepository.findOne(aux.getId());
            mapper.map(pessoaDto, pessoaJson);
            pessoasJson.add(pessoaJson);
            response.setPessoas(pessoasJson);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);
            stompSession.send("/app" + CONSTANTES.ENDPOINTS.PESSOA, json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("ERRO " + e);
        }
    }
    
}