package markup;

import java.util.List;

public class Strong extends AbstractMarkdownFormat {

    public Strong(List<Markupable> data) {
        super(data);
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        super.toMarkdownWithFormat(out, "__");
    }
}
