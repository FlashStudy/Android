package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.annotation.SuppressLint;
import android.util.Log;

import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;

public class CronogramaRestClient {

    private String BASE_URL = "http://192.168.0.11:7000/cronograma/";
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @SuppressLint("NewApi")
    public Cronograma salvar(Cronograma cronograma) {
        try {
            Log.e("TESTE", "TESTE");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();

            headers.setContentEncoding(new ContentCodingType("UTF-8"));

            headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("codigo", cronograma.getCodigo());
            jsonObject.put("inicio", cronograma.getInicio());
            jsonObject.put("fim", cronograma.getFim());
            jsonObject.put("usuario", objectMapper.writeValueAsString(cronograma.getUsuario()));
            jsonObject.put("disciplinas", objectMapper.writeValueAsString(cronograma.getDisciplinas()));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(cronograma), headers);

            Log.e("CRONOGRAMA", cronograma.toString());
            Log.e("CRONOGRAMA JSON", jsonObject.toString());
            Log.e("CRONOGRAMA MAPPER", objectMapper.writeValueAsString(cronograma));

            ResponseEntity<Cronograma> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, Cronograma.class);

            cronograma = responseEntity.getBody();

            return cronograma;
        } catch (Exception e) {
            Log.e("ERRO SERV CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Cronograma buscarPorUsuarioCodigo(long codigo) {

        try {
            return restTemplate.exchange(
                    BASE_URL + "atual/" + codigo,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Cronograma>() {
                    }
            ).getBody();
        } catch (Exception e) {
            Log.e("ERRO REQUISIÇÃO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
