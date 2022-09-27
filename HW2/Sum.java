public class Sum {
    public static void main(String[] args) {
        int result = 0;

        for (int i = 0; i < args.length; i = i + 1) {
            String arg = args[i] + " ";
            int currentNumberStart = 0;
            boolean isReadingNumber = false;

            for (int j = 0; j < arg.length(); j = j + 1) {
                char symb = arg.charAt(j);
                
                if (Character.isWhitespace(symb)) {
                    if (isReadingNumber) {
                        isReadingNumber = false;
                        result += Integer.parseInt(arg.substring(currentNumberStart, j));
                    }
                } else if (!isReadingNumber) {
                    isReadingNumber = true;
                    currentNumberStart = j;
                }
            }
        }

        System.out.println(result);
    }
}
