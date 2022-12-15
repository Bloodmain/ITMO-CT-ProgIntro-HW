package expression.parser;

import java.util.function.Predicate;

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

    public boolean test(Predicate<Character> predicate) {
        return predicate.test(ch);
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
    public String getSatisfied(Predicate<Character> predicate) {
        StringBuilder res = new StringBuilder();
        while (predicate.test(ch)) {
            res.append(consume());
        }
        return res.toString();
    }

    public void seekBackwards(int offset) {
        source.seekBackwards(offset + 1);
        ch = source.next();
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
