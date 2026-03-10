import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println("--Simulating...--");

        ProcessReader pr = new ProcessReader("processen50000.xml");
        try {
            ProcessList pl = pr.read();

            FCFS fcfs = new FCFS(new ProcessList(pl));
            RR rr2 = new RR(new ProcessList(pl), 2);
            RR rr4 = new RR(new ProcessList(pl), 4);
            RR rr8 = new RR(new ProcessList(pl), 8);
            SPN spn = new SPN(new ProcessList(pl));
            SRT srt = new SRT(new ProcessList(pl));

            fcfs.execute();
            rr2.execute();
            rr4.execute();
            rr8.execute();
            spn.execute();
            srt.execute();

            try (FileWriter writer = new FileWriter("fcfs.csv")) {
                writer.write(fcfs.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("rr2.csv")) {
                writer.write(rr2.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("rr4.csv")) {
                writer.write(rr4.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("rr8.csv")) {
                writer.write(rr8.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("spn.csv")) {
                writer.write(spn.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("srt.csv")) {
                writer.write(srt.resultAsCSV());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("--Finished--");
    }
}