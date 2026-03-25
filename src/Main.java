
public class Main {
    public static void main(String[] args) {
        System.out.println("--Simulating...--");
        
        try {
            Simulator sim10 = new Simulator(10000);
            Simulator sim20 = new Simulator(20000);
            Simulator sim50 = new Simulator(50000);

            sim10.simulate();
            sim20.simulate();
            sim50.simulate();

            sim10.export();
            sim20.export();
            sim50.export();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("--Finished--");
    }
}