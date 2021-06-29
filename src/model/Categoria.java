package model;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {

    private String nome;
    private int idCategoria;
    private Usuario usuario;

    public Categoria(String nome) {
        this.nome = nome;
        usuario = new Usuario("Admin", "admin", "adminpass");
    }

    public Categoria(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria == categoria.idCategoria && Objects.equals(nome, categoria.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, idCategoria);
    }
}
