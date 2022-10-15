package markup;

import java.util.List;

public abstract class AbstractMarkdownElement implements Markupable {
    private List<Markupable> data;
    public AbstractMarkdownElement(List<Markupable> data) {
        this.data = data;
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        for (Markupable el : data) {
            el.toMarkdown(out);
        }
    }
}
