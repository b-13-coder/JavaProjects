import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Bashir Ali Newton iteration program to find square roots
 *
 */
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number including zero to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x;
        double epsilon = 0.0001;
        //condition for checking if sqrt method is in the range
        double condition = Math.abs((r * r) - x) / 2;
        //if statement to check if the double is not 0
        if (x > epsilon || x < (epsilon * -1)) {
            //while double r is still not in the range
            while (condition > (epsilon * epsilon)) {
                r = ((r + (x / r)) / 2);
                condition = Math.abs((r * r) - x) / 2;
            }
        } else {
            r = 0;
        }
        return r;
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
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        out.print("Would you like to calculate a square root? ");
        String answer = in.nextLine();
        //while user continues to put in x
        if (answer.equals("y") || answer.equals("Y")) {
            while (answer.equals("y") || answer.equals("Y")) {
                out.print("Enter a number: ");
                double num = in.nextDouble();
                double squareRoot = sqrt(num);
                out.println("The square root of that number is within 0.01% of "
                        + squareRoot + ".");
                out.print("Would you like to calculate a square root again? ");
                answer = in.nextLine();
            }
        }
        //if they didnt enter y then print
        out.print("Goodbye!");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
