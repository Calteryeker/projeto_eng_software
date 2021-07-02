package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Meta implements Serializable {

    private double valor;
    private String descricao;
    private LocalDate data_criacao;

    public Meta() {
        data_criacao = LocalDate.now();
    }

    public Meta(double valor, String descricao) {

        this.valor = valor;
        this.descricao = descricao;
        data_criacao = LocalDate.now();
    }

    public Meta(double valor, String descricao, LocalDate data) {

        this.valor = valor;
        this.descricao = descricao;
        data_criacao = data;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return Objects.equals(descricao, meta.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao);
    }

    @Override
    public String toString() {
        return "Meta{" +
                "Valor= " + valor +
                ", Descrição= '" + descricao + '\'' +
                ", Data de criação= " + data_criacao +
                '}';
    }
}
