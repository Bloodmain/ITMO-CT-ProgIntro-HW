package markup;

import java.util.ArrayList;
import java.util.List;

public class Link extends AbsMarkupElement implements Paragraphable {
    private String href;

    public Link(List<Paragraphable> data, String href) {
        this.data = new ArrayList<>(data);
        this.href = href;
    }

    @Override
    public void toHtml(StringBuilder out) {
        out.append("<").append("a href='").append(this.href).append("'>");
        super.toHtml(out);
        out.append("</").append("a").append(">");
    }

}
