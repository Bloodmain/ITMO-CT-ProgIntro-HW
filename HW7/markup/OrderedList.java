package markup;

import java.util.ArrayList;
import java.util.List;

public class OrderedList extends AbsList {
    public OrderedList(List<ListItem> data) {
        this.data = new ArrayList<>(data);
        this.env = "enumerate";
    }

}
