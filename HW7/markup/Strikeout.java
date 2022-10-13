package markup;

import java.util.List;

public class Strikeout extends AbstractMarkdownFormat {

    public Strikeout(List<MarkupElement> data) {
        super(data);
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        super.toMarkdownWithFormat(out, "~");
    }
}
