import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;

public class MyScannerLite {
    final int BUFFER_SIZE = 1024;

    private final Reader reader;
    private final char[] buffer;
    private CharPredicate isCorrectLetter = Character::isLetter;

    private String token;
    private boolean isTokenAtEOL;
    private int bufferDataSize;
    private int bufferCurrentIndex;
    boolean isClosed;

    MyScannerLite(InputStream stream) {
        reader = new InputStreamReader(stream);
        buffer = new char[BUFFER_SIZE];
        token = "";
        isTokenAtEOL = false;
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
        isTokenAtEOL = false;
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
        boolean letterSequence = false;
        boolean tokenStarted = false;
        boolean rLineSeparator = false;
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

            if (buffer[bufferCurrentIndex] == '\n' || rLineSeparator) {
                wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                isTokenAtEOL = true;
                if (buffer[bufferCurrentIndex] == '\n') {
                    bufferCurrentIndex++;
                }
                break;
            }

            if (buffer[bufferCurrentIndex] == '\r') {
                rLineSeparator = true;
                bufferCurrentIndex++;
                isTokenAtEOL = true;
                continue;
            }

            if (isCorrectLetter.test(buffer[bufferCurrentIndex])) {
                if (!letterSequence && tokenStarted)  {
                    wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                    break;
                }
                tokenStarted = true;
                letterSequence = true;
            } else {
                letterSequence = false;
            }
            bufferCurrentIndex++;
        }

        token = wordBuffer.toString().trim();
    }

    public boolean hasNext() throws IllegalStateException, IOException {
        assertIsOpened();

        if (token.isEmpty() && !isTokenAtEOL) {
            readToken();
        }
        return !token.isEmpty() || isTokenAtEOL;
    }

    public boolean hasNextEmptyLine() throws IllegalStateException, IOException {
        if (!hasNext()) {
            return false;
        }

        return token.isEmpty() && isTokenAtEOL;
    }

    public Pair next() throws IllegalStateException, IOException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No tokens in stream");
        }

        String resToken = token;
        boolean isResEOL = isTokenAtEOL;
        clearToken();
        return new Pair(resToken, isResEOL);
    }
}
