package markup;

public class Text implements MarkupElement, Paragraphable {
    private final String data;

    public Text(String data) {
        this.data = data;
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append(data);
    }
}
