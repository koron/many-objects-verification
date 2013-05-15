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

    public static List<String> newList(List<String> src) throws Exception
    {
        int len = src.size();
        ArrayList<String> list = new ArrayList<String>(len);
        for (int i = 0; i < len; ++i) {
            list.add(i, src.get(i));
        }
        return list;
    }

    public static List<String> newList(BinPackedArray src) throws Exception
    {
        int len = src.size();
        ArrayList<String> list = new ArrayList<String>(len);
        for (int i = 0; i < len; ++i) {
            list.add(i, src.get(i));
        }
        return list;
    }

    public static BinPackedArray newArray(List<String> src) throws Exception
    {
        return new BinPackedArray(src);
    }

    public static BinPackedArray newArray(BinPackedArray src) throws Exception
    {
        return new BinPackedArray(src);
    }

    public static void prompt(String msg) throws Exception
    {
        System.out.print(msg);
        System.out.print(" >");
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        reader.readLine();
    }

    public static void trial(int n) throws Exception
    {
        StopWatch.begin("list to list conversion #" + n);
        currList = newList(currList);
        StopWatch.end();
        StopWatch.begin("list to list after GC #" + n);
        System.gc();
        StopWatch.end();

        StopWatch.begin("list to array conversion #" + n);
        currArray = newArray(currList);
        StopWatch.end();
        StopWatch.begin("list to array after GC #" + n);
        currList = null;
        System.gc();
        StopWatch.end();

        StopWatch.begin("array to array conversion #" + n);
        currArray = newArray(currArray);
        StopWatch.end();
        StopWatch.begin("array to array after GC #" + n);
        System.gc();
        StopWatch.end();

        StopWatch.begin("array to list conversion #" + n);
        currList = newList(currArray);
        StopWatch.end();
        StopWatch.begin("array to list after GC #" + n);
        currArray = null;
        System.gc();
        StopWatch.end();
    }

    public static void main(String[] args) throws Exception
    {
        int number = 10000000;
        int len = 32;

        StopWatch.begin("prepare list");
        currList = newStringList(number, len);
        StopWatch.end();
        StopWatch.begin("prepare after");
        System.gc();
        StopWatch.end();

        //prompt("Waiting to start");

        for (int i = 1; i <= 5; ++i) {
            System.out.println("");
            trial(i);
        }
    }
}
