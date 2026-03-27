package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@State(Scope.Benchmark)
public class Data {

    public final static VarHandle staticFinalHandle;

    public static VarHandle staticHandle;

    static {
        try {
            staticFinalHandle = createVarHandle();
            staticHandle = createVarHandle();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public final VarHandle instanceFinal;
    public VarHandle instance;
    public MyClass data;
    public String newValue = "123";

    public Data() {
        try {
            instance = createVarHandle();
            instanceFinal = createVarHandle();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        data = new MyClass("123");
    }

    public static VarHandle createVarHandle() throws IllegalAccessException, NoSuchFieldException {
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(MyClass.class, MethodHandles.lookup());
        return lookup.findVarHandle(MyClass.class, "data", String.class);
    }
}
