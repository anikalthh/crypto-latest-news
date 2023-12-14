package sg.nus.edu.cryptopyp.model;

import java.util.LinkedList;
import java.util.List;

public class SavedArticleIds {
    private List<String> listOfIdsToSave = new LinkedList<>();

    public List<String> getListOfIdsToSave() {
        return listOfIdsToSave;
    }

    public void setListOfIdsToSave(List<String> listOfIdsToSave) {
        this.listOfIdsToSave = listOfIdsToSave;
    }

    public SavedArticleIds() {
    }

    
}
