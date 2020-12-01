package editor;

import java.util.ArrayList;

public class SearchEngine {
    private SearchAlgorithm searchAlgorithm;
    private boolean flag = true;

    public SearchEngine() {
        this.searchAlgorithm = new NaiveSearch();
    }

    public void changeAlgorithm(){
        if(flag){
            this.searchAlgorithm = new RegexSearch();
            flag = false;
        }else{
            this.searchAlgorithm = new NaiveSearch();
            flag = true;
        }
    }

    public ArrayList<Text> search(String pattern, String text){
        return this.searchAlgorithm.search(pattern,text);
    }
}
