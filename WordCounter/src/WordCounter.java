import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Bashir Ali
 *
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * Comparator class used to sort strings alphabetically.
     *
     * @param o1
     *            The first string to be compared
     * @param o2
     *            The second string to be compared
     * @return A negative number if o1 is 'less than' o2, positive if o1 is
     *         'greater than' o2, and zero if o1 and o2 are 'equal'
     */
    private static class Sort implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
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
     * Words are also put into a queue and sorted by alphabetical order
     *
     * @param input
     *            The SimpleReader to read input from.
     * @param map
     *            The Map to populate with key-value pairs.
     * @requires format Term and previous definition to be separated by a single
     *           line.
     * @return A sorted Queue containing the keys from the Map.
     */
    public static Queue<String> getKeysAndValues(SimpleReader input,
            Map<String, Integer> m) {
        // empty queue for keys to be stored in
        Queue<String> queue = new Queue1L<>();
        Comparator<String> comparator = new Sort();

        // set of separator
        Set<Character> separators = new Set1L<>();
        separators.add(':');
        separators.add(';');
        separators.add('|');
        separators.add('~');
        separators.add('!');
        separators.add(' ');
        separators.add(',');

        /*
         * while not at the end, store a line of the text file as a string.
         * Then, using next word or separator, go through the string assigning
         * words to a map with their amount of occurrences and skipping over
         * separators
         */
        while (!input.atEOS()) {
            int index = 0;
            String line = input.nextLine();
            while (index < line.length()) {
                String word = nextWordOrSeparator(line, index, separators);
                index = index + word.length();
                int occurences = 1;
                if (m.hasKey(word) && !separators.contains(word.charAt(0))) {
                    Map.Pair<String, Integer> pair = m.remove(word);
                    occurences = pair.value();
                    occurences++;
                    m.add(word, occurences);
                } else if (!m.hasKey(word)
                        && !separators.contains(word.charAt(0))) {
                    queue.enqueue(word);
                    m.add(word, occurences);
                }
            }
        }

        // sort the words in the queue
        queue.sort(comparator);
        input.close();
        return queue;
    }

    /**
     * generate output page in HTML containing a table with each word in
     * alphabetic order and the number of times it occurs
     *
     * @param outFile
     *            file to print HTML lines in
     * @param queue
     *            alphabet ordered words in a queue
     * @param map
     *            map containing word and how much it occurs
     */
    public static void generateHTMLPage(SimpleWriter outFile, String inFileName,
            Queue<String> queue, Map<String, Integer> map) {
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println(
                "<title>" + "Words counted in " + inFileName + "</title>");
        outFile.println("</head>");
        outFile.println("<body>");
        outFile.println("<h2>" + "Words counted in " + inFileName + "</h2>");
        outFile.println("<hr />");
        outFile.println("<table border=\"1\">");
        outFile.println("<tr>");
        outFile.println("<th>Words</th>");
        outFile.println("<th>Counts</th>");
        outFile.println("</tr>");

        while (queue.length() != 0) {
            String current = queue.dequeue();
            outFile.println("<tr>");
            outFile.println("<td>" + current + "</td>");
            outFile.println("<td>" + map.value(current) + "</td>");
            outFile.println("</tr>");
        }

        outFile.println("</table>");
        outFile.println("</body>");
        outFile.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter a text file: ");
        String inputFile = in.nextLine();
        out.print("Enter an output file: ");
        String outputFile = in.nextLine();

        SimpleReader fileIn = new SimpleReader1L(inputFile);
        SimpleWriter fileOut = new SimpleWriter1L(outputFile);

        Map<String, Integer> map = new Map1L<>();

        Queue<String> queue = getKeysAndValues(fileIn, map);
        generateHTMLPage(fileOut, inputFile, queue, map);

        in.close();
        out.close();
    }

}
