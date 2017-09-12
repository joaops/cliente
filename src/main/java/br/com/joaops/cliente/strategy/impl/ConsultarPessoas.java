package br.com.joaops.cliente.strategy.impl;

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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
@Component(CONSTANTES.COMANDOS.CONSULTAR_PESSOAS)
public class ConsultarPessoas implements Strategy {
    
    @Autowired
    private StompSession stompSession;
    
    @Autowired
    private PessoaDtoRepository pessoaDtoRepository;
    
    @Override
    public void executar(Message message) {
        try {
            // Crio o Objeto de Resposta
            Message response = new Message();
            // Adiciono o Mesmo ID da Consulta
            response.setId(message.getId());
            // Consulto os Objetos da Resposta
            List<PessoaDto> pessoasDto = pessoaDtoRepository.findAll();
            // Converto eles para os Objetos de Transmissão
            List<PessoaJson> pessoasJson = new ArrayList<>();
            PessoaJson pessoaJson;
            for (PessoaDto pessoaDto : pessoasDto) {
                pessoaJson = new PessoaJson(pessoaDto.getId(), pessoaDto.getNome(), new SimpleDateFormat("dd/MM/yyyy").format(pessoaDto.getNascimento()));
                pessoasJson.add(pessoaJson);
            }
            // Adiciono os Objetos no Objeto de Resposta
            response.setParam("pessoas", pessoasJson);
            // Converto para Json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);
            // Transmito a Mensagem
            stompSession.send("/app" + CONSTANTES.ENDPOINTS.MESSAGE, json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("ERRO " + e);
            // Caso Ocorra uma Exeção, eu envio a mensagem de erro
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