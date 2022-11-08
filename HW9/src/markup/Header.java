package markup;

import java.util.List;

public class Header extends AbsFormattedText implements Paragraphable {
    public Header(List<Paragraphable> data, int level) {
        super(data);
        this.htmlFormat = "h" + level;
    }
}
