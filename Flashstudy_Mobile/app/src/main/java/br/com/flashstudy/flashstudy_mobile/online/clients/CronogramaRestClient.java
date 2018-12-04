package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.content.res.Resources;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;

public class CronogramaRestClient {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public CronogramaRestClient() {
        Resources resources = Resources.getSystem();
        BASE_URL = resources.getString(R.string.base_url) + "/cronograma/";
    }

    public Cronograma salvar(Cronograma cronograma) {
        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("codigo", cronograma.getCodigo());
            jsonObject.put("inicio", cronograma.getInicio());
            jsonObject.put("fim", cronograma.getFim());
            jsonObject.put("usuario", objectMapper.writeValueAsString(cronograma.getUsuario()));
            jsonObject.put("disciplinas", objectMapper.writeValueAsString(cronograma.getDisciplinas()));

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<Cronograma> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, Cronograma.class);

            cronograma = responseEntity.getBody();

            return cronograma;
        } catch (Exception e) {
            Log.e("ERRO SERV CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Cronograma buscarPorUsuarioCodigo(long codigo){

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
