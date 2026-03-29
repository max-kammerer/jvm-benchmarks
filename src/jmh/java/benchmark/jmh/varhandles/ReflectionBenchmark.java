package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.*;

public class ReflectionBenchmark {

    @Benchmark
    public String staticFinalReflection(Data data) throws IllegalAccessException {
        return (String) Data.staticFinalRef.get(data);
    }

    @Benchmark
    public String staticReflection(Data data) throws IllegalAccessException {
        return (String) Data.staticRef.get(data);
    }

    @Benchmark
    public String instanceFinalReflection(Data data) throws IllegalAccessException {
        return (String) data.instanceFinalRef.get(data);
    }

    @Benchmark
    public String instanceReflection(Data data) throws IllegalAccessException {
        return (String) data.instanceRef.get(data);
    }

    @Benchmark
    public String directFieldAccess(Data data) {
        return data.data;
    }

    @Benchmark
    public void setStaticFinalReflection(Data data) throws IllegalAccessException {
        Data.staticFinalRef.set(data, data.newValue);
    }

    @Benchmark
    public void setStaticReflection(Data data) throws IllegalAccessException {
        Data.staticRef.set(data, data.newValue);
    }

    @Benchmark
    public void setInstanceFinalReflection(Data data) throws IllegalAccessException {
        data.instanceFinalRef.set(data, data.newValue);
    }

    @Benchmark
    public void setInstanceReflection(Data data) throws IllegalAccessException {
         data.instanceRef.set(data, data.newValue);
    }

    @Benchmark
    public void setDirectFieldAccess(Data data) {
        data.data = data.newValue;
    }
}