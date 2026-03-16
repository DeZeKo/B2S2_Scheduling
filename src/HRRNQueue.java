import java.util.ArrayList;
import java.util.List;

public class HRRNQueue {
    private final List<Process> processes;

    HRRNQueue() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(Process p) {
        processes.add(p);
    }

    public Process getProcess(long currentTime) {
        if (processes.isEmpty()) {
            return null;
        }

        int bestIndex = 0;
        double bestRatio = responseRatio(processes.get(0), currentTime);

        for (int i = 1; i < processes.size(); i++) {
            Process p = processes.get(i);
            double ratio = responseRatio(p, currentTime);

            if (ratio > bestRatio) {
                bestRatio = ratio;
                bestIndex = i;
            } else if (ratio == bestRatio) {
                // Tie-breaker: earlier arrival time first
                Process best = processes.get(bestIndex);
                if (p.getArrivalTime() < best.getArrivalTime()) {
                    bestIndex = i;
                } else if (p.getArrivalTime() == best.getArrivalTime()) {
                    // Second tie-breaker: lower process ID first
                    if (p.getProcessID() < best.getProcessID()) {
                        bestIndex = i;
                    }
                }
            }
        }

        return processes.remove(bestIndex);
    }

    private double responseRatio(Process p, long currentTime) {
        long waitingTime = currentTime - p.getArrivalTime();
        return (waitingTime + p.getServiceTime()) / (double) p.getServiceTime();
    }

    public boolean isEmpty() {
        return processes.isEmpty();
    }

    public int size() {
        return processes.size();
    }

    @Override
    public String toString() {
        return processes.toString();
    }
}
