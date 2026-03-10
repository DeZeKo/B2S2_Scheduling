import java.util.HashMap;
import java.util.Map;

public class ResponseRatio {
    private Map<Integer, Long> waitingSince;
    private Map<Integer, Long> totalWaitingTime;
    private Map<Integer, Double> ratios;
    private double totalRatio;
    private int finishedProcesses;

    ResponseRatio() {
        this.waitingSince = new HashMap<>();
        this.totalWaitingTime = new HashMap<>();
        this.ratios = new HashMap<>();
        this.totalRatio = 0.0;
        this.finishedProcesses = 0;
    }

    // process enters ready queue and starts waiting
    public void markEnqueued(Process p, long currentTime) {
        int pid = p.getProcessID();
        waitingSince.put(pid, currentTime);
        totalWaitingTime.putIfAbsent(pid, 0L);
    }

    // process leaves ready queue and stops waiting
    public void markDequeued(Process p, long currentTime) {
        int pid = p.getProcessID();

        if (!waitingSince.containsKey(pid)) {
            return;
        }

        long waited = currentTime - waitingSince.get(pid);
        totalWaitingTime.put(pid, totalWaitingTime.getOrDefault(pid, 0L) + waited);
        waitingSince.remove(pid);
    }

    // process finishes
    public void markFinish(Process p) {
        int pid = p.getProcessID();

        if (ratios.containsKey(pid)) {
            return;
        }

        long w = totalWaitingTime.getOrDefault(pid, 0L);
        long s = p.getServiceTime();

        double r = ((double) w + s) / s;

        ratios.put(pid, r);
        totalRatio += r;
        finishedProcesses++;
    }

    public Double getRatioForProcess(int pid) {
        return ratios.get(pid);
    }

    public double getMeanRatio() {
        if (finishedProcesses == 0) {
            return 0.0;
        }
        return totalRatio / finishedProcesses;
    }

    public void printAllRatios() {
        for (Map.Entry<Integer, Double> entry : ratios.entrySet()) {
            System.out.println("Process " + entry.getKey() + " -> R = " + entry.getValue());
        }
    }
}
