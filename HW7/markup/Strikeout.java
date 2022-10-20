package markup;

import java.util.List;

public class Strikeout extends AbsFormattedText implements Paragraphable {
    public Strikeout(List<Paragraphable> data) {
        super(data);
        this.markdownFormat = "~";
        this.texFormat = "textst";
    }
}
