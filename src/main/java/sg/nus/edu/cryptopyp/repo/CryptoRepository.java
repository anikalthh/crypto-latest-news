package sg.nus.edu.cryptopyp.repo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CryptoRepository {
    
    @Autowired @Qualifier("redis")
    private RedisTemplate<String, String> template;

    private String hashRef = "savedArticles";
    
    public void saveToRedis(String id, String article) {
        template.opsForHash().put(hashRef, id, article);
    }

    public Optional<String> getArticleFromRedis(String id) {
        if (articleExists(id)) {
            return Optional.of(template.opsForHash().get(hashRef, id).toString());
        } 
        return Optional.empty();
    }

    public Boolean articleExists(String id) {
        return template.opsForHash().hasKey(hashRef, id);
    }
}
