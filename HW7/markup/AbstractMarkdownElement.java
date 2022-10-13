package markup;

import java.util.List;

public abstract class AbstractMarkdownElement implements MarkupElement {
    private List<MarkupElement> data;
    public AbstractMarkdownElement(List<MarkupElement> data) {
        this.data = data;
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        for (MarkupElement el : data) {
            el.toMarkdown(out);
        }
    }
}
