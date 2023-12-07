package com.example.mecanica.os_mecanica.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "peca")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String quantidade;
    private String valor;

   /* @ManyToMany(mappedBy = "peca")
    private Set<OrdemServico> ordensServico = new HashSet<>();*/

    public Peca() {
    }

    // Getters e Setters

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

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

   /* public Set<OrdemServico> getOrdensServico() {
        return ordensServico;
    }*/

   /* public void setOrdensServico(Set<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }*/
}