import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Tag cloud generator program using normal java components.
 *
 * @author Bashir Ali and Kwasi Fosu
 *
 */
public final class TagCloudGeneratorWithJavaComponents {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorWithJavaComponents() {
    }

    /**
     * font variables.
     */
    private static final int MAX_FONT = 48;
    /**
     * font variables.
     */
    private static final int MIN_FONT = 11;

    /**
     * set of separators.
     */
    private static final Set<Character> SEPARATORS = createSeparatorsSet();

    /**
     * @return separator set.
     */
    private static Set<Character> createSeparatorsSet() {
        Set<Character> separators = new HashSet<Character>();
        separators.add(':');
        separators.add(';');
        separators.add('|');
        separators.add('~');
        separators.add('!');
        separators.add(' ');
        separators.add('.');
        separators.add(',');
        separators.add('[');
        separators.add(']');
        separators.add('{');
        separators.add('}');
        separators.add('-');
        separators.add('/');
        separators.add('*');
        separators.add('\'');
        separators.add('\t');
        separators.add('\n');
        separators.add('\r');
        return separators;
    }

    /**
     * Comparator class used to sort strings alphabetically.
     */
    private static class SortKey
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getKey().toLowerCase()
                    .compareTo(o2.getKey().toLowerCase());
        }
    }

    /**
     * Comparator class used to sort strings alphabetically.
     */
    private static class SortValue
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return Integer.compare(o2.getValue(), o1.getValue());
        }
    }

    /**
     * Returns the next word or separator from the text starting from the
     * specified position.
     *
     * @param text
     *            The text to get the next word or separator from
     * @param position
     *            The starting position to search for the next word or separator
     * @param separators
     *            A set of symbols considered as separators
     * @return The next word or separator found in the text starting from the
     *         given position.
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position <= text.length() : "Violation of: position < |text|";
        int i = position;
        int n = text.length();

        // Find the end of the word or separator
        if (!separators.contains(text.charAt(i))) {
            while (i < n && !separators.contains(text.charAt(i))) {
                i++;
            }
        } else {
            while (i < n && separators.contains(text.charAt(i))) {
                i++;
            }
        }

        return text.substring(position, i);

    }

    /**
     * Reads each word and adds it to a map with corresponding occurrences.
     *
     * @param input
     *            The BufferedReader to read input from.
     * @param m
     *            The Map to populate with key-value pairs.
     * @requires format Term and previous definition to be separated by a single
     *           line.
     */
    public static void getKeysAndValues(BufferedReader input,
            Map<String, Integer> m) throws IOException {
        /*
         * puts a word and its occurrences into a map as a pair
         */
        String line = input.readLine();
        while (line != null) {
            int index = 0;
            while (index < line.length()) {
                String word = nextWordOrSeparator(line, index, SEPARATORS)
                        .toLowerCase();
                index = index + word.length();
                int occurences = 1;
                if (m.containsKey(word)
                        && !SEPARATORS.contains(word.charAt(0))) {
                    occurences = m.get(word) + 1;
                }
                m.put(word, occurences);
            }
            line = input.readLine();
        }
    }

    /**
     * generate output page in HTML containing a table with each word in
     * alphabetic order and the number of times it occurs.
     *
     * @param outFile
     *            file to print HTML lines in
     * @param inFileName
     *            name of the input file
     * @param list
     *            empty list to populate with ordered keys
     * @param m
     *            map containing word and how much it occurs
     * @param n
     *            amount of words to print
     */
    public static void generateHTMLPage(PrintWriter outFile, String inFileName,
            List<Map.Entry<String, Integer>> list, Map<String, Integer> m,
            int n) throws IOException {
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println("<title>" + "Top " + n + " words in " + inFileName
                + "</title>");
        outFile.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        outFile.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        outFile.println("</head>");
        outFile.println("<body>");
        outFile.println("<h2 style='color: blue;'>" + "Top " + n + " words in "
                + inFileName + "</h2>");
        outFile.println("<div class=\"cdiv\">");
        outFile.println("<p class=\"cbox\">");

        //sort the pairs in a list by value
        Comparator<Map.Entry<String, Integer>> compareValue = new SortValue();
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            list.add(entry);
        }
        Collections.sort(list, compareValue);

        //remove all the pairs until list is same as n
        while (list.size() > n) {
            list.remove(list.size() - 1);
        }

        //sort alphabetically and find max and min counts
        Comparator<Map.Entry<String, Integer>> compareKey = new SortKey();
        int minCount = Integer.MAX_VALUE;
        int maxCount = 0;
        for (Map.Entry<String, Integer> current : list) {
            if (current.getValue() < minCount) {
                minCount = current.getValue();
            }
            if (current.getValue() > maxCount) {
                maxCount = current.getValue();
            }
        }
        Collections.sort(list, compareKey);

        //print each word in the list
        for (Map.Entry<String, Integer> current : list) {
            int occurences = ((MAX_FONT - MIN_FONT)
                    * (current.getValue() - minCount) / (maxCount - minCount))
                    + MIN_FONT;
            outFile.println("<span style=\"cursor:default\" class=\"f"
                    + occurences + "\" title=\"count: " + current.getValue()
                    + "\">" + current.getKey() + "</span>");
        }
        outFile.println("</p>");
        outFile.println("</div>");
        outFile.println("</body>");
        outFile.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter a text file: ");
        String inputFile = in.nextLine();
        System.out.print("Enter an output file: ");
        String outputFile = in.nextLine();
        int n = -1;
        while (n < 0) {
            try {
                System.out.print(
                        "Enter how many words from the text file you want to print: ");
                n = Integer.parseInt(in.nextLine());
                if (n < 0) {
                    System.out.println(
                            "No negative numbers. Enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Must type in an integer.");
                in.close();
                return;
            }
        }

        in.close();

        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.err
                    .println("Error: Program error while opening input file.");
        }

        Map<String, Integer> map = new HashMap<>();
        if (fileIn != null) {
            try {
                getKeysAndValues(fileIn, map);
            } catch (IOException e) {
                System.err.println(
                        "Error: Program error while reading input file.");
            }
            try {
                fileIn.close();
            } catch (IOException e) {
                System.err.println(
                        "Error: Program error while closing input file.");
            }
        }

        PrintWriter fileOut = null;
        try {
            fileOut = new PrintWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            System.err
                    .println("Error: Program error while opening output file.");
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        if (fileOut != null) {
            try {
                generateHTMLPage(fileOut, inputFile, list, map, n);
            } catch (IOException e) {
                System.err.println(
                        "Error: Program error while reading input file.");
            }
            fileOut.close();
        }
    }
}
