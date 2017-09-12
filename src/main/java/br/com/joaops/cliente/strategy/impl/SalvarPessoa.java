package br.com.joaops.cliente.strategy.impl;

import br.com.joaops.cliente.Main;
import br.com.joaops.cliente.controller.PessoaLayoutController;
import br.com.joaops.cliente.dto.PessoaDto;
import br.com.joaops.cliente.json.domain.PessoaJson;
import br.com.joaops.cliente.json.protocol.Message;
import br.com.joaops.cliente.json.protocol.Status;
import br.com.joaops.cliente.repository.PessoaDtoRepository;
import br.com.joaops.cliente.strategy.Strategy;
import br.com.joaops.cliente.util.CONSTANTES;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import org.dozer.Mapper;
import org.springframework.beans.factory.BeanFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
@Component(CONSTANTES.COMANDOS.SALVAR_PESSOA)
public class SalvarPessoa implements Strategy {
    
    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private StompSession stompSession;
    
    @Autowired
    private PessoaDtoRepository pessoaDtoRepository;
    
    @Override
    public void executar(Message message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Message response = new Message();
            response.setId(message.getId());
            PessoaJson aux = objectMapper.convertValue(message.getParam("pessoa"), PessoaJson.class);
            PessoaDto dto = new PessoaDto(aux.getId(), aux.getNome(), new SimpleDateFormat("dd/MM/yyyy").parse(aux.getNascimento()));
            PessoaDto salvo = pessoaDtoRepository.save(dto);
            PessoaJson pessoaJson = new PessoaJson(salvo.getId(), salvo.getNome(), new SimpleDateFormat("dd/MM/yyyy").format(salvo.getNascimento()));
            response.setParam("pessoa", pessoaJson);
            String json = objectMapper.writeValueAsString(response);
            stompSession.send("/app" + CONSTANTES.ENDPOINTS.MESSAGE, json.getBytes(StandardCharsets.UTF_8));
            // Atualizar a Tela
            PessoaLayoutController controller = beanFactory.getBean(PessoaLayoutController.class);
            controller.carregarTableViewPessoa();
        } catch (Exception e) {
            System.err.println("ERRO " + e);
            sendMessageError(message.getId(), e.getMessage());
        }
    }
    
    private void sendMessageError(Long id, String error) {
        try {
            // Monto a Mensagem de ERRO
            Message response = new Message();
            response.setId(id);
            response.setStatus(Status.ERRO);
            response.setParam("erro", error);
            // Converto para Json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);
            // Transmito a Mensagem
            stompSession.send("/app" + CONSTANTES.ENDPOINTS.MESSAGE, json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
        }
    }
    
}