package markup;

public class Text implements Markupable {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        out.append(text);
    }
}
