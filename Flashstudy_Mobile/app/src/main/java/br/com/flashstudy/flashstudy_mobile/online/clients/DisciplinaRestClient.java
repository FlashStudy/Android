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

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;

public class DisciplinaRestClient {
    private String BASE_URL = "http://192.168.0.11:7000/disciplina/";
    private RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public List<Disciplina> listar(Long codigo) {
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

    public boolean atualizar(Disciplina disciplina){
        try {

            ObjectMapper mapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(disciplina), headers);

            restTemplate.put(BASE_URL + "atualizar/", entity);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SERV DISCIPLINA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Disciplina> salvarLista(List<Disciplina> disciplinaList) {
        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("disciplinas", objectMapper.writeValueAsString(disciplinaList));

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<?> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, List.class);

            return (List<Disciplina>) responseEntity.getBody();

        } catch (Exception e) {
            Log.e("ERRO SERV DISCIPLINA", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Disciplina salvar(Disciplina disciplina) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("codigo", disciplina.getCodigo());
            jsonObject.put("nome", disciplina.getNome());
            jsonObject.put("usuario", mapper.writeValueAsString(disciplina.getUsuario()));

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            return restTemplate.postForEntity(BASE_URL + "salvar", entity, Disciplina.class).getBody();
        } catch (Exception e) {
            Log.e("ERRO USUARIO SERV", e.getMessage());
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
