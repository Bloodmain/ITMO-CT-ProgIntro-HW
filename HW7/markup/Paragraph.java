package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends AbsMarkupElement implements Listable {
    public Paragraph(List<Paragraphable> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append("<").append("p").append(">");
        super.toHtml(out);
        out.append("</").append("p").append(">");
    }
}
