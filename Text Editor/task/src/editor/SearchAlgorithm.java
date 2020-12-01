package editor;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SearchAlgorithm {
    ArrayList<Text> search(String pattern, String text);
}

class NaiveSearch implements SearchAlgorithm{
    @Override
    public ArrayList<Text> search(String regex,String text) {
        ArrayList<Text> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()){
            list.add(new Text(matcher.start(),matcher.group().length()));
        }
        return list;
    }
}

class RegexSearch implements SearchAlgorithm{
    @Override
    public ArrayList<Text> search(String regex,String text) {
        ArrayList<Text> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()){
            list.add(new Text(matcher.start(),matcher.group().length()));
        }
        return list;
    }
}
