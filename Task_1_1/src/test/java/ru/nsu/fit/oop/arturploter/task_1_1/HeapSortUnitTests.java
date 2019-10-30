package ru.nsu.fit.oop.arturploter.task_1_1;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeapSortUnitTests {
    @Test
    public void given_ListWithDuplicates_Then_ReturnSortedListWithDuplicates() {
        ArrayList<String> listWithDuplicates = new ArrayList<>(Collections.nCopies(100, "choppa"));

        List<String> actual = HeapSort.sort(listWithDuplicates);
        Collections.sort(listWithDuplicates);

        assertThat(actual, is(listWithDuplicates));
    }

    @Test
    public void given_IntegerList_Then_ReturnSortedIntegerList() {
        ArrayList<Integer> randomIntegers = new Random()
                .ints(783646, -5000, 5000).boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        List<Integer> actual = HeapSort.sort(randomIntegers);
        Collections.sort(randomIntegers);

        assertThat(randomIntegers, is(randomIntegers));
    }

    @Test
    public void given_DoubleList_Then_ReturnSortedDoubleList() {
        ArrayList<Double> randomDoubles = new Random()
                .doubles(7000000, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        List<Double> actual = HeapSort.sort(randomDoubles);
        Collections.sort(randomDoubles);

        assertThat(actual, is(randomDoubles));
    }

    @Test
    public void given_BooleanList_Then_ReturnSortedBooleanList() {
        ArrayList<Boolean> randomBooleans = new ArrayList<>(Collections.nCopies(7261205, new Random().nextBoolean()));
        List<Boolean> actual = HeapSort.sort(randomBooleans);
        Collections.sort(randomBooleans);

        assertThat(actual, is(randomBooleans));
    }

    @Test
    public void given_EmptyList_Then_ReturnEmptyList() {
        ArrayList<String> emptyList = new ArrayList<>();
        List<String> actual = HeapSort.sort(emptyList);

        assertThat(actual, is(emptyList));
    }

    @Test
    public void given_StringList_Then_ReturnSortedStringList() {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras elementum nibh purus,"
                + " sed viverra lorem semper rhoncus. Donec odio eros, condimentum quis bibendum at,"
                + " condimentum non quam. Etiam fringilla porttitor velit eget sagittis. Suspendisse id dui est."
                + " Duis vel volutpat tortor. In feugiat tincidunt mauris, maximus porta massa ornare vitae."
                + " Praesent eget ullamcorper ex, eget malesuada orci. Fusce at nisi erat."
                + "\n"
                + "Phasellus quis lobortis velit, in sodales lectus. Pellentesque sit amet volutpat nisi."
                + " Suspendisse potenti. Sed massa lorem, aliquet eu imperdiet eu, hendrerit id nisi."
                + " Curabitur lacinia velit ligula, in dignissim quam tristique luctus."
                + " Nunc dapibus vehicula neque eget imperdiet. Morbi luctus, sapien ut congue semper,"
                + " mauris neque fermentum est, tempus suscipit ex dui convallis metus."
                + " Cras iaculis rhoncus turpis vitae posuere. Morbi nec ornare nibh."
                + " Nulla blandit justo et lectus interdum sollicitudin. Nullam eget nunc et diam ultricies fermentum."
                + " Nam congue faucibus neque ac finibus."
                + " Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."
                + " In fringilla, mauris et pretium consectetur, massa urna varius neque,"
                + " et malesuada quam risus sed odio. Aliquam erat volutpat. Lorem LOREM lOrEm Lorem LoREM LoREM lOREm"
                + " ``` ^^^ \n \b \f \n \t";

        ArrayList<String> dummyWords = new ArrayList<>(Arrays.asList(loremIpsum.split(" ")));

        List<String> actual = HeapSort.sort(dummyWords);
        Collections.sort(dummyWords);

        assertThat(actual, is(dummyWords));
    }

    @Test
    public void given_EmployeeObjectList_Then_ReturnEmployeeObjectList() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", 35, 82000));
        employees.add(new Employee("David", 26, 60000));
        employees.add(new Employee("Elinore", 29, 63000));
        employees.add(new Employee("Lowell", 52, 131000));
        employees.add(new Employee("Ralph", 37, 97000));
        employees.add(new Employee("Edward", 35, 89000));
        employees.add(new Employee("David", 26, 71000));
        employees.add(new Employee("Markus", 65, 78000));
        employees.add(new Employee("Muhammad", 65, 78000));

        List<Employee> actual = HeapSort.sort(employees);
        Collections.sort(employees);

        assertThat(actual, is(employees));
    }

    private class Employee implements Comparable<Employee> {
        private UUID id;
        private String name;
        private int age;
        private int salary;

        public Employee(String name, int age, int salary) {
            id = UUID.randomUUID();
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public long getSalary() {
            return salary;
        }

        @Override
        public int compareTo(Employee employee) {
            return this.getId().compareTo(employee.getId());
        }
    }
}