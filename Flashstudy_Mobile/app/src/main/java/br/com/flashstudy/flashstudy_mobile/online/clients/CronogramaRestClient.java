package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;

public class CronogramaRestClient {

    private String BASE_URL = "http://192.168.0.39:8000/cronograma/";
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();


    public Cronograma salvar(Cronograma cronograma) {
        try {
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("inicio", cronograma.getInicio());
            jsonObject.put("fim", cronograma.getFim());
            jsonObject.put("usuario", objectMapper.writeValueAsString(cronograma.getUsuario()));
            jsonObject.put("disciplinas", objectMapper.writeValueAsString(cronograma.getDisciplinas()));

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<Cronograma> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, Cronograma.class);

            cronograma = responseEntity.getBody();

            return cronograma;
        } catch (Exception e) {
            Log.i("ERRO SERV CRONO", e.getMessage());
            return null;
        }
    }
}
