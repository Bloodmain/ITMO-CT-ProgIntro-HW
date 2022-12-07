package markup;

import java.util.List;

public abstract class AbsMarkupElement implements MarkupElement {
    protected List<MarkupElement> data;

    @Override
    public void toTex(StringBuilder out) {
        for (Texable el : data) {
            el.toTex(out);
        }
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        for (Markdownable el : data) {
            el.toMarkdown(out);
        }
    }

    @Override
    public void toHtml(StringBuilder out) {
        for (MarkupElement el : data) {
            el.toHtml(out);
        }
    }
}
