package markup;

public abstract class AbsList extends AbsMarkupElement implements Listable {
    protected String env;
    @Override
    public void toTex(StringBuilder out) {
        out.append(String.format("\\begin{%s}", env));
        super.toTex(out);
        out.append(String.format("\\end{%s}", env));
    }
}
