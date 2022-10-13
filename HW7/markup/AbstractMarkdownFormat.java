package markup;

import java.util.List;

public abstract class AbstractMarkdownFormat extends AbstractMarkdownElement {
    public AbstractMarkdownFormat(List<MarkupElement> data) {
        super(data);
    }

    void toMarkdownWithFormat(StringBuilder out, String format) {
        out.append(format);
        super.toMarkdown(out);
        out.append(format);
    }
}
