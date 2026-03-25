import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println("--Simulating...--");

        ProcessReader pr = new ProcessReader("data_sets/processen50000.xml");
        try {
            ProcessList pl = pr.read();

            FCFS fcfs = new FCFS(new ProcessList(pl));
            RR rr2 = new RR(new ProcessList(pl), 2);
            RR rr4 = new RR(new ProcessList(pl), 4);
            RR rr8 = new RR(new ProcessList(pl), 8);
            SPN spn = new SPN(new ProcessList(pl));
            SRT srt = new SRT(new ProcessList(pl));
            HRRN hrrn = new HRRN(new ProcessList(pl));
            MLFB mlfb1 = new MLFB(new ProcessList(pl), new int[]{1, 2, 4, 8, 16});
            MLFB mlfb2 = new MLFB(new ProcessList(pl), new int[]{8, 16, 32, 64, 128});

            fcfs.execute();
            rr2.execute();
            rr4.execute();
            rr8.execute();
            spn.execute();
            srt.execute();
            hrrn.execute();
            mlfb1.execute();
            mlfb2.execute();

            try (FileWriter writer = new FileWriter("raw_output/FCFS.csv")) {
                writer.write(fcfs.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/RR2.csv")) {
                writer.write(rr2.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/RR4.csv")) {
                writer.write(rr4.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/RR8.csv")) {
                writer.write(rr8.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/SPN.csv")) {
                writer.write(spn.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/SRT.csv")) {
                writer.write(srt.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/HRRN.csv")) {
                writer.write(hrrn.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/MLFB1.csv")) {
                writer.write(mlfb1.resultAsCSV());
            }
            try (FileWriter writer = new FileWriter("raw_output/MLFB2.csv")) {
                writer.write(mlfb2.resultAsCSV());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("--Finished--");
    }
}