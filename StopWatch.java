public class StopWatch
{
    private static String lastLabel;
    private static long lastStart;

    public static void begin(String label)
    {
        lastLabel = label;
        lastStart = System.nanoTime();

    }

    public static void end()
    {
        long time = System.nanoTime() - lastStart;
        System.out.format("(%2$3d.%3$09d) %1$s\n", lastLabel,
                time / 1000000000L, time % 1000000000L);
    }
}
