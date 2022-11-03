package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsFormattedText extends AbsMarkupElement {
    protected String htmlFormat;
    AbsFormattedText(List<Paragraphable> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append("<").append(htmlFormat).append(">");
        super.toHtml(out);
        out.append("</").append(htmlFormat).append(">");
    }

}
