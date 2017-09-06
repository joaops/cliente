/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.json.response;

import br.com.joaops.cliente.json.domain.PessoaJson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class PessoaResponse {
    
    private String id;
    private List<String> status;
    private List<PessoaJson> pessoas;

    public PessoaResponse() {
        this("", new ArrayList<>(), new ArrayList<>());
    }

    public PessoaResponse(String id, List<String> status, List<PessoaJson> pessoas) {
        this.id = id;
        this.status = status;
        this.pessoas = pessoas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<PessoaJson> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaJson> pessoas) {
        this.pessoas = pessoas;
    }
    
}