import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class MyScannerLite {
    final int BUFFER_SIZE = 1024;

    private final Reader reader;
    private final char[] buffer;
    private CharPredicate isCorrectLetter = Character::isLetter;
    private CharPredicate isLineSeparator = chr -> chr == '\n';


    String token = "";
    boolean isTokenAtEOL = false;
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

    public void setCorrectLetter(CharPredicate delimiter) {
        isCorrectLetter = delimiter;
    }

    public void setLineSeparator(CharPredicate lineSeparator) {
        isLineSeparator = lineSeparator;
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

            if (isLineSeparator.test(buffer[bufferCurrentIndex])) {
                wordBuffer.append(buffer, start, bufferCurrentIndex - start);
                isTokenAtEOL = true;
                bufferCurrentIndex++;
                break;
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
