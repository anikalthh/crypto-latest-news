package sg.nus.edu.cryptopyp.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.cryptopyp.model.CryptoArticle;
import sg.nus.edu.cryptopyp.service.CryptoService;

@Controller
@RequestMapping("/")
public class CryptoController {
    
    @Autowired
    private CryptoService cSvc;

    @GetMapping
    public String getArticles(Model m, HttpSession sess) {
        List<CryptoArticle> listOfCryptoArticleObjects = cSvc.getArticles();
        m.addAttribute("ifSaved", sess.getAttribute("ifSaved"));
        m.addAttribute("articles", listOfCryptoArticleObjects);

        // INVALIDATE SESH
        sess.invalidate();
        return "index";
    }

    @PostMapping("/articles")
    public String saveArticles(@RequestBody MultiValueMap<String, String> payload, HttpSession sess) {
        List<String> listOfCryptoArticles = payload.get("save-checkbox");

        System.out.printf("\n---payload---");
        System.out.println(listOfCryptoArticles);

        System.out.println("\n--------PRINTING EACH ARTICLE-------");
        for (String a : listOfCryptoArticles) { // unable to iterate here
            System.out.printf("article: %s \n", a);
        }

        cSvc.saveArticles(listOfCryptoArticles);

        // m.addAttribute("saved-ids", listOfCryptoArticles);
        sess.setAttribute("ifSaved", true);
        return "redirect:/";
    }
}
