/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.strategy;

/**
 *
 * @author João Paulo
 */
public interface Command {
    
    public void executar(Object ... objects);
    
}