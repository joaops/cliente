/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.json.domain;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class PessoaJson {
    
    private Long id;
    private String nome;
    private String nascimento;
    
    public PessoaJson() {
        this(0L, "", "");
    }
    
    public PessoaJson(Long id, String nome, String nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNascimento() {
        return nascimento;
    }
    
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    
}