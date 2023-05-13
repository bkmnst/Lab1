import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class BankAccount {
    String surname;
    String name;
    String phone;
    String iban;
    double balance;
    String currency;

    public BankAccount(String surname, String name, String phone, String iban, double balance, String currency) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
    }

    public static BankAccount fromString(String s) {
        String[] fields = s.split(";");
        return new BankAccount(fields[0], fields[1], fields[2], fields[3], Double.parseDouble(fields[4]), fields[5]);
    }
}

public class SearchMethods {
    public static int linearSearch(BankAccount[] accounts, String key) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].surname.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearch(BankAccount[] accounts, String key) {
        int low = 0;
        int high = accounts.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = accounts[mid].surname.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int interpolationSearch(BankAccount[] accounts, String key) {
        int low = 0;
        int high = accounts.length - 1;

        while (low <= high && key.compareTo(accounts[low].surname) >= 0 && key.compareTo(accounts[high].surname) <= 0) {
            if (low == high) {
                if (accounts[low].surname.equals(key)) return low;
                return -1;
            }

            int pos = low + ((key.compareTo(accounts[low].surname) * (high - low)) /
                    (accounts[high].surname.compareTo(accounts[low].surname)));

            if (accounts[pos].surname.equals(key))
                return pos;

            if (accounts[pos].surname.compareTo(key) < 0)
                low = pos + 1;

            else
                high = pos - 1;
        }
        return -1;
    }

    public static int fibonacciSearch(BankAccount[] accounts, String key) {
        int fibMMm2 = 0;
        int fibMMm1 = 1;
        int fibM = fibMMm2 + fibMMm1;

        while (fibM < accounts.length) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM = fibMMm2 + fibMMm1;
        }

        int offset = -1;

        while (fibM > 1) {
            int i = Math.min(offset + fibMMm2, accounts.length - 1);

            if (accounts[i].surname.compareTo(key) < 0) {
                fibM = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            } else if (accounts[i].surname.compareTo(key) > 0) {
                fibM = fibMMm2;
                fibMMm1 -= fibMMm2;
                fibMMm2 -= fibM;
            } else return i;
        }

        if(fibMMm1 == 1 && accounts[offset+1].surname.equals(key))
            return offset+1;

        return -1;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("bank_accounts.txt"));
        BankAccount[] accounts = new BankAccount[lines.size()];
        for (int i=0; i<lines.size(); i++) {
            accounts[i] = BankAccount.fromString(lines.get(i));
        }


        long startTimeLinearSearch=System.nanoTime();

        System.out.println(linearSearch(accounts,"Surname8"));

        long endTimeLinearSearch=System.nanoTime();

        long timeElapsedLinearSearch=endTimeLinearSearch-startTimeLinearSearch;

        System.out.println("Execution time in nanoseconds: " + timeElapsedLinearSearch);
        System.out.println("Execution time in milliseconds: " + timeElapsedLinearSearch / 1000000);


        Arrays.sort(accounts,(a,b)->a.surname.compareTo(b.surname));

        long startTimeBinarySearch=System.nanoTime();

        System.out.println(binarySearch(accounts,"Surname8"));

        long endTimeBinarySearch=System.nanoTime();

        long timeElapsedBinarySearch=endTimeBinarySearch-startTimeBinarySearch;

        System.out.println("Execution time in nanoseconds: " + timeElapsedBinarySearch);
        System.out.println("Execution time in milliseconds: " + timeElapsedBinarySearch / 1000000);


        long startTimeInterpolationSearch=System.nanoTime();

        System.out.println(interpolationSearch(accounts,"Surname8"));

        long endTimeInterpolationSearch=System.nanoTime();

        long timeElapsedInterpolationSearch=endTimeInterpolationSearch-startTimeInterpolationSearch;

        System.out.println("Execution time in nanoseconds: " + timeElapsedInterpolationSearch);
        System.out.println("Execution time in milliseconds: " + timeElapsedInterpolationSearch / 1000000);


        long startTimeFibonacciSearch=System.nanoTime();

        System.out.println(fibonacciSearch(accounts,"Surname8"));

        long endTimeFibonacciSearch=System.nanoTime();

        long timeElapsedFibonacciSearch=endTimeFibonacciSearch-startTimeFibonacciSearch;

        System.out.println("Execution time in nanoseconds: " + timeElapsedFibonacciSearch);
        System.out.println("Execution time in milliseconds: " + timeElapsedFibonacciSearch / 1000000);


    }
}
