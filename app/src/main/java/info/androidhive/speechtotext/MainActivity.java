package info.androidhive.speechtotext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import entity.Produto;
import entity.Resposta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import util.ProdutosApi;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;
    HashMap<String, String> vozCapturada = new HashMap<String, String>();
    HashMap<String, String> vozApi = new HashMap<String, String>();
    List<String> vozes = new ArrayList<String>();
    List<String> carrinho = new ArrayList<String>();
    Resposta resposta;

    private TextView txtSpeechInput;
    ListView lista;
    ListView tprodutos;
    String text = "";
    TextView tcodigo;
    ImageView ticon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        // hide the action bar
        getActionBar().hide();
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        tts = new TextToSpeech(this, this);
        lista = (ListView) findViewById(R.id.lista);
        tprodutos = (ListView) findViewById(R.id.produtos);
        tcodigo = (TextView) findViewById(R.id.codigo);

    }

    public void adicionaVozCapturada(String voz) {
        vozCapturada.put("Usuário", voz);
    }

    public void adicionavozApi(String voz) {
        vozApi.put("Natura", voz);
    }

    public void adicionaVozes(String voz) {
        vozes.add(voz);

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String teste = result.get(0);
                    verificaVoz(teste);

                    List<String> cursos = vozes;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cursos);
                    lista.setAdapter(adapter);
                }
                break;
            }

        }
    }

    public void verificaVoz(String teste) {
        if (teste.equals("JP")) {
            text = null;
            text = "Produto adicionado no carrinho";
            speakOut();
            adicionaVozes(text);
            String codigo = "";
            adicionarCarrinho(codigo);
        } else if (teste.equals("exibir")) {
            mostraproduto();
        } else {
            text = null;
            text = teste;
            speakOut();
            adicionaVozCapturada(text);
            adicionaVozes("Usuário: " + text);
        }
    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void adicionarCarrinho(String codigo) {
        carrinho.add(codigo);
        //chamar api confirmando que foi adicionado no carrinho, a API deve perguntar se deseja mais produtos
        speakOut();
    }

    public Resposta retornaResposta() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo9256607.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProdutosApi api = retrofit.create(ProdutosApi.class);

        api.getAllProdutos().enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                Resposta resposta = response.body();
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Log.i("Erro", t.getMessage());
            }
        });

        return resposta;
    }

    public void mostraproduto() {
        Produto produto = new Produto("2b0aac7cb35ef488e4ca50f24faf3555", "1-069a5338db970f72d3b21b204a3c0910", "63371", "Desodorante Colônia Paz e Humor Masculino - 75ml",
                "<p>Equilibra a energia das notas cítricas com a alegria das notas frutais, com o conforto da vanila, e a força das madeiras.</p>\n<p>Conteúdo: 75 ml.</p>\n<p>Benefícios: Perfumação.</p>",
                "99.9BRL", null, "in stock", "images.rede.natura.net/image/sku/principal_641x449/63371_1.jpg");

        Produto produto2 = new Produto("2b0aac7cb35ef488e4ca50f24faf3555", "1-069a5338db970f72d3b21b204a3c0910", "63371", "Desodorante Colônia Paz e Humor Masculino - 75ml",
                "<p>Equilibra a energia das notas cítricas com a alegria das notas frutais, com o conforto da vanila, e a força das madeiras.</p>\n<p>Conteúdo: 75 ml.</p>\n<p>Benefícios: Perfumação.</p>",
                "99.9BRL", null, "in stock", "images.rede.natura.net/image/sku/principal_641x449/63371_1.jpg");

        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(produto);
        produtos.add(produto2);

        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        tprodutos.setAdapter(adapter);

    }


}
