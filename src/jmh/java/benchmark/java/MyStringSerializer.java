package benchmark.java;

import java.io.IOException;

import one.nio.serial.CalcSizeStream;
import one.nio.serial.DataStream;
import one.nio.serial.Json;
import one.nio.serial.JsonReader;
import one.nio.serial.Serializer;
import one.nio.util.Utf8;

import static java.lang.Character.MIN_SUPPLEMENTARY_CODE_POINT;

public class MyStringSerializer extends Serializer<String> {

    public MyStringSerializer() {
        super(String.class);
        generateUid();
    }

    @Override
    public void calcSize(String obj, CalcSizeStream css) {
        int length = encodedLength(obj);
        css.add(length + (length <= 0x7fff ? 2 : 4));
//        System.out.println("calc size");
    }

    @Override
    public void write(String obj, DataStream out) throws IOException {
        out.writeUTF(obj);
    }

    @Override
    public String read(DataStream in) throws IOException {
        String result = in.readUTF();
        in.register(result);
        return result;
    }

    @Override
    public void skip(DataStream in) throws IOException {
        int length = in.readUnsignedShort();
        if (length > 0x7fff) {
            length = (length & 0x7fff) << 16 | in.readUnsignedShort();
        }
        in.skipBytes(length);
    }

    @Override
    public void toJson(String obj, StringBuilder builder) {
        Json.appendString(builder, obj);
    }

    @Override
    public String fromJson(JsonReader in) throws IOException {
        return in.readString();
    }

    @Override
    public String fromString(String s) {
        return s;
    }


    static int encodedLength(String string) {
        // Warning to maintainers: this implementation is highly optimized.
        int utf16Length = string.length();
        int utf8Length = utf16Length;
        int i = 0;

        // This loop optimizes for pure ASCII.
        while (i < utf16Length && string.charAt(i) < 0x80) {
            i++;
        }

        // This loop optimizes for chars less than 0x800.
        for (; i < utf16Length; i++) {
            char c = string.charAt(i);
            if (c < 0x800) {
                utf8Length += ((0x7f - c) >>> 31); // branch free!
            } else {
                utf8Length += encodedLengthGeneral(string, i);
                break;
            }
        }

        if (utf8Length < utf16Length) {
            // Necessary and sufficient condition for overflow because of maximum 3x expansion
            throw new IllegalArgumentException(
                    "UTF-8 length does not fit in int: " + (utf8Length + (1L << 32)));
        }
        return utf8Length;
    }

    private static int encodedLengthGeneral(String string, int start) {
        int utf16Length = string.length();
        int utf8Length = 0;
        for (int i = start; i < utf16Length; i++) {
            char c = string.charAt(i);
            if (c < 0x800) {
                utf8Length += (0x7f - c) >>> 31; // branch free!
            } else {
                utf8Length += 2;
                // jdk7+: if (Character.isSurrogate(c)) {
                if (Character.MIN_SURROGATE <= c && c <= Character.MAX_SURROGATE) {
                    // Check that we have a well-formed surrogate pair.
                    int cp = Character.codePointAt(string, i);
                    if (cp < MIN_SUPPLEMENTARY_CODE_POINT) {
                        throw new IllegalArgumentException("" + i + utf16Length);
                    }
                    i++;
                }
            }
        }
        return utf8Length;
    }


}