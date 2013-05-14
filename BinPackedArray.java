import java.util.List;
import java.io.ByteArrayOutputStream;

public class BinPackedArray
{
    public static final String CHARSET = "UTF-8";

    private int[] indexes = null;

    private byte[] bytes = null;

    public BinPackedArray(List<String> list) throws Exception
    {
        int[] indexes = new int[list.size() + 1];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0, len = list.size(); i < len; ++i) {
            indexes[i] = out.size();
            out.write(encode(list.get(i)));
        }
        indexes[list.size()] = out.size();
        this.indexes = indexes;
        this.bytes = out.toByteArray();
    }

    public BinPackedArray(BinPackedArray array) throws Exception
    {
        int size = array.size();
        int[] indexes = new int[size + 1];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0, len = size; i < len; ++i) {
            indexes[i] = out.size();
            out.write(encode(array.get(i)));
        }
        indexes[size] = out.size();
        this.indexes = indexes;
        this.bytes = out.toByteArray();
    }

    public String get(int index) throws Exception
    {
        return decode(this.bytes, indexes[index], indexes[index + 1]);
    }

    public static String decode(byte[] b, int start, int end) throws Exception
    {
        return new String(b, start, end - start, CHARSET);
    }

    public static byte[] encode(String str) throws Exception
    {
        return str.getBytes(CHARSET);
    }

    public int size()
    {
        return this.indexes.length - 1;
    }
}
