package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.flashstudy.flashstudy_mobile.online.model.Ciclo;

public class CicloRestClient {
    private String BASE_URL = "http://192.168.0.35:8000/ciclo/";
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public Ciclo salvar(Ciclo ciclo) {
        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("codigo", ciclo.getCodigo());
            jsonObject.put("dias", objectMapper.writeValueAsString(ciclo.getDias()));
            jsonObject.put("numMaterias", ciclo.getNumMaterias());
            jsonObject.put("usuario", objectMapper.writeValueAsString(ciclo.getUsuario()));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(ciclo), headers);

            return restTemplate.postForEntity(BASE_URL + "salvar", entity, Ciclo.class).getBody();
        } catch (Exception e) {
            Log.e("ERRO SERV CICLO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Ciclo buscarPorUsuarioCodigo(long codigo) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(
                    BASE_URL + "atual/" + codigo,
                    HttpMethod.GET,
                    entity,
                    Ciclo.class
            ).getBody();
        } catch (Exception e) {
            Log.e("ERRO REQUISIÇÃO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
