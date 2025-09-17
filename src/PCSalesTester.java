public class PCSalesTester
{
    public static void main(String[] args)
    {
        Customer testCustomer1 = new Customer(1, "Jye Curtis-Smith", "jyecurtissmith@gmail.com");
        Customer testCustomer2 = new Customer(2, "John Smith", "john@gmail.com");
        Customer testCustomer3 = new Customer(3, "Jane Doe", "jdoe@gmail.com");

        System.out.println(testCustomer1);
        System.out.println(testCustomer2);
        System.out.println(testCustomer3);

        Order testOrder1 = new Order(1, 3);
        Order testOrder2 = new Order(2, 6);
        Order testOrder3 = new Order(3, 9);

        System.out.println(testOrder1);
        System.out.println(testOrder2);
        System.out.println(testOrder3);

        PCPart part1 = new CPU(
                1,
                "AMD",
                "Ryzen 7 5700X",
                264.0f,
                "x86",
                8,
                3.4f
        );
        PCPart part2 = new GPU(
                2,
                "ASUS",
                "ROG Astral OC GeForce RTX 5090",
                5295.0f,
                32000,
                21760
        );
        PCPart part3 = new Motherboard(
                3,
                "MSI",
                "B450M-A PRO MAX II",
                87.0f,
                "AM4",
                2
        );

        System.out.println(part1);
        System.out.println(part2);
        System.out.println(part3);
    }
}
