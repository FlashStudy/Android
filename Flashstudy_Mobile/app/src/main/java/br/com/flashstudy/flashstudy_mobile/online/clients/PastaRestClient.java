package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Pasta;

public class PastaRestClient {
    private String BASE_URL = "http://192.168.0.35:8000/usuario/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Pasta> findAll(Long codigo) {
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

    public Pasta salvar(Pasta pasta) {
        try {
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("nome", pasta.getNome());
            jsonObject.put("usuario", pasta.getUsuario());

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
            return restTemplate.postForEntity(BASE_URL + "salvar", entity, Pasta.class).getBody();
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

}
