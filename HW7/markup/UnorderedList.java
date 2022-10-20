package markup;

import java.util.ArrayList;
import java.util.List;

public class UnorderedList extends AbsList {
    public UnorderedList(List<ListItem> data) {
        this.data = new ArrayList<>(data);
        this.env = "itemize";
    }
}
