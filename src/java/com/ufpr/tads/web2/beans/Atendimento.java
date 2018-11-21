    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.beans;

import java.sql.Timestamp;

/**
 *
 * @author sa
 */
public class Atendimento {
    private int id_atendimento;
    private Timestamp dataHora;
    private String descricao;
    private String solucao;
    private int id_produto;
    private int id_tipo_atendimento;
    private int id_funcionario; //não pede nas especificacoes NÃO coloquei no banco
    private int id_cliente;
    private String situacao;
    private int id_tipoAt;

    public int getId_atendimento() {
        return id_atendimento;
    }

    public void setId_atendimento(int id_atendimento) {
        this.id_atendimento = id_atendimento;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getId_tipo_atendimento() {
        return id_tipo_atendimento;
    }

    public void setId_tipo_atendimento(int id_tipo_atendimento) {
        this.id_tipo_atendimento = id_tipo_atendimento;
    }
    
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public int getId_tipoAt() {
        return id_tipoAt;
    }

    public void setId_tipoAt(int id_tipoAt) {
        this.id_tipoAt = id_tipoAt;
    }
}
