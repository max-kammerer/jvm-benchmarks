package benchmark.java;
import java.io.Serializable;
public class UserJava implements Serializable {

    public long id;

    public String name;

    public String email;

    public int age;

    public long createdAt;

    public Address address;

    public Company company;

    public UserJava() {}

    public UserJava(long id, String name, String email, int age, long createdAt, Address address, Company company) {

        this.id = id;

        this.name = name;

        this.email = email;

        this.age = age;

        this.createdAt = createdAt;

        this.address = address;

        this.company = company;
    }

    public long getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public int getAge() {

        return age;
    }

    public long getCreatedAt() {

        return createdAt;
    }

    public Address getAddress() {

        return address;
    }

    public Company getCompany() {

        return company;
    }

    @Override

    public String toString() {

        return "UserJava{" +

                "id=" + id +

                ", name='" + name + '\'' +

                ", email='" + email + '\'' +

                ", age=" + age +

                ", createdAt=" + createdAt +

                ", address=" + address +

                ", company=" + company +

                '}';
    }

// Nested types must also be Serializable for one-nio

    public static class Address implements Serializable {

        public String city;

        public String street;

        public int house;

        public Address() {}

        public Address(String city, String street, int house) {

            this.city = city;

            this.street = street;

            this.house = house;
        }

        public String getCity() {

            return city;
        }

        public String getStreet() {

            return street;
        }

        public int getHouse() {

            return house;
        }

        @Override

        public String toString() {

            return "Address{" +

                    "city='" + city + '\'' +

                    ", street='" + street + '\'' +

                    ", house=" + house +

                    '}';
        }
    }

    public static class Company implements Serializable {

        public String name;

        public String department;

        public Company() {}

        public Company(String name, String department) {

            this.name = name;

            this.department = department;
        }

        public String getName() {

            return name;
        }

        public String getDepartment() {

            return department;
        }

        @Override

        public String toString() {

            return "Company{" +

                    "name='" + name + '\'' +

                    ", department='" + department + '\'' +

                    '}';
        }
    }
}