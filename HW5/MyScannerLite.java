import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.NoSuchElementException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class MyScannerLite {
    final int BUFFER_SIZE = 1024;

    private final Reader reader;
    private final char[] buffer;
    private CharPredicate checkForDelimiter = Character::isWhitespace;
    private CharPredicate checkForLineSeparator = chr -> chr == '\n';

    String token = "";
    boolean isTokenNotWhitespace = false;

    int bufferDataSize = 0;
    int bufferCurrentIndex = 0;
    boolean isClosed = false;

    MyScannerLite(InputStream stream) {
        reader = new InputStreamReader(stream);
        buffer = new char[BUFFER_SIZE];
    }

    MyScannerLite(String lineToParse) {
        reader = new InputStreamReader(
                new ByteArrayInputStream(lineToParse.getBytes(StandardCharsets.UTF_8))
        );
        buffer = new char[BUFFER_SIZE];
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

    private void clearToken() {
        token = "";
        isTokenNotWhitespace = false;
    }

    public void close() throws IllegalStateException, IOException {
        if (isClosed) {
            throw new IllegalStateException("Scanner has already been closed");
        } else {
            reader.close();
            isClosed = true;
        }
    }

    private void assertIsOpened() throws IllegalStateException {
        if (isClosed) {
            throw new IllegalStateException("Scanner has been closed");
        };
    }

    private void readToken(boolean readTillEOL) throws IllegalStateException, IOException, NoSuchElementException {
        assertIsOpened();

        clearToken();
        boolean tokenStarted = false;
        int start = bufferCurrentIndex;

        StringBuilder wordBuffer = new StringBuilder();

        while (true) {
            if (bufferCurrentIndex >= bufferDataSize) {
                wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                readToBuffer();
                start = 0;
                if (bufferDataSize == -1) {
                    break;
                }
            }

            if (checkForDelimiter.test(buffer[bufferCurrentIndex])) {
                if (tokenStarted || readTillEOL)  {
                    wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                    if (checkForLineSeparator.test(buffer[bufferCurrentIndex])) {
                        bufferCurrentIndex++;
                    }
                    break;
                }
            } else {
                tokenStarted = true;
            }
            bufferCurrentIndex++;
        }

        token = wordBuffer.toString();
        isTokenNotWhitespace = tokenStarted;
    }

    public boolean hasNext() throws IllegalStateException, IOException {
        assertIsOpened();

        if (token.isEmpty()) {
            readToken(false);
        }
        return isTokenNotWhitespace;
    }

    public String next() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No tokens in stream");
        }

        String res = token.trim();
        clearToken();
        return res;
    }

    public boolean hasNextLine() throws IllegalStateException, IOException {
        assertIsOpened();
        if (token.isEmpty()) {
            if (bufferCurrentIndex < bufferDataSize) {
                return true;
            } else {
                readToBuffer();
                return bufferDataSize != -1;
            }
        }
        return true;
    }

    public String nextLine() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNextLine()) {
            throw new NoSuchElementException("No line found");
        }

        String res = token;
        CharPredicate lastDelimiter = checkForDelimiter;
        setDelimiter(checkForLineSeparator);
        readToken(true);
        setDelimiter(lastDelimiter);

        res += token;
        clearToken();
        return res;
    }
}
