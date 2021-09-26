package com.pkg.def;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import com.pkg.awt.DrawTask;
import com.pkg.util.function.ExecutableFunction;

import java.awt.Graphics;
import java.awt.Dimension;

import java.util.List;
import java.util.Map;

public final class Static {
    private Static() {
    }

    /**
     * @return random character
     * @since 1.0
     */

    public static char randomChar() {
        return randomString(1, 2).charAt(0);
    }

    /**
     * @param x
     * @param y
     * @return random number between x and y
     * @since 1.0
     */

    public static double random(double x, double y) {
        return x + Math.random() * (y - x);
    }

    /**
     * @param shortest
     * @param longest
     * @return random String between "shortest" and "longest" length
     * @since 1.0
     */

    public static String randomString(int shortest, int longest) {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = (int) random(shortest, longest);
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }

    /**
     * @param <T>
     * @param arr
     * @return random element in array
     * @since 2.2
     */

    @SafeVarargs
    public static <T> T randomElement(T... arr) {
        return arr[(int) random(0, arr.length - 1)];
    }

    /**
     * @param <K>    key list
     * @param <V>    value list
     * @param keys
     * @param values
     * @return map with keys and values
     * @since 1.8
     */

    public static <K, V> Map<K, V> toMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
    }

    /**
     * @param x
     * @return false if x is null or NaN or 0
     * @since 1.1
     */

    public static boolean bool(Object x) {
        return ((x == (Object) 0) || (x == "") || (x == null) || (x == Constants.NaN)) ? false : true;
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.1
     */

    public static boolean bool(Object[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(byte[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(int[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(short[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(long[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(float[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x has no element
     * @since 1.3
     */

    public static boolean bool(double[] x) {
        return !(x.length == 0 || x == null);
    }

    /**
     * @param x
     * @return false if x is null
     * @since 1.3
     */

    public static boolean bool(Class<?> x) {
        return !(x == null);
    }

    private static List<Byte> h = new ArrayList<Byte>();

    /**
     * @param str
     * @param encoding
     * @return byte list
     * @throws UnsupportedEncodingException
     * @since 1.1
     */

    public static List<Byte> toUTF(String str, Charset encoding) throws UnsupportedEncodingException {
        h.clear();
        h = new ArrayList<Byte>();
        byte[] arr = str.getBytes(encoding.name());
        for (Byte n : arr) {
            h.add(n);
        }
        return h;
    }

    /**
     * @param <T>
     * @param arr
     * @param converted
     * @since 2.0
     */

    @SuppressWarnings("unchecked")
    public static <T> void convert(Object[] arr, T[] converted) {
        for (int i = 0; i < converted.length; i++) {
            converted[i] = (T) arr[i];
        }
    }

    /**
     * @param <T>
     * @param arr1
     * @param arr2
     * @return true if arr1 = arr2
     * @since 2.1
     */

    public static <T> boolean equal(T[] arr1, T[] arr2) {
        if (arr1.length != arr2.length)
            return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }

    /**
     * @param str
     * @return character default encoding
     * @throws UnsupportedEncodingException
     * @since 1.8
     */

    public static List<Byte> toUTF(String str) throws UnsupportedEncodingException {
        return toUTF(str, StandardCharsets.UTF_16);
    }
    
    public static final Static console = new Static();

    /**
     * @param x
     * @since 1.0
     */

    private final ExecutableFunction.EF1 write = System.out::print;

    /**
     * @param x
     * @since 1.0
     */

    public void writeArray(Object[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(boolean[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(int[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(long[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(short[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(float[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.2
     */

    public void writeArray(double[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param x
     * @since 1.3
     */

    public void writeArray(byte[] x) {
        write.$(Arrays.toString(x));
    }

    /**
     * @param task     to do
     * @param interval the time
     * @param delay
     * @since 1.5
     */

    public static void setInterval(TimerTask task, int delay, int interval) {
        new Timer().scheduleAtFixedRate(task, delay, interval);
    }

    /**
     * @param task  to do
     * @param delay time
     * @since 1.8
     */

    public static void setInterval(TimerTask task, int delay) {
        setInterval(task, delay, 0);
    }

    /**
     * @param date
     * @param format
     * @return formatted date
     * @since 1.5
     */

    public static String formatDate(LocalDate date, String format) {
        final DateTimeFormatter form = DateTimeFormatter.ofPattern(format);
        final String formattedDate = date.format(form);
        return formattedDate;
    }

    /**
     * @param testString
     * @param caseType   case insensitive or sentitive
     * @param matcher    string
     * @return true if matches testString
     * @since 1.5
     */

    public static boolean match(String testString, int caseType, String matcher) {
        Pattern pattern = Pattern.compile(matcher, caseType);
        Matcher match = pattern.matcher(testString);
        return match.find();
    }

    /**
     * @param c
     * @return true if the character is a digit
     * @since 1.9
     */

    public static boolean isDigit(char c) {
        return switch (c) {
            case '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    /**
     * email form
     * 
     * @since 1.5
     */

    public static final String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    /**
     * password form
     * 
     * @since 1.5
     */

    public static final String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

    /**
     * username form
     * 
     * @since 1.5
     */

    public static final String usernameRegex = "[a-z0-9_-]{6,12}$";

    /**
     * Create a draw task <blockquote>
     * 
     * <pre>
     * JFrame frame = new JFrame();
     * frame.setSize(1000, 1000);
     * Global.Draw drawer = Global.Draw((g) -> {
     *     g.drawLine(1, 1, 100, 100);
     * }, new Dimension(750, 750));
     * frame.add(drawer);
     * </pre>
     * 
     * </blockquote>
     * 
     * @since 1.7
     * @see DrawTask#draw
     */

    public static class Draw extends JPanel {
        private static final long serialVersionUID = 1L;
        private DrawTask task;
        private Dimension d;

        public Draw(DrawTask task, Dimension panelWidthHeight) {
            this.task = task;
            d = panelWidthHeight;
        }

        @Override
        public void paint(Graphics g) {
            setVisible(true);
            setSize(d);
            task.draw(g);
        }
    }

    /**
     * @param hostname
     * @return host IP
     * @throws UnknownHostException
     * @since 2.2
     */

    public static String getIP(String hostname) throws UnknownHostException {
        InetAddress addr = InetAddress.getByName(hostname);
        return addr.getHostAddress();
    }
}