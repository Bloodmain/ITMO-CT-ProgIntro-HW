package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends AbsMarkupElement {
    public ListItem(List<Listable> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public void toTex(StringBuilder out) {
        out.append("\\item ");
        super.toTex(out);
    }
}
