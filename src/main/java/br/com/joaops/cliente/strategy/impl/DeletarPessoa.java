package br.com.joaops.cliente.strategy.impl;

import br.com.joaops.cliente.json.domain.PessoaJson;
import br.com.joaops.cliente.json.request.PessoaRequest;
import br.com.joaops.cliente.json.response.PessoaResponse;
import br.com.joaops.cliente.repository.PessoaDtoRepository;
import br.com.joaops.cliente.strategy.Command;
import br.com.joaops.cliente.util.CONSTANTES;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
@Component(CONSTANTES.COMANDOS.DELETAR_PESSOA)
public class DeletarPessoa implements Command {
    
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
            PessoaJson aux = request.getPessoas().get(0);
            pessoaDtoRepository.remove(aux.getId());
        } catch (Exception e) {
            System.err.println("ERRO " + e);
        }
    }
    
}