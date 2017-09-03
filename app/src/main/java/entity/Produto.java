package entity;

import java.util.List;

/**
 * Created by guitr on 03/09/2017.
 */

public class Produto {

    private String _id;
    private String _rev;
    private String googleId;
    private String titulo;
    private String descricao;
    private String preco;
    private String precoProm;
    private String disponibilidade;
    private String urlImagem;

    public String get_id() { return _id; }

    public void set_id(String _id) { this._id = _id; }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getPrecoProm() {
        return precoProm;
    }

    public void setPrecoProm(String precoProm) {
        this.precoProm = precoProm;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) { this.disponibilidade = disponibilidade; }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String get_rev() { return _rev; }

    public void set_rev(String _rev) { this._rev = _rev; }

    public Produto(String _id, String _rev, String googleId, String titulo, String descricao, String preco, String precoProm, String disponibilidade, String urlImagem) {
        this._id = _id;
        this._rev = _rev;
        this.googleId = googleId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.precoProm = precoProm;
        this.disponibilidade = disponibilidade;
        this.urlImagem = urlImagem;
    }

   /* @Override
    public String toString() {
        return "id: " + _id + "\nTitulo: " + titulo + "\nDescrição: " + descricao.toString().replace("<p>","").replace("</p>","") + "\nPreço: " + preco + "\nPreço promocional: " + precoProm + "\nDisponibilidade: " + disponibilidade;
    }*/

    @Override
    public String toString() {
        if(precoProm==null){
            return "Titulo: " + titulo + "\nPreço: " + preco ;
        }else {
            return "Titulo: " + titulo + "\nPreço: " + precoProm ;
        }

    }
}