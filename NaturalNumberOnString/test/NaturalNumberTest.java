import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     *
     * Creates and returns a {@code NaturalNumber} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the string
     * @return the constructed NaturalNumber
     * @ensures createFromArgsTest = [entries in args]
     */
    private NaturalNumber createFromArgsTest(String... args) {
        NaturalNumber n = this.constructorTest();
        for (String s : args) {
            n.multiplyBy10(Integer.parseInt(s));
        }
        return n;
    }

    /**
     *
     * Creates and returns a {@code NaturalNumber} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the string
     * @return the constructed NaturalNumber
     * @ensures createFromArgsTest = [entries in args]
     */
    private NaturalNumber createFromArgsRef(String... args) {
        NaturalNumber n = this.constructorRef();
        for (String s : args) {
            n.multiplyBy10(Integer.parseInt(s));
        }
        return n;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(3);
        NaturalNumber nExpected = this.constructorRef(3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntArgumentConstructorUsingIntMax() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("3");
        NaturalNumber nExpected = this.constructorRef("3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringArgumentConstructorWithLargeNumber() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("12345678987654321");
        NaturalNumber nExpected = this.constructorRef("12345678987654321");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber three = new NaturalNumber1L(3);
        NaturalNumber n = this.constructorTest(three);
        NaturalNumber nExpected = this.constructorRef(three);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNArgumentConstructorWithLargeNumber() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber x = new NaturalNumber1L(123456789);
        NaturalNumber n = this.constructorTest(x);
        NaturalNumber nExpected = this.constructorRef(x);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void isZeroNoArgsConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest();
        boolean nExpected = true;
        boolean n = zero.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void isZeroWithArgs() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest("0");
        boolean nExpected = false;
        boolean n = zero.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10OnZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        n.multiplyBy10(3);
        NaturalNumber nExpected = this.constructorRef(3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10OnSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(7);
        n.multiplyBy10(3);
        NaturalNumber nExpected = this.constructorRef(73);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10OnDoubleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(77);
        n.multiplyBy10(3);
        NaturalNumber nExpected = this.constructorRef(773);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void divideyBy10OnZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        int i = n.divideBy10();
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(0, i);
    }

    @Test
    public final void divideyBy10OnSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(7);
        int i = n.divideBy10();
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(7, i);
    }

    @Test
    public final void divideyBy10OnDoubleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(70);
        int i = n.divideBy10();
        NaturalNumber nExpected = this.constructorRef(7);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(0, i);
    }
}
