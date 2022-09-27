public class SumLongOctal {
    public static void main(String[] args) {
        long result = 0;

        for (int i = 0; i < args.length; i++) {
            int j = 0;

            while (j < args[i].length()) {
                if (Character.isWhitespace(args[i].charAt(j))) {
                    j++;
                    continue;
                }

                int currentNumberEnd = j;
                while (currentNumberEnd < args[i].length() - 1 && !Character.isWhitespace(args[i].charAt(currentNumberEnd + 1))) {
                    currentNumberEnd++;
                }

                if (Character.toLowerCase(args[i].charAt(currentNumberEnd)) == 'o') {
                    result += Long.parseLong(args[i].substring(j, currentNumberEnd), 8);
                } else {
                    result += Long.parseLong(args[i].substring(j, currentNumberEnd + 1));
                }

                j = currentNumberEnd + 1;
            }
        }

        System.out.println(result);
    }
}
