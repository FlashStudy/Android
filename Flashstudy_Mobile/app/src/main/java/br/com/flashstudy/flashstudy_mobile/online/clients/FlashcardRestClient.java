package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.flashstudy.flashstudy_mobile.online.model.Flashcard;

public class FlashcardRestClient {

    private String BASE_URL = "http://192.168.0.40:8000/flashcard/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Flashcard> findAll(Long codigo) {
        try {
            return restTemplate.exchange(
                    BASE_URL + "findallbyuser/" + codigo,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Flashcard>>() {
                    }
            ).getBody();
        } catch (Exception e) {
            Log.i("ERRO CONSULT SERV", e.getMessage());
            return null;
        }
    }

    public boolean salvar(Flashcard flashcard) {
        try {
            Map<String, String> values = new HashMap<>();
            values.put("titulo", flashcard.getTitulo());
            values.put("pergunta", flashcard.getPergunta());
            values.put("resposta", flashcard.getResposta());
            values.put("usuario", flashcard.getUsuario().toString());

            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("titulo", flashcard.getTitulo());
            jsonObject.put("pergunta", flashcard.getPergunta());
            jsonObject.put("resposta", flashcard.getResposta());
            jsonObject.put("usuario", flashcard.getUsuario());


            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            restTemplate.postForEntity(BASE_URL + "salvar", entity, null);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
