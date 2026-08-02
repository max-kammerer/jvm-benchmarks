package benchmark.jmh.methods;

import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;

public class JavaClass {

    public void test(Blackhole blackhole) {
        blackhole.consume(123);
    }


    public String doSmth2() throws InterruptedException {
        return "Hello";
    }

    public String doSmth() throws InterruptedException {
        //Thread.sleep(1);
        return "Hello";
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method testMethod = JavaClass.class.getDeclaredMethod("doSmth2");
        JavaClass obj = new JavaClass();
        testMethod.invoke(obj);
        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                testMethod = JavaClass.class.getDeclaredMethod("doSmth");
            }
            long start = System.nanoTime();
            testMethod.invoke(obj);
            System.out.println(System.nanoTime() - start + " nanos");
        }
    }
}
