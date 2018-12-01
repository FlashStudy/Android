package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;

public class DisciplinaRestClient {
    private String BASE_URL = "http://192.168.0.94:8000/disciplina/";
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();


    public List<Disciplina> salvarLista(List<Disciplina> disciplinaList) {
        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("disciplinas", objectMapper.writeValueAsString(disciplinaList));

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            restTemplate.postForEntity(BASE_URL + "salvarLista", entity, null);

            return restTemplate.exchange(
                    BASE_URL + "findallbyuser/" + disciplinaList.get(0).getUsuario().getCodigo(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Disciplina>>() {
                    }
            ).getBody();
        } catch (Exception e) {
            Log.i("ERRO SERV DISCIPLINA", e.getMessage());
            return null;
        }
    }

}
