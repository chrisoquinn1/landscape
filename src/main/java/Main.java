import java.util.Arrays;

public class Main {

    public static void main(final String[] args) {
        if (areValid(args)) {
            final int[] commaSeparatedNumbers = getCommaSeparatedNumbers(args[0]);
            final LandscapeCalculator landscapeCalculator = new LandscapeCalculator();
            System.out.println(landscapeCalculator.calculateWaterAmount(commaSeparatedNumbers));

        }
        else{
            System.out.println("Arguments are not valid. There should be one argument. Arguments: " + Arrays.toString(args));
            System.out.println("Example: java Main \"1,2,3,4,5\"");

        }


    }

    /**
     *
     * @param args program args
     * @return true if args is valid
     */
    private static boolean areValid(final String[] args) {
        return args != null && args.length == 1 && isCommaSeparatedNumbers(args[0]);

    }

    /**
     *
     * @param arg arg
     * @return true if arg is comma separated list of numbers
     */
    private static boolean isCommaSeparatedNumbers(final String arg) {
        return getCommaSeparatedNumbers(arg).length > 0;
    }

    /**
     *
     * @param arg arg
     * @return array from comma separated number string
     */
    private static int[] getCommaSeparatedNumbers(final String arg) {
        final String[] split = arg.split(",");
        int[] result = new int[split.length];
        try {
            for (int i = 0; i < split.length; i++) {
                result[i] = Integer.parseInt(split[i]);
            }
        } catch (final NumberFormatException n) {
            result = new int[0];
        }
        return result;
    }

}
