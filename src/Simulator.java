import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Simulator {
    private final ProcessReader processreader;
    private final int dataSetAmount;
    private final Map<String, Algorithm> algorithms;

    public Simulator(int dsm) throws Exception {
        this.dataSetAmount = dsm;
        processreader = new ProcessReader("data_sets/processen" + dataSetAmount + ".xml");
        ProcessList processlist = processreader.read();

        algorithms = new LinkedHashMap<>();
        algorithms.put("FCFS", new FCFS(new ProcessList(processlist)));
        algorithms.put("RR2", new RR(new ProcessList(processlist), 2));
        algorithms.put("RR4", new RR(new ProcessList(processlist), 4));
        algorithms.put("RR8", new RR(new ProcessList(processlist), 8));
        algorithms.put("SPN", new SPN(new ProcessList(processlist)));
        algorithms.put("SRT", new SRT(new ProcessList(processlist)));
        algorithms.put("HRRN", new HRRN(new ProcessList(processlist)));
        algorithms.put("MLFB1", new MLFB(new ProcessList(processlist), new int[]{1, 2, 4, 8, 16}));
        algorithms.put("MLFB2", new MLFB(new ProcessList(processlist), new int[]{8, 16, 32, 64, 128}));
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
