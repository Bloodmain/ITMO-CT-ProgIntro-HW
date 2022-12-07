package markup;

public class Text implements MarkupElement, Paragraphable {
    private final String data;

    public Text(String data) {
        this.data = data;
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        out.append(data);
    }

    @Override
    public void toTex(StringBuilder out) {
        out.append(data);
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append(data);
    }
}
