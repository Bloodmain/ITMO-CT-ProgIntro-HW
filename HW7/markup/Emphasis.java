package markup;

import java.util.List;

public class Emphasis extends AbstractMarkdownFormat {

    public Emphasis(List<MarkupElement> data) {
        super(data);
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        super.toMarkdownWithFormat(out, "*");
    }
}
