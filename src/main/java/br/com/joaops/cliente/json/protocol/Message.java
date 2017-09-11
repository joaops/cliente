package br.com.joaops.cliente.json.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author João Paulo
 */
public class Message {
    
    private Long id;
    private String operacao;
    private Status status;
    private Map<String, Object> params;
    
    public Message() {
        this(0L, "", Status.OK, new HashMap<>());
    }
    
    public Message(Long id, String operacao, Status status, Map<String, Object> params) {
        this.id = id;
        this.operacao = operacao;
        this.status = status;
        this.params = params;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOperacao() {
        return operacao;
    }
    
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public boolean containsParam(String key) {
        return params.containsKey(key);
    }
    
    public Object getParam(String key) {
        return params.get(key);
    }
    
    public void setParam(String key, Object value) {
        params.put(key, value);
    }
    
    public Map<String, Object> getParams() {
        return params;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
}