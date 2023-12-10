package com.example.mecanica.os_mecanica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ordemservico")
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String solucao;

    private String defeito;

    private LocalDate data_entrada;

    private LocalDate data_saida;

    private Double total;

    private Boolean status;

    private String tipo_servico;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToMany
    @JoinTable(
            name = "ordem_servico_peca",
            joinColumns = @JoinColumn(name = "ordem_servico_id"),
            inverseJoinColumns = @JoinColumn(name = "peca_id")
    )
    private Set<Peca> pecas = new HashSet<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public LocalDate getDataEntrada() {
        return data_entrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.data_entrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return data_saida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.data_saida = dataSaida;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTipoServico() {
        return tipo_servico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipo_servico = tipoServico;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Set<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(Set<Peca> pecas) {
        this.pecas = pecas;
    }
}