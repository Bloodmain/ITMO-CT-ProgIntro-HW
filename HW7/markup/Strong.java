package markup;

import java.util.List;

public class Strong extends AbstractMarkdownFormat {

    public Strong(List<MarkupElement> data) {
        super(data);
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        super.toMarkdownWithFormat(out, "__");
    }
}
