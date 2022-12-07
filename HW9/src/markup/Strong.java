package markup;

import java.util.List;

public class Strong extends AbsFormattedText implements Paragraphable {
    public Strong(List<Paragraphable> data) {
        super(data);
        this.markdownFormat = "__";
        this.texFormat = "textbf";
        this.htmlFormat = "strong";
    }
}
