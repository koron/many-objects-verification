import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ManyObjects
{
    public static final String CHARS =
        "0123456789_-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final int CHARS_LEN = CHARS.length();

    private static final Random RAND = new Random();

    private static List<String> currList = null;

    private static BinPackedArray currArray = null;

    private static BufferedReader reader = null;

    public static String newRandomString(int len)
    {
        StringBuilder s = new StringBuilder(len);
        for (int i = 0; i < len; ++i) {
            int n = RAND.nextInt(CHARS_LEN);
            s.append(CHARS.charAt(n));
        }
        return s.toString();
    }

    public static List<String> newStringList(int num, int len)
    {
        ArrayList<String> list = new ArrayList<String>(num);
        for (int i = 0; i < num; ++i) {
            list.add(newRandomString(len));
        }
        return list;
    }

    public static void prompt(String prompt) throws Exception
    {
        System.out.print(prompt);
        System.out.print(" >");
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        reader.readLine();
    }

    public static void main(String[] args) throws Exception
    {
        int number = 10000000;
        int len = 32;

        prompt("currList = newStringList()");
        currList = newStringList(number, len);
        prompt("System.gc();");
        System.gc();
        prompt("currArray = new BinPackedArray(currList)");
        currArray = new BinPackedArray(currList);
        prompt("System.gc();");
        System.gc();
        prompt("currList = null; System.gc();");
        currList = null;
        System.gc();
        prompt("currArray = new BinPackedArray(currArray)");
        currArray = new BinPackedArray(currArray);
        prompt("System.gc();");
        System.gc();
        prompt("return;");
    }
}
