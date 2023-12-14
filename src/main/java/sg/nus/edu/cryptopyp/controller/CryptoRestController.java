package sg.nus.edu.cryptopyp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.cryptopyp.service.CryptoService;


@RestController
@RequestMapping(path="/news", produces= MediaType.APPLICATION_JSON_VALUE)
public class CryptoRestController {

    @Autowired
    private CryptoService cSvc;
    
    @GetMapping("/{id}")
    public ResponseEntity<String> getCryptoArticleById(@PathVariable("id") String id) {
        Optional<JsonObject> opt = cSvc.getArticleInJson(id);
        if (opt.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            String errorMsg = sb
                .append("Cannot find news article ")
                .append(id)
                .toString();
            JsonObject jsonObj = Json.createObjectBuilder()
                .add("error", errorMsg)
                .build();
            return ResponseEntity.status(404).body(jsonObj.toString());
        } else {
            JsonObject jsonObj = opt.get();
            return ResponseEntity.ok(jsonObj.toString());
        }
    }
}
