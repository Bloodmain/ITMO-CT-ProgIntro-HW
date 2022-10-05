import java.util.Arrays;

public class IntList {
    private final int START_ARRAY_SIZE = 1;
    private final int ARRAY_MULTIPLE_BY = 2;
    private int[] data;
    private int length;

    IntList() {
        data = new int[START_ARRAY_SIZE];
        length = 0;
    }

    private void checkAndAmortizeSize(int len) {
        if (len >= data.length) {
            data = Arrays.copyOf(data, len * ARRAY_MULTIPLE_BY);
        }
    }

    public void add(int x) {
        checkAndAmortizeSize(length + 1);
        data[length++] = x;
    }

    public int get(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= length) {
            throw new ArrayIndexOutOfBoundsException("Index is out of range");
        } else {
            return data[i];
        }
    }

    public void set(int i, int x) throws ArrayIndexOutOfBoundsException {
        if (i >= length) {
            throw new ArrayIndexOutOfBoundsException("Index is out of range");
        } else {
            data[i] = x;
        }
    }

    public int length() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length - 1; ++i) {
            res.append(data[i]);
            res.append(" ");
        }
        res.append(data[length - 1]);
        return res.toString();
    }
}
