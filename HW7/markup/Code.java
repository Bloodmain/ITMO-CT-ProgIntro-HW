package markup;

import java.util.List;

public class Code extends AbsFormattedText implements Paragraphable {
    public Code(List<Paragraphable> data) {
        super(data);
        this.htmlFormat = "code";
    }
}
