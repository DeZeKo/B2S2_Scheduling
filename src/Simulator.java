import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Simulator {
    private final ProcessReader pr;
    private final int dataSetAmount;
    private final Map<String, Algorithm> algorithms;

    public Simulator(int dsm) throws Exception {
        this.dataSetAmount = dsm;
        pr = new ProcessReader("data_sets/processen" + dataSetAmount + ".xml");
        ProcessList pl = pr.read();

        algorithms = new LinkedHashMap<>();
        algorithms.put("FCFS", new FCFS(new ProcessList(pl)));
        algorithms.put("RR2", new RR(new ProcessList(pl), 2));
        algorithms.put("RR4", new RR(new ProcessList(pl), 4));
        algorithms.put("RR8", new RR(new ProcessList(pl), 8));
        algorithms.put("SPN", new SPN(new ProcessList(pl)));
        algorithms.put("SRT", new SRT(new ProcessList(pl)));
        algorithms.put("HRRN", new HRRN(new ProcessList(pl)));
        algorithms.put("MLFB1", new MLFB(new ProcessList(pl), new int[]{1, 2, 4, 8, 16}));
        algorithms.put("MLFB2", new MLFB(new ProcessList(pl), new int[]{8, 16, 32, 64, 128}));
    }

    public void simulate() {
        for (Algorithm algorithm : algorithms.values()) {
            algorithm.execute();
        }
    }

    public void export() throws Exception {
        for (Map.Entry<String, Algorithm> entry : algorithms.entrySet()) {
            String name = entry.getKey();
            Algorithm algorithm = entry.getValue();
            try (FileWriter writer = new FileWriter("raw_output/" + dataSetAmount + "_" + name + ".csv")) {
                writer.write(algorithm.resultAsCSV());
            }
        }
    }
}
