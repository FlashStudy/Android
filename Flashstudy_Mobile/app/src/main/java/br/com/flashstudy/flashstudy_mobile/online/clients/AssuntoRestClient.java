package br.com.flashstudy.flashstudy_mobile.online.clients;

import android.content.res.Resources;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;

public class AssuntoRestClient {
    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public AssuntoRestClient() {
        Resources resources = Resources.getSystem();
        BASE_URL = resources.getString(R.string.base_url) + "/assunto/";
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

    public Assunto salvar(Assunto assunto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            jsonObject.put("usuario", mapper.writeValueAsString(assunto.getUsuario()));
            jsonObject.put("tema", assunto.getTema());
            jsonObject.put("disciplina", mapper.writeValueAsString(assunto.getDisciplina()));
            jsonObject.put("codigo", assunto.getCodigo());

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            return restTemplate.postForEntity(BASE_URL + "salvar", entity, Assunto.class).getBody();
        } catch (Exception e) {
            Log.e("ERRO USUARIO SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
