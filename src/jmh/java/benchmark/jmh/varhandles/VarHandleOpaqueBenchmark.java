package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 5)
@Fork(1)
public class VarHandleOpaqueBenchmark {

    @Benchmark
    public String staticFinalHandle(Data data) {
        return (String) Data.staticFinalHandle.getOpaque(data.data);
    }

    @Benchmark
    public String staticHandle(Data data) {
        return (String) Data.staticHandle.getOpaque(data.data);
    }

    @Benchmark
    public String instanceFinalHandle(Data data) {
        return (String) data.instanceFinal.getOpaque(data.data);
    }

    @Benchmark
    public String instanceHandle(Data data) {
        return (String) data.instance.getOpaque(data.data);
    }

    @Benchmark
    public String directFieldAccess(Data data) {
        return data.data.data;
    }

    @Benchmark
    public void setStaticFinalHandle(Data data) {
        Data.staticFinalHandle.setOpaque(data.data, data.newValue);
    }

    @Benchmark
    public void setStaticHandle(Data data) {
        Data.staticHandle.setOpaque(data.data, data.newValue);
    }

    @Benchmark
    public void setInstanceFinalHandle(Data data) {
        data.instanceFinal.setOpaque(data.data, data.newValue);
    }

    @Benchmark
    public void setInstanceHandle(Data data) {
         data.instance.setOpaque(data.data, data.newValue);
    }

    @Benchmark
    public void setDirectFieldAccess(Data data) {
        data.data.data = data.newValue;
    }
}