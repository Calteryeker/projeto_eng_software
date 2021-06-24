package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Despesa implements Serializable {

    private String nome;
    private int idDespesa;
    private double valor;
    private LocalDate data_criacao;
    private Categoria categoria;

    public Despesa() {};

    public Despesa(String nome, double valor, LocalDate data_criacao, Categoria categoria) {

        this.nome = nome;
        this.valor = valor;
        this.data_criacao = data_criacao;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public int getOrdem() {
        return idDespesa;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrdem(int idMeta) {
        this.idDespesa = idMeta;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(String nomeCategoria) {
        categoria.setNome(nomeCategoria);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despesa despesa = (Despesa) o;
        return Double.compare(despesa.valor, valor) == 0 && nome.equals(despesa.nome) && Objects.equals(data_criacao, despesa.data_criacao) && categoria.equals(despesa.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, idDespesa, valor, data_criacao, categoria);
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "Nome='" + nome + '\'' +
                ", Id da Despesa=" + idDespesa +
                ", Valor=" + valor +
                ", Data de criação=" + data_criacao +
                ", Categoria=" + categoria +
                '}';
    }
}

