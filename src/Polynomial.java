import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class implements methods for polynomials calculations
 * <b>Coefficients starts from x in power of 0</b>.
 * */
public class Polynomial {
    private double[] coefficients;

    /**
     * Instantiates a new Polynomial. <b>Coefficients starts from x in power of 0</b>.
     * For example, new Polynomial(4, 5, 6) will be equal to 4 + 5x + 6x^2
     *
     * @param coefficients the coefficients
     */
    public Polynomial(double ...coefficients) {
        this.coefficients = new double[coefficients.length];
        System.arraycopy(coefficients, 0, this.coefficients, 0, coefficients.length);
    }

    /**
     * Gets coefficient by power of x.
     *
     * @param powerOfX the power of x
     * @return the coefficient by power of x
     */
    public double getCoefficientByPowerOfX(int powerOfX) {
        return coefficients[powerOfX];
    }

    /**
     * Returns array of coefficients.
     *
     * @return the double [ ]
     */
    public double[] getCoefficients() {
        return coefficients;
    }

    public int getPolynomialLength() {
        return this.coefficients.length;
    }

    public int getMaximumPowerOfX() {
        for(int i = coefficients.length-1; i >= 0; i--) {
            if(coefficients[i] != 0) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Subtract given polynomial from <b>this</b>.
     *
     * @param polynomial the polynomial
     */
    public void subtract(Polynomial polynomial) {
        this.coefficients = subtractPolynomials(this, polynomial).getCoefficients();
    }

    /**
     * Multiply given polynomial by <b>this</b>.
     *
     * @param polynomial the polynomial
     */
    public void multiplyPolynomial(Polynomial polynomial) {
        this.coefficients = multiplyTwoPolynomials(this, polynomial).getCoefficients();
    }

    /**
     * Adding given polynomial to <b>this</b>.
     *
     * @param polynomial the polynomial
     */
    public void add(Polynomial polynomial) {
        this.coefficients = addPolynomials(this, polynomial).getCoefficients();
    }

    /**
     * Adding given real number to polynomial.
     *
     * @param num the num
     */
    public void addRealNumber(double num) {
        coefficients[0] += num;
    }

    /**
     * Multiply polynomial by given real number.
     *
     * @param num the num
     */
    public void multiplyByRealNumber(double num) {
        for(int i = 0; i < coefficients.length; i++) {
            coefficients[i] *= num;
        }
    }

    /**
     * Calculates value of polynomial for given x.
     *
     * @param x the x
     * @return result
     */
    public double calculateForX(double x) {
        AtomicInteger ind = new AtomicInteger(0);
        return Arrays.stream(coefficients)
                .map(c -> c*Math.pow(x, ind.getAndIncrement()))
                .sum();
    }

    /**
     * Calculates sum of given Polynomials into the new one.
     *
     * @param polynomials the Polynomials
     * @return the Polynomial
     */
    public static Polynomial addPolynomials(Polynomial... polynomials) {
        double[] newCoefs = new double[Arrays.stream(Arrays.stream(polynomials)
                .map(Polynomial::getPolynomialLength)
                .mapToInt(x -> x).toArray()).max().orElse(0)];

        for(int i = 0; i < newCoefs.length ; i++) {
            int counter = 0;
            for (Polynomial polynomial : polynomials) {
                if(i >= polynomial.getPolynomialLength())
                    continue;
                counter += polynomial.coefficients[i];
            }
            newCoefs[i] = counter;
        }
        return new Polynomial(newCoefs);
    }

    /**
     * Subtract given polynomials and returns result as a new Polynomial.
     *
     * @param polynomials the polynomials
     * @return the polynomial
     */
    public static Polynomial subtractPolynomials(Polynomial... polynomials) {
        double[] newCoefs = new double[Arrays.stream(Arrays.stream(polynomials)
                .map(Polynomial::getPolynomialLength)
                .mapToInt(x -> x).toArray())
                .max()
                .orElse(0)];

        for(int i = 0; i < newCoefs.length ; i++) {
            int counter = 0;
            for (int j = 0; j < polynomials.length; j++) {
                if(i >= polynomials[j].getPolynomialLength())
                    continue;
                if(j == 0)
                    counter += polynomials[j].coefficients[i];
                else
                    counter -= polynomials[j].coefficients[i];
            }
            newCoefs[i] = counter;
        }
        return new Polynomial(newCoefs);
    }

    /**
     * Multiply given polynomials and returns result as a new Polynomial.
     *
     * @param polynomials the polynomials
     * @return the polynomial
     */
    public static Polynomial multiplyPolynomials(Polynomial...polynomials) {
        Polynomial polynomial = polynomials[0];
        for(int i = 1; i < polynomials.length; i++) {
            polynomial = multiplyTwoPolynomials(polynomial, polynomials[i]);
        }
        return polynomial;
    }

    /**
     * Multiply two given polynomials and returns result as a new Polynomial.
     *
     * @param polynomial1 first polynomial
     * @param polynomial2 second polynomial
     * @return the polynomial
     */
    public static Polynomial multiplyTwoPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial maxPolynomial = polynomial1;
        Polynomial minPolynomial = polynomial2;
        if(polynomial2.getPolynomialLength() > polynomial1.getPolynomialLength() ) {
            minPolynomial = polynomial1;
            maxPolynomial = polynomial2;
        }
        double[] newCoefficients = new double[maxPolynomial.getMaximumPowerOfX() + minPolynomial.getMaximumPowerOfX()+1];

        for(int i = 0; i < maxPolynomial.getPolynomialLength(); i++) {
            for(int j = 0; j < minPolynomial.getPolynomialLength(); j++) {
                newCoefficients[i+j] += maxPolynomial.coefficients[i] * minPolynomial.coefficients[j];
            }
        }
        return new Polynomial(newCoefficients);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Polynomial: ");
        for(int i = getPolynomialLength()-1, pow = getMaximumPowerOfX(); i >= 0; i--) {
            if(coefficients[i] != 0) {
                string.append(coefficients[i] > 0 && i != getPolynomialLength()-1 ? "+" + coefficients[i] : coefficients[i]);
                string.append(pow == 0 ? "" : pow == 1 ? "x" : "x^" + pow);
                pow--;
            }
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Polynomial polynomial) {
            return Arrays.equals(polynomial.getCoefficients(), this.coefficients);
        }
        return false;
    }
}
