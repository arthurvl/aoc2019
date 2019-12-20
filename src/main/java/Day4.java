import java.util.function.Function;

public class Day4 {
    public static boolean isValidPasswordPartOne(String password) {
        return monotonicallyIncreasing(password) && containsAdjacentEquals(password);
    }

    public static boolean containsAdjacentEquals(String password) {
        char previous = password.charAt(0);
        for (char c : password.substring(1).toCharArray()) {
            if (c == previous) {
                return true;
            }
            previous = c;
        }
        return false;
    }

    public static boolean monotonicallyIncreasing(String password) {
        char seen = '\0';
        for (char c : password.toCharArray()) {
            if (c < seen) {
                return false;
            }
            seen = c;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(getValidPasswords(Day4::isValidPasswordPartOne));
        System.out.println(getValidPasswords(Day4::isValidPasswordPartTwo));
    }

    private static boolean isValidPasswordPartTwo(String password) {
        return isValidPasswordPartOne(password) && containsAdjacentSetOfSize2(password);
    }

    private static int getValidPasswords(Function<String, Boolean> isValid) {
        int validPasswords = 0;
        for (int i = 138241; i <= 674034; i++) {
            String password = String.valueOf(i);
            if (isValid.apply(password)) {
                validPasswords++;
            }
        }
        return validPasswords;
    }

    public static boolean containsAdjacentSetOfSize2(String password) {
        char minus3 = password.charAt(0);
        char minus2 = password.charAt(1);
        char minus1 = password.charAt(2);
        if (minus3 == minus2 && minus1 != minus2) {
            return true;
        }
        for (char c : password.substring(3).toCharArray()) {
            if (minus3 != minus2 && minus2 == minus1 && c != minus1) {
                return true;
            }
            minus3 = minus2;
            minus2 = minus1;
            minus1 = c;
        }
        return minus3 != minus2 && minus2 == minus1;
    }
}
