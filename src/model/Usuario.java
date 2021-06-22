package model;

public class Usuario {

    private String nome;
    private String login;
    private String senha;

    public Usuario(String nome, String login, String senha) {

        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getLogin() {
        return login;
    }

}
