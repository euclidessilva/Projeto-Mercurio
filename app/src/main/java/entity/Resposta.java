package entity;

import java.util.List;

/**
 * Created by guitr on 03/09/2017.
 */

public class Resposta {

    private List<Produto> produtos;
    private String comando;
    private List<String> carrinho;

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public List<String> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<String> carrinho) {
        this.carrinho = carrinho;
    }
}
