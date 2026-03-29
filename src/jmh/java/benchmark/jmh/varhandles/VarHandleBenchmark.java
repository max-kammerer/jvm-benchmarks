package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.*;

public class VarHandleBenchmark {

    @Benchmark
    public String staticFinalHandle(Data data) {
        return (String) Data.staticFinalHandle.get(data);
    }

    @Benchmark
    public String staticHandle(Data data) {
        return (String) Data.staticHandle.get(data);
    }

    @Benchmark
    public String instanceFinalHandle(Data data) {
        return (String) data.instanceFinal.get(data);
    }

    @Benchmark
    public String instanceHandle(Data data) {
        return (String) data.instance.get(data);
    }

    @Benchmark
    public String directFieldAccess(Data data) {
        return data.data;
    }

    @Benchmark
    public void setStaticFinalHandle(Data data) {
        Data.staticFinalHandle.set(data, data.newValue);
    }

    @Benchmark
    public void setStaticHandle(Data data) {
        Data.staticHandle.set(data, data.newValue);
    }

    @Benchmark
    public void setInstanceFinalHandle(Data data) {
        data.instanceFinal.set(data, data.newValue);
    }

    @Benchmark
    public void setInstanceHandle(Data data) {
         data.instance.set(data, data.newValue);
    }

    @Benchmark
    public void setDirectFieldAccess(Data data) {
        data.data = data.newValue;
    }
}