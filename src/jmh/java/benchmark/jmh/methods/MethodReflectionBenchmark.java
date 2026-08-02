package benchmark.jmh.methods;

import org.openjdk.jmh.annotations.*;


import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MethodReflectionBenchmark {

    @Benchmark
    public void directCall(Data data, Blackhole blackhole) {
        data.javaClass.test(blackhole);
    }

    @Benchmark
    public void reflectionCall(Data data, Blackhole blackhole) throws InvocationTargetException, IllegalAccessException {
        data.testMethod.invoke(data.javaClass, blackhole);
    }

    @Benchmark
    public void reflectionFastCall(Data data, Blackhole blackhole) throws InvocationTargetException, IllegalAccessException {
        data.testMethodFast.invoke(data.javaClass, blackhole);
    }

    @State(Scope.Benchmark)
    public static class Data {

        public final JavaClass javaClass = new JavaClass();

        public final Method testMethod;
        public final Method testMethodFast;

        static {

        }
        public Data() {
            try {
                testMethod = JavaClass.class.getDeclaredMethod("test", Blackhole.class);
                testMethodFast = JavaClass.class.getDeclaredMethod("test", Blackhole.class);
                testMethodFast.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}