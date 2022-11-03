package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends AbsFormattedText {
    public Paragraph(List<Paragraphable> data) {
        super(data);
        this.htmlFormat = "p";
    }
}
