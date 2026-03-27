package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 5)
@Fork(1)
public class ReflectionBenchmark {

    @Benchmark
    public String staticFinalReflection(Data data) throws IllegalAccessException {
        return (String) Data.staticFinalRef.get(data.data);
    }

    @Benchmark
    public String staticReflection(Data data) throws IllegalAccessException {
        return (String) Data.staticRef.get(data.data);
    }

    @Benchmark
    public String instanceFinalReflection(Data data) throws IllegalAccessException {
        return (String) data.instanceFinalRef.get(data.data);
    }

    @Benchmark
    public String instanceReflection(Data data) throws IllegalAccessException {
        return (String) data.instanceRef.get(data.data);
    }

    @Benchmark
    public String directFieldAccess(Data data) {
        return data.data.data;
    }

    @Benchmark
    public void setStaticFinalReflection(Data data) throws IllegalAccessException {
        Data.staticFinalRef.set(data.data, data.newValue);
    }

    @Benchmark
    public void setStaticReflection(Data data) throws IllegalAccessException {
        Data.staticRef.set(data.data, data.newValue);
    }

    @Benchmark
    public void setInstanceFinalReflection(Data data) throws IllegalAccessException {
        data.instanceFinalRef.set(data.data, data.newValue);
    }

    @Benchmark
    public void setInstanceReflection(Data data) throws IllegalAccessException {
         data.instanceRef.set(data.data, data.newValue);
    }

    @Benchmark
    public void setDirectFieldAccess(Data data) {
        data.data.data = data.newValue;
    }
}