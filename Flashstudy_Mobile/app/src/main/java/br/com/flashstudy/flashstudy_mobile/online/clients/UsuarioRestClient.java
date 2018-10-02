package br.com.flashstudy.flashstudy_mobile.online.clients;


import android.util.Log;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class UsuarioRestClient {

    private String BASE_URL = "http://192.168.0.17:8000/usuario/";
    private RestTemplate restTemplate = new RestTemplate();

    public boolean cadastro(Usuario usuario) {
        try {
            Map<String, String> values = new HashMap<>();
            values.put("nome", usuario.getNome());
            values.put("email", usuario.getEmail());
            values.put("senha", usuario.getSenha());

            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("nome", usuario.getNome());
            jsonObject.put("email", usuario.getEmail());
            jsonObject.put("senha", usuario.getSenha());

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            restTemplate.postForEntity(BASE_URL + "save", entity, null);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UsuarioOff login(Usuario usuario) {
        try {
            Map<String, String> values = new HashMap<>();
            values.put("email", usuario.getEmail());
            values.put("senha", usuario.getSenha());

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
            Map<String, String> values = new HashMap<>();
            values.put("codigo", String.valueOf(usuario.getCodigo()));
            values.put("nome", usuario.getNome());
            values.put("email", usuario.getEmail());
            values.put("senha", usuario.getSenha());

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
