package sg.nus.edu.cryptopyp.service;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.cryptopyp.model.CryptoArticle;
import sg.nus.edu.cryptopyp.repo.CryptoRepository;
import sg.nus.edu.cryptopyp.utility.Utility;

@Service
public class CryptoService {
    
    @Autowired
    private CryptoRepository cRepo;

    @Value("${crypto.api.key}")
    private String cryptoApiKey;

    private String articleBaseUrl = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    public List<CryptoArticle> getArticles() {
        String fullUrl = UriComponentsBuilder
            .fromUriString(articleBaseUrl)
            .toUriString();

        RequestEntity<Void> req = RequestEntity
            .get(fullUrl)
            .build();
        
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        JsonReader jr = Json.createReader(new StringReader(resp.getBody()));
        JsonObject jsonObj = jr.readObject();
        JsonArray ja = jsonObj.getJsonArray("Data");

        List<CryptoArticle> listOfCryptoArticleObjects = ja.stream()
            .map(j -> j.asJsonObject())
            .map(o -> {
                String id = o.getString("id");
                long published_on = (long) o.getInt("published_on");
                String title = o.getString("title");
                String url = o.getString("url");
                String imageurl = o.getString("imageurl");
                String body = o.getString("body");
                String tags = o.getString("tags");
                String categories = o.getString("categories");
                return new CryptoArticle(id, published_on, title, url, imageurl, body, tags, categories);
            })
            .toList();

        // System.out.printf("\n ----- LIST OF CRYPTO ARTICLES ----- \n");
        // System.out.println(listOfCryptoArticleObjects);

        return listOfCryptoArticleObjects;
    }

    public void saveArticles(List<String> listOfCryptoArticles) {

        for (String a : listOfCryptoArticles) {
            String id = a.split("\\^")[0];
            JsonObject jsonObj = Utility.convertFromThymeleaf(a);
            System.out.printf("\nsaving %s, %s \n", id, jsonObj);
            cRepo.saveToRedis(id, jsonObj.toString());
        }      
    }

    public Optional<JsonObject> getArticleInJson(String id) {
        Optional<String> opt = cRepo.getArticleFromRedis(id);

        if (opt.isEmpty()) {
            return Optional.empty();
        } else {
            JsonReader jr = Json.createReader(new StringReader(opt.get()));
            JsonObject jsonObj = jr.readObject();
            return Optional.of(jsonObj);
        }
    }
}
