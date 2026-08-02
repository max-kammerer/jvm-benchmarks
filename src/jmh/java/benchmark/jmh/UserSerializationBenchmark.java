package benchmark.jmh;

import org.openjdk.jmh.annotations.*;

import benchmark.java.UserJava;
import benchmark.proto.User;
import one.nio.serial.Serializer;
import org.openjdk.jmh.infra.Blackhole;


public class UserSerializationBenchmark {

    // ---------- serialize ----------
    @Benchmark
    public byte[] oneNio_with_dto_serialize(Data data) throws Exception {
        UserJava javaUser = data.javaUser;
        UserJava.Company c = javaUser.company;
        UserJava.Address a = javaUser.address;
        UserJava javaUser2 = new UserJava(javaUser.id, javaUser.name, javaUser.email, javaUser.age, javaUser.createdAt,
                new UserJava.Address(a.city, a.street, a.house), new UserJava.Company(c.name, c.department));

        return Serializer.serialize(javaUser2);
    }

    @Benchmark

    public byte[] oneNio_serialize_once(Data data) throws Exception {
        DynamicDataStream dynamicDS = new DynamicDataStream(new byte[200]);
        UserJava javaUser = data.javaUser;
        UserJava.Company c = javaUser.company;
        UserJava.Address a = javaUser.address;
        UserJava javaUser2 = new UserJava(javaUser.id, javaUser.name, javaUser.email, javaUser.age, javaUser.createdAt,
                new UserJava.Address(a.city, a.street, a.house), new UserJava.Company(c.name, c.department));
        dynamicDS.writeObject(javaUser2);

        return dynamicDS.toByteArray();
    }

    @Benchmark

    public byte[] proto_with_dto_serialize(Data data) {
        UserJava javaUser = data.javaUser;
        User.Builder b = benchmark.proto.User.newBuilder().setId(javaUser.id).setName(javaUser.name).setEmail(javaUser.email).setAge(javaUser.age).setCreatedAt(javaUser.createdAt);
        b.setAddress(User.Address.newBuilder().setCity(javaUser.address.city).setStreet(javaUser.address.street).setHouse(javaUser.address.house).build());
        b.setCompany(User.Company.newBuilder().setName(javaUser.company.name).setDepartment(javaUser.company.department).build());

        return b.build().toByteArray();
    }

    @Benchmark

    public UserJava proto_with_dto_deserialize(Data data, Blackhole bh) throws Exception {
        User proto = User.parseFrom(data.protoBytes);
        User.Address a = proto.getAddress();
        UserJava.Address addr = new UserJava.Address(a.getCity(), a.getStreet(), a.getHouse());
        User.Company c = proto.getCompany();
        UserJava.Company comp = new UserJava.Company(c.getName(), c.getDepartment());

        return new UserJava(proto.getId(), proto.getName(), proto.getEmail(), proto.getAge(), proto.getCreatedAt(), addr, comp);
    }

    @Benchmark

    public void proto_bh_consume_deserialize(Data data, Blackhole bh) throws Exception {
        User proto = User.parseFrom(data.protoBytes);
        User.Address a = proto.getAddress();
        bh.consume(a.getCity());
        bh.consume(a.getStreet());
        bh.consume(a.getHouse());
        User.Company c = proto.getCompany();
        bh.consume(c.getName());
        bh.consume(c.getDepartment());
        bh.consume(proto.getId());
        bh.consume(proto.getName());
        bh.consume(proto.getEmail());
        bh.consume(proto.getAge());
        bh.consume(proto.getCreatedAt());
    }

    @Benchmark

    public UserJava oneNio_with_dto_deserialize(Data data, Blackhole bh) throws Exception {
        UserJava javaUser = (UserJava) Serializer.deserialize(data.oneNioBytes);
        UserJava.Company c = javaUser.company;
        UserJava.Address a = javaUser.address;

        return new UserJava(javaUser.id, javaUser.name, javaUser.email, javaUser.age, javaUser.createdAt,

                new UserJava.Address(a.city, a.street, a.house), new UserJava.Company(c.name, c.department));
    }

    @Benchmark

    public void oneNio_bh_consume_deserialize(Data data, Blackhole bh) throws Exception {
        UserJava user = (UserJava) Serializer.deserialize(data.oneNioBytes);
        UserJava.Address a = user.getAddress();
        bh.consume(a.getCity());
        bh.consume(a.getStreet());
        bh.consume(a.getHouse());
        UserJava.Company c = user.getCompany();
        bh.consume(c.getName());
        bh.consume(c.getDepartment());
        bh.consume(user.getId());
        bh.consume(user.getName());
        bh.consume(user.getEmail());
        bh.consume(user.getAge());
        bh.consume(user.getCreatedAt());
    }

// ---------- deserialize ----------

    @State(Scope.Benchmark)
    public static class Data {

        private UserJava javaUser;

        private benchmark.proto.User protoUser;

        private byte[] oneNioBytes;

        private byte[] protoBytes;

        @Setup
        public void setup() throws Exception {
            long createdAt = System.currentTimeMillis();
            UserJava.Address address = new UserJava.Address("ыCity", "ыStreet", 10);
            UserJava.Company company = new UserJava.Company("ыMyComp", "ыR&D");

            javaUser = new UserJava(42L, "ыadыва", "ыadываы@example.com", 30, createdAt, address, company);

            protoUser = benchmark.proto.User.newBuilder().setId(javaUser.getId()).setName(javaUser.name).setEmail(javaUser.email).setAge(javaUser.age).setCreatedAt(createdAt).setAddress(User.Address.newBuilder().setCity(address.city).setStreet(address.street).setHouse(address.house).build()).setCompany(User.Company.newBuilder().setName(company.name).setDepartment(company.department).build()).build();

            oneNioBytes = Serializer.serialize(javaUser);

            protoBytes = protoUser.toByteArray();
            System.out.println("one-nio size = " + oneNioBytes.length);
            System.out.println("protobuf size = " + protoBytes.length);
        }

        public UserJava getJavaUser() {
            return javaUser;
        }

        public User getProtoUser() {
            return protoUser;
        }

        public byte[] getOneNioBytes() {
            return oneNioBytes;
        }

        public byte[] getProtoBytes() {
            return protoBytes;
        }
    }
}