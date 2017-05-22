public class TimeKeeper {
    public static long sumTime = 0;
    public static long numTimes = 0;

    public static long returnAverage() {
        return sumTime / numTimes;
    }

    public static void reset() {
        sumTime = 0;
        numTimes = 0;
    }
}