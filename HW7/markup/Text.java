package markup;

public class Text implements MarkupElement {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        out.append(text);
    }
}
