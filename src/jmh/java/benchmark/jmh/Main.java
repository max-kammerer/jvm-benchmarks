package benchmark.jmh;

import one.nio.os.NativeLibrary;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Use NativeLibrary " + NativeLibrary.IS_SUPPORTED);
        org.openjdk.jmh.Main.main(args);
    }
}
