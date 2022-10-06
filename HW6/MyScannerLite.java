import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;

public class MyScannerLite implements AutoCloseable {
    final int BUFFER_SIZE = 1024;

    private final Reader reader;
    private final char[] buffer;
    private CharPredicate isCorrectLetter = Character::isLetter;

    private String token;
    private int tokenSkippedLine;
    private int bufferDataSize;
    private int bufferCurrentIndex;
    boolean isClosed;

    MyScannerLite(InputStream stream) {
        reader = new InputStreamReader(stream);
        buffer = new char[BUFFER_SIZE];
        token = "";
        tokenSkippedLine = 0;
        bufferDataSize = 0;
        bufferCurrentIndex = 0;
        isClosed = false;
    }

    public void setCorrectLetter(CharPredicate delimiter) {
        isCorrectLetter = delimiter;
    }

    private void readToBuffer() throws IOException {
        bufferDataSize = reader.read(buffer);
        bufferCurrentIndex = 0;
    }

    private void clearToken() {
        token = "";
        tokenSkippedLine = 0;
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
        }
    }

    private void readToken() throws IllegalStateException, IOException, NoSuchElementException {
        assertIsOpened();

        clearToken();
        boolean tokenStarted = false;
        boolean rLineSeparator = false;
        int tokenStartIndex = -1;

        StringBuilder wordBuffer = new StringBuilder();

        while (true) {
            if (bufferCurrentIndex >= bufferDataSize) {
                if (tokenStarted) {
                    wordBuffer.append(buffer, tokenStartIndex, bufferCurrentIndex - tokenStartIndex);
                    tokenStartIndex = 0;
                }
                readToBuffer();
                if (bufferDataSize == -1) {
                    break;
                }
            }

            if (isCorrectLetter.test(buffer[bufferCurrentIndex])) {
                if (!tokenStarted) {
                    tokenStarted = true;
                    tokenStartIndex = bufferCurrentIndex;
                }
            } else {
                if (tokenStarted) {
                    wordBuffer.append(buffer, tokenStartIndex, bufferCurrentIndex - tokenStartIndex);
                    break;
                }
            }

            if (buffer[bufferCurrentIndex] == '\n') {
                if (!rLineSeparator) {
                    tokenSkippedLine++;
                }
                rLineSeparator = false;
            } else if (buffer[bufferCurrentIndex] == '\r') {
                rLineSeparator = true;
                tokenSkippedLine++;
            } else if (rLineSeparator) {
                rLineSeparator = false;
            }
            bufferCurrentIndex++;
        }

        token = wordBuffer.toString();
    }

    public boolean hasNext() throws IllegalStateException, IOException {
        assertIsOpened();

        if (token.isEmpty()) {
            readToken();
        }
        return !token.isEmpty();
    }

    public String next() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No tokens in stream");
        }

        String resToken = token;
        token = "";
        return resToken;
    }

   public int getTokenSkippedLine() throws IllegalStateException {
        assertIsOpened();
        return tokenSkippedLine;
   }
}
