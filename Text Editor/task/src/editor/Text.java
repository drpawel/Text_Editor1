package editor;

public class Text {
    private int index;
    private int length;

    public Text(int index, int length) {
        this.index = index;
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return length;
    }
}
