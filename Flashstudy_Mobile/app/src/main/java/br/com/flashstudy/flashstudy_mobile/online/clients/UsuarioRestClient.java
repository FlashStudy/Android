package br.com.flashstudy.flashstudy_mobile.online.clients;


import android.util.Log;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class UsuarioRestClient {

    private String BASE_URL = "http://192.168.0.96:8000/usuario/";
    private RestTemplate restTemplate = new RestTemplate();

    public UsuarioOff cadastro(Usuario usuario) {
        try {
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("nome", usuario.getNome());
            jsonObject.put("email", usuario.getEmail());
            jsonObject.put("senha", usuario.getSenha());

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<?> responseEntity = restTemplate.postForEntity(BASE_URL + "salvar", entity, Usuario.class);

            usuario = (Usuario) responseEntity.getBody();

            return new UsuarioOff(usuario.getCodigo(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
        } catch (Exception e) {
            return null;
        }
    }

    public UsuarioOff login(Usuario usuario) {
        try {
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("email", usuario.getEmail());
            jsonObject.put("senha", usuario.getSenha());

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            Usuario usuario1 = (Usuario) restTemplate.postForEntity(BASE_URL + "login", entity, null).getBody();
            Log.i("retorno do usuario", usuario1.toString());
            return new UsuarioOff(usuario1.getCodigo(), usuario1.getNome(), usuario1.getEmail(), usuario1.getSenha());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean atualizar(Usuario usuario) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("codigo", usuario.getCodigo());
            jsonObject.put("nome", usuario.getNome());
            jsonObject.put("email", usuario.getEmail());
            jsonObject.put("senha", usuario.getSenha());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            restTemplate.put(BASE_URL + "atualizar", entity);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario procuraPorEmail(String email) {
        try {
            return restTemplate.exchange(
                    BASE_URL + "find/" + email,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Usuario>() {
                    }
            ).getBody();
        } catch (Exception e) {
            return null;
        }
    }
/*
    public List<UsuarioOff> findAll() {
        try {
            return restTemplate.exchange(
                    BASE_URL + "findall",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UsuarioOff>>() {
                    }
            ).getBody();
        } catch (Exception e) {
            return null;
        }
    }
*/
/*

    public UsuarioOff find(int id) {
        try {
            return restTemplate.exchange(
                    BASE_URL + "find/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<UsuarioOff>() {
                    }
            ).getBody();
        } catch (Exception e) {
            return null;
        }
    }
*/


/*
    public boolean delete(int id) {
        try {
            restTemplate.delete(BASE_URL + "delete/" + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
*/


}
