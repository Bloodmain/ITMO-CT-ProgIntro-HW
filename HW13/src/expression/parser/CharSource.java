package expression.parser;

public interface CharSource {
    boolean hasNext();
    char next();
    boolean seekBackwards(int offset);
    IllegalArgumentException error(String message);
}
