package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;

public class AssuntoRestClient {
    private String BASE_URL = "http://192.168.0.35:8000/assunto/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Assunto> findAll(Long codigo) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List> responseEntity = restTemplate.exchange(
                    BASE_URL + "listar/" + codigo,
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            return mapper.convertValue(responseEntity.getBody(), new TypeReference<List<Assunto>>() {
            });
        } catch (Exception e) {
            Log.e("ERRO CONSULTA SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Assunto> findByDisciplina(Long codigo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    BASE_URL + "listarPorDisciplina/" + codigo,
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

    public List<Assunto> salvar(List<Assunto> assuntos) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(assuntos), headers);

            ResponseEntity<List> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, List.class);

            assuntos = mapper.convertValue(responseEntity.getBody(), new TypeReference<List<Assunto>>() {
            });

            return assuntos;
        } catch (Exception e) {
            Log.e("ERRO ASSUNTO SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(Assunto assunto) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(assunto), headers);

            restTemplate.put(BASE_URL + "atualizar/", entity);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SERV ASSUNTO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(long codigo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.delete(BASE_URL + "delete/" + codigo, entity);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SERV ASSUNTO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
