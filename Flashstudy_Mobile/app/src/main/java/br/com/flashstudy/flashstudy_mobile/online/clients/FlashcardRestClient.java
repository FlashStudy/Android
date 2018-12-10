package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Flashcard;

public class FlashcardRestClient {

    private String BASE_URL = "http://192.168.0.11:7000/flashcard/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Flashcard> findAll(Long codigo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    BASE_URL + "findallbyuser/" + codigo,
                    HttpMethod.GET,
                    entity,
                    List.class
            ).getBody();
        } catch (Exception e) {
            Log.e("ERRO CONSULTA SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Flashcard salvar(Flashcard flashcard) {
        try {
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("titulo", flashcard.getTitulo());
            jsonObject.put("pergunta", flashcard.getPergunta());
            jsonObject.put("resposta", flashcard.getResposta());
            jsonObject.put("usuario", flashcard.getUsuario());


            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
            return restTemplate.postForEntity(BASE_URL + "salvar", entity, Flashcard.class).getBody();
        } catch (Exception e) {
            Log.e("ERRO CONSULTA SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletar(long codigo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.delete(BASE_URL + "delete/" + codigo, entity);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SERV DISCIPLINA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarLista(List<Flashcard> flashcards) {
        try {
            Log.e("FLASHCARDS", flashcards.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();

            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(flashcards), headers);

            restTemplate.delete(BASE_URL + "deleteLista", entity);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SERV DISCIPLINA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
