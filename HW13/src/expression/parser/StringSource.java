package expression.parser;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
        this.pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public boolean seekBackwards(int offset) {
        if (offset > pos || offset < 0) {
            throw new IllegalArgumentException("Incorrect offset");
        }
        pos -= offset;
        return pos > 0;
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException("Pos " + pos + ": " + message);
    }
}
