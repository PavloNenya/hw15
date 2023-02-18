import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PolynomialTest {

    @Test
    @Order(1)
    void addTwoPolynomials() {
        //Arrange
        double[] expected = new double[] {17, 0, 7};
        Polynomial polynomial1 = new Polynomial(10, -1, 1);
        Polynomial polynomial2 =  new Polynomial(7, 1, 6);

        //Act
        Polynomial result = Polynomial.addPolynomials(polynomial1, polynomial2);

        //Assert
        assertArrayEquals(expected, result.getCoefficients());                            //10+7 = 17
    }

    @Test
    @Order(2)
    void multiplyTwoPolynomials() {
        //Arrange
        double[] coeffs = {15, 19, 14, 40};
        Polynomial polynomial1 = new Polynomial(3, 5);                      //3 + 5x
        Polynomial polynomial2 = new Polynomial(5, -2, 8);                  //5 - 2x + 8

        //Act
        Polynomial res = Polynomial.multiplyTwoPolynomials(polynomial1, polynomial2);  //15 + 19x + 14x^2 + 40x^3

        //Assert
        assertArrayEquals(coeffs, res.getCoefficients());
    }

    @Test
    @Order(3)
    void multiplyPolynomials() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 5);                      // 3 +  5x
        Polynomial polynomial2 = new Polynomial(5, -2, 8);                  // 5 -  2x +  8x^2
        Polynomial polynomial3 = new Polynomial(-3, 3, 12, 4);              //-3 -  3x - 12x^2 + 4x^3
        Polynomial polynomial4 = new Polynomial(3, 5);                      // 3 +  5x
        Polynomial polynomial5 = new Polynomial(5, 0, 8);                   // 5 +        8x^2
        Polynomial polynomial6 = new Polynomial(0, 3, 12, 4);               //   -  3x - 12x^2 + 4x^3

        //Act
        Polynomial res1 = Polynomial.multiplyPolynomials(
                polynomial1,
                polynomial2,
                polynomial3);                                   // -45 -12x +195x^2 + 210x^3 + 364x^4 + 536x^5 +160x^5
        Polynomial res2 = Polynomial.multiplyPolynomials(
                polynomial4,
                polynomial5,
                polynomial6);                                   // 45x + 255x^2 + 432x^3 + 508x^4 + 576x^5 +160x^6

        //Assert
        assertArrayEquals(new double[] {-45, -12, 195, 210, 364, 536, 160}, res1.getCoefficients() );
        assertArrayEquals(new double[] {0, 45, 255, 432, 508, 576, 160}, res2.getCoefficients());
    }

    @Test
    @Order(4)
    void subtractTwoPolynomials() {
        //Arrange
        double[] resCoeffs;

        //Act
        resCoeffs = Polynomial.subtractPolynomials(
                new Polynomial(10, 1, 1),
                new Polynomial(7, -1, 6))
                .getCoefficients();

        //Assert
        assertArrayEquals(resCoeffs, new double[]{3, 2, -5});
    }

    @Test
    @Order(5)
    void subtractPolynomials() {
        //Arrange
        double[] resCoeffs = Polynomial.subtractPolynomials(
                new Polynomial(10, 1, 1),
                new Polynomial(7, -1, 6),
                new Polynomial(2, 6, 10),
                new Polynomial(2, -6, 6),
                new Polynomial(2, 6, 10),
                new Polynomial(2, 3, 6)
        ).getCoefficients();

        //Assert
        assertArrayEquals(resCoeffs, new double[]{-5, -7, -37});
    }

    @Test
    @Order(6)
    void equality() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(5, 2, 3);
        Polynomial polynomial2 = new Polynomial(5, 2, 3);

        //Assert
        assertEquals(polynomial1, polynomial2);
    }

    @Test
    @Order(7)
    void calculateForX() {
        //Arrange
        double x = 4.5;
        Polynomial polynomial = new Polynomial(3, 5, 1);        //3 + 5x + x^2 = 3 + 5*4.5  + 4.5^2

        //Act
        double res = polynomial.calculateForX(x);

        //Assert
        assertEquals(45.75, res);
    }

    @Test
    @Order(8)
    void add() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 4, 2);       //3 + 4x + 2x^2
        Polynomial polynomial2 = new Polynomial(1, 2, 3, 4);    //1 + 2x + 3x^2 + 4x^3

        //Act
        polynomial1.add(polynomial2);                                      //4 + 6x + 5x^2 + 4x^3
        double[] res = polynomial1.getCoefficients();
        double[] expected = {4, 6, 5, 4};

        //Assert
        assertArrayEquals(expected, res);
    }

    @Test
    @Order(9)
    void subtract() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 4, 2);       //3 + 4x + 2x^2
        Polynomial polynomial2 = new Polynomial(1, 2, 3, 4);    //1 + 2x + 3x^2 + 4x^3

        //Act
        polynomial1.subtract(polynomial2);                                 //2 + 2x - 1x^2 - 4x^3
        double[] res = polynomial1.getCoefficients();
        double[] expected = {2, 2, -1, -4};

        //Assert
        assertArrayEquals(expected, res);
    }

    @Test
    @Order(10)
    void multiplyPolynomial() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 4, 2);       //3 + 4x + 2x^2
        Polynomial polynomial2 = new Polynomial(1, 2, 3, 4);    //1 + 2x + 3x^2 + 4x^3

        //Act
        polynomial1.multiplyPolynomial(polynomial2);                       //2 + 2x - 1x^2 - 4x^3
        double[] res = polynomial1.getCoefficients();
        double[] expected = {3, 10, 19, 28, 22, 8};

        //Assert
        assertArrayEquals(expected, res);
    }


    @Test
    @Order(11)
    void addRealNumber() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 4, 2);       //3 + 4x + 2x^2

        //Act
        polynomial1.addRealNumber(14.5);                                   //17.5 + 4x + 2x^2
        double[] res = polynomial1.getCoefficients();
        double[] expected = {17.5, 4, 2};

        //Assert
        assertArrayEquals(expected, res);
    }

    @Test
    @Order(12)
    void multiplyByRealNumber() {
        //Arrange
        Polynomial polynomial1 = new Polynomial(3, 4, 2);       //3 + 4x + 2x^2

        //Act
        polynomial1.multiplyByRealNumber(2.5);                             //7.5 + 10x + 5x^2
        double[] res = polynomial1.getCoefficients();
        double[] expected = {7.5, 10, 5};

        //Assert
        assertArrayEquals(expected, res);
    }
}