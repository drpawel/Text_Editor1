package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TextEditor extends JFrame {
    private final JTextArea TextArea = new JTextArea(20,40);
    private JFileChooser fileChooser = new JFileChooser(".");
    private final SearchEngine searchEngine = new SearchEngine();
    private JCheckBox checkBox;
    private ArrayList<Text> list;
    private JTextField searchField;
    private String path;
    private int index = 0;


    public TextEditor() {
        this.add(fileChooser);
        fileChooser.setName("FileChooser");
        this.getContentPane().add(prepareMainPanel());
        setFrame();
    }

    private JPanel prepareMainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 400));
        mainPanel.setLayout(new BorderLayout());
        setJMenuBar(prepareMenuBar());
        mainPanel.add(prepareSelectionPanel(),BorderLayout.PAGE_START);
        mainPanel.add(prepareTextPanel(),BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel prepareSelectionPanel(){
        JPanel selectionPanel = new JPanel();

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150 , 30));
        searchField.setName("SearchField");

        selectionPanel.add(prepareOpenButton());
        selectionPanel.add(prepareSaveButton());
        selectionPanel.add(searchField);
        selectionPanel.add(prepareStartSearchButton());
        selectionPanel.add(preparePreviousMatchButton());
        selectionPanel.add(prepareNextMatchButton());
        selectionPanel.add(prepareCheckBox());

        return selectionPanel;
    }

    private JButton prepareOpenButton(){
        JButton LoadButton = new JButton(new ImageIcon("D:\\IdeaProjects\\Plik\\resources\\open.png"));
        LoadButton.setPreferredSize(new Dimension(30, 30));
        LoadButton.setName("OpenButton");
        LoadButton.addActionListener(getLoadingListener());
        return LoadButton;
    }

    private JButton prepareSaveButton(){
        JButton SaveButton = new JButton(new ImageIcon("D:\\IdeaProjects\\Plik\\resources\\save.png"));
        SaveButton.setPreferredSize(new Dimension(30, 30));
        SaveButton.setName("SaveButton");

        SaveButton.addActionListener(getSavingListener());
        return SaveButton;
    }

    private JButton prepareStartSearchButton(){
        JButton StartSearchButton = new JButton(new ImageIcon("D:\\IdeaProjects\\Plik\\resources\\search.png"));
        StartSearchButton.setPreferredSize(new Dimension(30,30));
        StartSearchButton.setName("StartSearchButton");

        StartSearchButton.addActionListener(getSearchListener(1));
        return StartSearchButton;
    }

    private JButton preparePreviousMatchButton(){
        JButton PreviousMatchButton = new JButton(new ImageIcon("D:\\IdeaProjects\\Plik\\resources\\previous.png"));
        PreviousMatchButton.setPreferredSize(new Dimension(30,30));
        PreviousMatchButton.setName("PreviousMatchButton");

        PreviousMatchButton.addActionListener(getSearchListener(2));
        return PreviousMatchButton;
    }

    private JButton prepareNextMatchButton(){
        JButton NextMatchButton = new JButton(new ImageIcon("D:\\IdeaProjects\\Plik\\resources\\next.png"));
        NextMatchButton.setPreferredSize(new Dimension(30,30));
        NextMatchButton.setName("NextMatchButton");

        NextMatchButton.addActionListener(getSearchListener(3));
        return NextMatchButton;
    }

    private JCheckBox prepareCheckBox(){
        checkBox = new JCheckBox("Use Regex");
        checkBox.setName("UseRegExCheckbox");
        checkBox.addActionListener(e -> {
            searchEngine.changeAlgorithm();
        });

        return checkBox;
    }

    private JPanel prepareTextPanel(){
        JPanel textPanel = new JPanel();
        TextArea.setName("TextArea");
        JScrollPane ScrollPane = new JScrollPane(TextArea);
        ScrollPane.setName("ScrollPane");

        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textPanel.add(ScrollPane);
        return textPanel;
    }

    private JMenuBar prepareMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");

        JMenuItem loadItem = new JMenuItem("Open");
        loadItem.setName("MenuOpen");
        loadItem.addActionListener(getLoadingListener());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        saveItem.addActionListener(getSavingListener());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");
        exitItem.addActionListener(e -> dispose());

        menuFile.add(loadItem);
        menuFile.add(saveItem);
        menuFile.addSeparator();
        menuFile.add(exitItem);

        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");

        JMenuItem startSearchItem = new JMenuItem("Start search");
        startSearchItem.setName("MenuStartSearch");
        startSearchItem.addActionListener(getSearchListener(1));

        JMenuItem previousMatchItem = new JMenuItem("Previous match");
        previousMatchItem.setName("MenuPreviousMatch");
        previousMatchItem.addActionListener(getSearchListener(2));

        JMenuItem nextMatchItem = new JMenuItem("Next match");
        nextMatchItem.setName("MenuNextMatch");
        nextMatchItem.addActionListener(getSearchListener(3));

        JMenuItem useRegExpItem = new JMenuItem("Use regular expression");
        useRegExpItem.setName("MenuUseRegExp");
        useRegExpItem.addActionListener(e -> checkBox.doClick());
        //TODO change algorithm

        menuSearch.add(startSearchItem);
        menuSearch.add(previousMatchItem);
        menuSearch.add(nextMatchItem);
        menuSearch.add(useRegExpItem);

        menuBar.add(menuFile);
        menuBar.add(menuSearch);

        return menuBar;
    }

    private ActionListener getLoadingListener(){
        ActionListener loadingListener = e -> {
            if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
            TextArea.setText(null);
            try{
                TextArea.append(new String(Files.readAllBytes(Paths.get(path))));
            }catch (IOException | NullPointerException exception ){
                System.out.println("No file found");
            }
        };
        return loadingListener;
    }

    private ActionListener getSavingListener(){
        ActionListener savingListener = e -> {
            if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
            File file = new File(path);
            FileWriter writer = null;
            try {
                writer = new FileWriter(file);
                writer.write(TextArea.getText());
                writer.close();
            } catch (IOException ioException) {
                System.out.println("No file found: " +path);
            }
        };
        return savingListener;
    }

    private ActionListener getSearchListener(int mode){
        ActionListener searchListener = e -> {
            switch (mode){
                case 1:
                    String pattern = searchField.getText();
                    list = searchEngine.search(pattern,TextArea.getText());
                    index = 0;
                    break;
                case 2:
                    if(!list.isEmpty()){
                        if(index-1<0){
                            index = list.size()-1;
                        }else{
                            index--;
                        }
                    }
                    break;
                case 3:
                    if(!list.isEmpty()){
                        if(index+1>list.size()-1){
                            index = 0;
                        }else{
                            index++;
                        }
                    }
                    break;
            }
            TextArea.setCaretPosition(list.get(index).getIndex() + list.get(index).getLength());
            TextArea.select(list.get(index).getIndex(), list.get(index).getIndex() + list.get(index).getLength());
            TextArea.grabFocus();
        };
        return searchListener;
    }

    private void setFrame(){
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}
