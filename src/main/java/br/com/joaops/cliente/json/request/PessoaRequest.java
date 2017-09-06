/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.json.request;

import br.com.joaops.cliente.json.domain.PessoaJson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class PessoaRequest {
    
    private String id;
    private String operacao;
    private List<PessoaJson> pessoas;

    public PessoaRequest() {
        this("", "", new ArrayList<>());
    }

    public PessoaRequest(String id, String operacao, List<PessoaJson> pessoas) {
        this.id = id;
        this.operacao = operacao;
        this.pessoas = pessoas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    
    public List<PessoaJson> getPessoas() {
        return pessoas;
    }
    
    public void setPessoas(List<PessoaJson> pessoas) {
        this.pessoas = pessoas;
    }
    
}