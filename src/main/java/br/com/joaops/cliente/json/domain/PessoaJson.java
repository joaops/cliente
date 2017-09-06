/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.json.domain;

import java.util.Date;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class PessoaJson {
    
    private String nome;
    private Date nascimento;
    
    public PessoaJson() {
        this("", new Date());
    }
    
    public PessoaJson(String nome, Date nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Date getNascimento() {
        return nascimento;
    }
    
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
}