import java.io.FileWriter;

public class Simulator {
    private final ProcessReader pr;
    private final int dataSetAmount;

    private final FCFS fcfs;
    private final RR rr2;
    private final RR rr4;
    private final RR rr8;
    private final SPN spn;
    private final SRT srt;
    private final HRRN hrrn;
    private final MLFB mlfb1;
    private final MLFB mlfb2;

    public Simulator(int dsm) throws Exception {
        this.dataSetAmount = dsm;
        pr = new ProcessReader("data_sets/processen" + dataSetAmount + ".xml");
        ProcessList pl = pr.read();

        fcfs = new FCFS(new ProcessList(pl));
        rr2 = new RR(new ProcessList(pl), 2);
        rr4 = new RR(new ProcessList(pl), 4);
        rr8 = new RR(new ProcessList(pl), 8);
        spn = new SPN(new ProcessList(pl));
        srt = new SRT(new ProcessList(pl));
        hrrn = new HRRN(new ProcessList(pl));
        mlfb1 = new MLFB(new ProcessList(pl), new int[]{1, 2, 4, 8, 16});
        mlfb2 = new MLFB(new ProcessList(pl), new int[]{8, 16, 32, 64, 128});
    }

    public void simulate() {
        fcfs.execute();
        rr2.execute();
        rr4.execute();
        rr8.execute();
        spn.execute();
        srt.execute();
        hrrn.execute();
        mlfb1.execute();
        mlfb2.execute();
    }

    public void export() throws Exception {
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_FCFS.csv")) {
            writer.write(fcfs.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_RR2.csv")) {
            writer.write(rr2.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_RR4.csv")) {
            writer.write(rr4.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_RR8.csv")) {
            writer.write(rr8.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_SPN.csv")) {
            writer.write(spn.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_SRT.csv")) {
            writer.write(srt.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_HRRN.csv")) {
            writer.write(hrrn.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_MLFB1.csv")) {
            writer.write(mlfb1.resultAsCSV());
        }
        try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_MLFB2.csv")) {
            writer.write(mlfb2.resultAsCSV());
        }
    }
}
