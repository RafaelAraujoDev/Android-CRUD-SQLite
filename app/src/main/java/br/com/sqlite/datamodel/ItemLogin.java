package br.com.sqlite.datamodel;

import java.io.Serializable;

public class ItemLogin implements Serializable {

    private int id_usuario;
    private String email_usuario;
    private String senha_usuario;

    ItemLogin(int id, String emial, String senha){
        this.id_usuario = id;
        this.email_usuario = emial;
        this.senha_usuario = senha;
    }

    public ItemLogin(){
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }
}
