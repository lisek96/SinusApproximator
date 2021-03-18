public class SinusApproximator {
    long[] factorialData;

    SinusApproximator() {
        factorialData = new long[20];
    }


    public void approximateSinus_InputDegrees(double degrees) {
        double x = transformX(degrees);
        approximateSinus_InputRadians(x);
    }

    public void approximateSinus_InputRadians(double radians) {
        if (radians > Math.PI / 2) {
            double degrees = radiansToMathDegrees(radians);
            radians = transformX(degrees);
        }
        double[] words = new double[10];
        double javaMathSinus = getSinus(radians);
        double[] powersOfX = getPowers(radians);
        for (int i = 1, j = 0; i <= 20; i += 2, j++) {
            long factorial = getFactorial(i);
            double powerOfX = powersOfX[i];
            words[j] = powerOfX/factorial;
        }
        double approxSinusValue = 0;
        for (int j = 0; j < words.length; j++) {
            if (j % 2 == 0)
                approxSinusValue += words[j];
            else
                approxSinusValue -= words[j];

            printResults(j, approxSinusValue, javaMathSinus);
        }
    }

    double mathDegreesToRadians(double degrees) {
        return (degrees * (2 * Math.PI)) / 360;
    }

    double radiansToMathDegrees(double radians) {
        return 360 * radians / (2 * Math.PI);
    }

    double getSinus(double x) {
        return Math.sin(x);
    }



    double transformX(double degrees) {
        int z = (int) degrees / 360;
        degrees = degrees - z * 360;
        double x = mathDegreesToRadians(degrees);
        if (degrees <= 90) return x;
        if (degrees <= 180) return Math.PI - x;
        if (degrees <= 270) return -(x - Math.PI);
        return -(2 * Math.PI - x);
    }

    long getFactorial(int n) {
        if (n <= 1) return 1;
        if (factorialData[n] != 0) {
            return factorialData[n];
        }
        long factorial = n * getFactorial(n - 1);
        factorialData[n] = factorial;
        return factorial;
    }

    private double[] getPowers(double x) {
        double[] powersOfX = new double[20];
        powersOfX[1] = x;
        double xSquared = x * x;
        for (int i = 3; i < 20; i += 2) {
            powersOfX[i] = powersOfX[i - 2] * xSquared;
        }
        return powersOfX;
    }

    private void printResults(int i, double a, double b) {
        System.out.println(i + ": " + "Approx: " + a + "   Math.sin: " + b + " Difference: " + getAbsoluteDifference(a, b));
    }

    private double getAbsoluteDifference(double a, double b) {
        double bigger = a > b ? a : b;
        double smaller = a < b ? a : b;

        double difference =  a * b < 0 ? bigger - smaller : Math.abs(bigger) - Math.abs(smaller);

        return difference < 0 ? -difference : difference;

    }

    public static void main(String[] args) {

        SinusApproximator sinusApproximator = new SinusApproximator();

        //factorialCheck
        System.out.println(sinusApproximator.getFactorial(9));

        //powerCheck
        double[] powersOfX = sinusApproximator.getPowers(2);
        System.out.println(powersOfX[11] + "\n\n\nResult:\n");


        //replace arg1 with math degrees or arg2 with radians to approximately count sinus(arg1)/sinus(arg2)
        final double arg1=350;
        final double arg2=81.90;

        sinusApproximator.approximateSinus_InputDegrees(arg1);
        //sinusApproximator.approximateSinus_InputRadians(arg2);
    }


}