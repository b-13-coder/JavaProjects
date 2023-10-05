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
public final class Newton3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        double r = x;
        double condition = Math.abs((r * r) - x) / 2;
        // variable to check if x is not 0
        double check = 0.00001;
        //if statement to check if the double is not 0
        if (x > check || x < check) {
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
        if (answer.equals("y") || answer.equals("Y")) {
            //while user continues to enter y
            while (answer.equals("y") || answer.equals("Y")) {
                out.print("Enter a number: ");
                double num = in.nextDouble();
                out.print("Enter a value for epsilon: ");
                double num2 = in.nextDouble();
                double squareRoot = sqrt(num, num2);
                out.println("The square root of that number is approx. "
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
