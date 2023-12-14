package sg.nus.edu.cryptopyp.utility;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.cryptopyp.model.CryptoArticle;

public class Utility {
    
    public static String cryptoArticleToJsonStr(CryptoArticle article) {
        JsonObject jsonObj = Json.createObjectBuilder()
            .add("id", article.getId())
            .add("published_on", article.getPublished_on())
            .add("title", article.getTitle())
            .add("url", article.getUrl())
            .add("imageurl", article.getImageurl())
            .add("body", article.getBody())
            .add("tags", article.getTags())
            .add("categories", article.getCategories())
            .build();
        return jsonObj.toString();
    }

    public static JsonObject convertFromThymeleaf(String cryptoStr) {
        String[] vals = cryptoStr.split("\\^");
        System.out.printf("\n----VALS in array?----: %s", vals);

        int count = 0;
        for (String str : vals) {

            System.out.printf("\nidx: %s, val: %s\n", count, vals);
            count ++;
        }
        JsonObject jsonObj = Json.createObjectBuilder()
            .add("id", vals[0])
            .add("title", vals[1])
            .add("published_on", vals[2])
            .add("body", vals[3])
            .add("imageurl", vals[4])
            .add("url", vals[5])
            .add("tags", vals[6])
            .add("categories", vals[7])
            .build();

        return jsonObj;
    }

}
