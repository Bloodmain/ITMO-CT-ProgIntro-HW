package markup;

import java.util.List;

public class Emphasis extends AbsFormattedText implements Paragraphable {
    public Emphasis(List<Paragraphable> data) {
        super(data);
        this.htmlFormat = "em";
    }

}