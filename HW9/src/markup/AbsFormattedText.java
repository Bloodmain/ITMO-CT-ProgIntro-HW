package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsFormattedText extends AbsMarkupElement {
    protected String markdownFormat;
    protected String texFormat;
    protected String htmlFormat;

    AbsFormattedText(List<Paragraphable> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public void toMarkdown(StringBuilder out) {
        out.append(markdownFormat);
        super.toMarkdown(out);
        out.append(markdownFormat);
    }

    @Override
    public void toTex(StringBuilder out) {
        out.append(String.format("\\%s{", texFormat));
        super.toTex(out);
        out.append("}");
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append("<").append(htmlFormat).append(">");
        super.toHtml(out);
        out.append("</").append(htmlFormat).append(">");
    }
}
