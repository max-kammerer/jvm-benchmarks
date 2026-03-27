package benchmark.jmh;

import java.util.Arrays;

import one.nio.serial.SerializeStream;

public class DynamicDataStream extends SerializeStream {

    public DynamicDataStream(byte[] array) {
        super(array);
    }

    public long alloc(int size) {
        long currentOffset = offset;
        if ((offset = currentOffset + size) > limit) {
            limit = Math.max(offset, limit * 2);
            array = Arrays.copyOf(array, (int) (limit - address));
        }
        return currentOffset;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(array, count());
    }
}
