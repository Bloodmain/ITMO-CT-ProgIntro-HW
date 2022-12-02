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

    public boolean test(char expected) {
        return ch == expected;
    }

    public boolean test(String expected) {
        for (int i = 0; i < expected.length(); ++i) {
            if (!test(expected.charAt(i))) {
                return false;
            }
        }
        return true;
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

    public boolean checkBounds(char leftBound, char rightBound) {
        return leftBound <= ch && ch <= rightBound;
    }

    public void assertNextEquals(char expected) {
        if (!test(expected)) {
            throw source.error("Expected '" + expected + "' but found '" + ch + "'");
        }
    }

    public void assertNextOneOfThe(String expected) {
        for (int i = 0; i < expected.length(); ++i) {
            if (test(expected.charAt(i))) {
                return;
            }
        }
        throw source.error("Expected one of the symbols '" + expected + "' but found '" + ch + "'");
    }

    public void asserEOF() {
        if (!checkEOF()) {
            throw source.error("Expected EOF but found '" + ch + "'");
        }
    }
}
