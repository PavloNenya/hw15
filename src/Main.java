public class Main {
    public static void main(String[] args) {
        Polynomial polynomial1 = new Polynomial(3, 5);                      // 3 + 5x
        Polynomial polynomial2 = new Polynomial(5, -2, 8);                  // 5 - 2x + 8x^2
        Polynomial res = Polynomial.addPolynomials(polynomial1, polynomial2);          // 8 + 3x + 8x^2
        System.out.println(res);


        Polynomial polynomial3 = new Polynomial(5);
        Polynomial polynomial4 = Polynomial.multiplyTwoPolynomials(polynomial1, polynomial2);

        polynomial3.multiplyPolynomial(polynomial4);
        System.out.println(polynomial3);
    }

}