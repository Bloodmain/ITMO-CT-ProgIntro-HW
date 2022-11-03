package markup;

import java.util.List;

public abstract class AbsMarkupElement implements MarkupElement {
    protected List<MarkupElement> data;

    @Override
    public void toHtml(StringBuilder out) {
        for (MarkupElement el : data) {
            el.toHtml(out);
        }
    }
}
