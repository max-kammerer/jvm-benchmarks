package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.*;

public class VarHandleOpaqueBenchmark {

    @Benchmark
    public String staticFinalHandle(Data data) {
        return (String) Data.staticFinalHandle.getOpaque(data);
    }

    @Benchmark
    public String staticHandle(Data data) {
        return (String) Data.staticHandle.getOpaque(data);
    }

    @Benchmark
    public String instanceFinalHandle(Data data) {
        return (String) data.instanceFinal.getOpaque(data);
    }

    @Benchmark
    public String instanceHandle(Data data) {
        return (String) data.instance.getOpaque(data);
    }

    @Benchmark
    public String directFieldAccess(Data data) {
        return data.data;
    }

    @Benchmark
    public void setStaticFinalHandle(Data data) {
        Data.staticFinalHandle.setOpaque(data, data.newValue);
    }

    @Benchmark
    public void setStaticHandle(Data data) {
        Data.staticHandle.setOpaque(data, data.newValue);
    }

    @Benchmark
    public void setInstanceFinalHandle(Data data) {
        data.instanceFinal.setOpaque(data, data.newValue);
    }

    @Benchmark
    public void setInstanceHandle(Data data) {
        data.instance.setOpaque(data, data.newValue);
    }

    @Benchmark
    public void setDirectFieldAccess(Data data) {
        data.data = data.newValue;
    }
}