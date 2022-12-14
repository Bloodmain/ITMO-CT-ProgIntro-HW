package expression.parser;

public class BaseParser {
    protected final static char END = '\0';
    protected final CharSource source;
    protected char ch;


    public BaseParser(CharSource source) {
        this.source = source;
        consume();
    }

    public char consume() {
        char now = ch;
        ch = source.hasNext() ? source.next() : END;
        return now;
    }
    public void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            consume();
        }
    }

    public void assertNextWhiteSpace() {
        if (!Character.isWhitespace(ch)) {
            throw source.error("Expected whitespace but found '" + ch + "'");
        }
    }

    public boolean test(char expected) {
        return ch == expected;
    }

    public boolean checkEOF() {
        return ch == END;
    }

    public boolean testAndConsume(char expected) {
        if (test(expected)) {
            consume();
            return true;
        }
        return false;
    }

    public void assertAndConsume(String expected) {
        for (int i = 0; i < expected.length(); ++i) {
            if (!testAndConsume(expected.charAt(i))) {
                throw source.error("Expected '" + expected + "' but found '" + ch + "'");
            }
        }
    }

    public boolean checkBounds(char leftBound, char rightBound) {
        return leftBound <= ch && ch <= rightBound;
    }

    public void assertNextEquals(char expected) {
        if (!test(expected)) {
            throw source.error("Expected '" + expected + "' but found '" + ch + "'");
        }
    }

    public void assertEOF() {
        if (!checkEOF()) {
            throw source.error("Expected EOF but found '" + ch + "'");
        }
    }
}
