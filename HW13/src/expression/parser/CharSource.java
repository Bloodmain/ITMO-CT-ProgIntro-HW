package expression.parser;

public interface CharSource {
    boolean hasNext();
    char next();
    void seekBackwards(int offset);
    IllegalArgumentException error(String message);
}
