/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Wolkor
 */
public class Mensagem implements Serializable {
    
    private String operacao;
    private Status status; 
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public Mensagem(String operacao){
        this.operacao = operacao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
    
    @Override
    public String toString(){     
        return "< operacao: "+ operacao +" | Status: " + status + " >";
    }
    
}
