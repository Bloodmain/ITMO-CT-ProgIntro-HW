import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class MyScanner {
    final int BUFFER_SIZE = 1024;

    private final Reader reader;
    private final char[] buffer;
    private CharPredicate checkForDelimiter = Character::isWhitespace;
    private CharPredicate checkForLineSeparator = chr -> chr == '\n';

    String token = "";
    int tokenStart = 0;
    boolean isTokenNotWhitespace = false;

    int bufferDataSize = 0;
    int bufferCurrentIndex = 0;
    boolean isClosed = false;

    MyScanner(InputStream stream) {
        reader = new InputStreamReader(stream);
        buffer = new char[BUFFER_SIZE];
    }

    MyScanner(String lineToParse) {
        reader = new InputStreamReader(
            new ByteArrayInputStream(lineToParse.getBytes(StandardCharsets.UTF_8))
        );
        buffer = new char[BUFFER_SIZE];
    }

    MyScanner(String filepath, String charsetName) throws FileNotFoundException, IOException, UnsupportedCharsetException {
        reader = new InputStreamReader(
            new FileInputStream(filepath), charsetName
        );
        buffer = new char[BUFFER_SIZE];
    }

    MyScanner(String filepath, Charset charsetName) throws FileNotFoundException, IOException, UnsupportedCharsetException {
        reader = new InputStreamReader(
            new FileInputStream(filepath), charsetName
        );
        buffer = new char[BUFFER_SIZE];
    }

    public CharPredicate getDelimiter() {
        return checkForDelimiter;
    }

    public CharPredicate getLineSeparator() {
        return checkForLineSeparator;
    }

    public void setDelimiter(CharPredicate delimiter) {
        checkForDelimiter = delimiter;
    }

    public void setLineSeparator(CharPredicate lineSeparator) {
        checkForLineSeparator = lineSeparator;
    }

    private void readToBuffer() throws IOException {
        bufferDataSize = reader.read(buffer);
        bufferCurrentIndex = 0;
    }

    public void close() throws IllegalStateException, IOException {
        if (isClosed) {
            throw new IllegalStateException("Scanner has already been closed");
        } else {
            reader.close();
            isClosed = true;
        }
    }

    private void clearToken() {
        token = "";
        tokenStart = 0;
        isTokenNotWhitespace = false;
    }

    private void assertIsOpened() throws IllegalStateException {
        if (isClosed) {
            throw new IllegalStateException("Scanner has been closed");
        };
    }

    private void readToken(boolean readTillEOL) throws IllegalStateException, IOException, NoSuchElementException {
        assertIsOpened();

        token = "";
        tokenStart = 0;
        boolean tokenStarted = readTillEOL;
        int start = bufferCurrentIndex;
        StringBuilder wordBuffer = new StringBuilder();
        while (true) {
            if (bufferCurrentIndex >= bufferDataSize) {
                wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                readToBuffer();
                start = 0;
                if (bufferDataSize == -1) {
                    token = wordBuffer.toString();
                    break;
                }
            }

            if (checkForDelimiter.test(buffer[bufferCurrentIndex])) {
                if (tokenStarted)  {
                    wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                    if (readTillEOL) {
                        bufferCurrentIndex++;
                    }
                    token = wordBuffer.toString();
                    break;
                }
            } else {
                tokenStarted = true;
            }

            bufferCurrentIndex++;
            
        }

        isTokenNotWhitespace = tokenStarted;
    }

    public boolean hasNext() throws IllegalStateException, IOException {
        assertIsOpened();

        if (token.isEmpty() || tokenStart >= token.length()) {
            readToken(false);
        }
        return isTokenNotWhitespace;
    }

    private boolean isNumeric(String token, String maxValue, String minValue) {
        if (token.length() == 0) {
            return false;
        }

        String valueToCompareWith = maxValue;
        int startFrom = 0;

        if (token.charAt(0) == '-') {
            valueToCompareWith = minValue;
            startFrom = 1;
        }

        if (token.length() == startFrom) {
            return false;
        }

        if (token.length() > valueToCompareWith.length()) {
            return false;
        }

        for (int i = startFrom; i < token.length(); ++i) {
            if (!Character.isDigit(token.charAt(i))) {
                return false;
            }
        }

        if (token.length() == valueToCompareWith.length()) {
            return token.compareTo(valueToCompareWith) < 0;
        }

        return true;
    }

    public boolean hasNextInt() throws IllegalStateException, IOException {
        if (!hasNext()) {
            return false;
        } else {
            return isNumeric(token.substring(tokenStart).trim(), String.valueOf(Integer.MAX_VALUE), String.valueOf(Integer.MIN_VALUE));
        }
    }

    public String next() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No tokens in stream");
        }
        
        String res = token.substring(tokenStart).trim();
        clearToken();
        return res;
    }

    public int nextInt() throws IllegalStateException, IOException, InputMismatchException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No tokens in stream");
        }
        if (!hasNextInt()) {
            throw new InputMismatchException("Next token is doesn't match Integer");
        }

        int res = Integer.parseInt(token.substring(tokenStart).trim());
        clearToken();
        return res;
    }

    public boolean hasNextLine() throws IllegalStateException, IOException {
        assertIsOpened();
        if (token.isEmpty() || tokenStart >= token.length()) {
            readToken(false);
        }
        return !token.isEmpty();
    }

    public String nextLine() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNextLine()) {
            throw new NoSuchElementException("No line found"); 
        }

        String res = token.substring(tokenStart);

        if (res.indexOf('\n') > -1) {
            int i = res.indexOf('\n');
            tokenStart += i + 1;
            return res.substring(0, i);
        }

        CharPredicate lastDelimiter = checkForDelimiter;
        setDelimiter(checkForLineSeparator);
        readToken(true);
        setDelimiter(lastDelimiter);

        res += token;
        clearToken();
        return res;
    }
}
