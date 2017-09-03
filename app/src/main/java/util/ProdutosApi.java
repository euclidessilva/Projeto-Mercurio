package util;

import java.util.List;

import entity.Produto;
import entity.Resposta;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by guitr on 03/09/2017.
 */

public interface ProdutosApi {
    @GET("produtos")
    Call<Resposta>getAllProdutos();
}
