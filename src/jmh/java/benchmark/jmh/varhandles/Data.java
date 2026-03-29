package benchmark.jmh.varhandles;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

@State(Scope.Benchmark)
public class Data {

    public final static VarHandle staticFinalHandle;

    public static VarHandle staticHandle;

    static {
        try {
            staticFinalHandle = createVarHandle();
            staticHandle = createVarHandle();
            staticFinalRef = createReflectionField();
            staticRef = createReflectionField();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public final VarHandle instanceFinal;
    public VarHandle instance;

    public final static Field staticFinalRef;

    public static Field staticRef;


    public final Field instanceFinalRef;
    public Field instanceRef;


    public String newValue = "123";
    public String data = "oldvalue";

    public Data() {
        try {
            instance = createVarHandle();
            instanceFinal = createVarHandle();
            instanceFinalRef = createReflectionField();
            instanceRef = createReflectionField();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static VarHandle createVarHandle() throws IllegalAccessException, NoSuchFieldException {
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Data.class, MethodHandles.lookup());
        return lookup.findVarHandle(Data.class, "data", String.class);
    }

    public static Field createReflectionField() throws IllegalAccessException, NoSuchFieldException {
        return Data.class.getDeclaredField("data");
    }
}
