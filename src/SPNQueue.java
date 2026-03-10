import java.util.PriorityQueue;
import java.util.Queue;

public class SPNQueue {
    private final Queue<Process> processes;

    SPNQueue() {
        processes = new PriorityQueue<>(
            (p1, p2) -> {
                if (p1.getServiceTime() != p2.getServiceTime()) {
                    return Integer.compare(p1.getServiceTime(), p2.getServiceTime());
                }
                if (p1.getArrivalTime() != p2.getArrivalTime()) {
                    return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                }
                return Integer.compare(p1.getProcessID(), p2.getProcessID());
            }
        );
    }

    public void addProcess(Process p) {
        processes.offer(p);
    }

    public Process getProcess() {
        return processes.poll();
    }

    public boolean isEmpty() {
        return processes.isEmpty();
    }

    @Override
    public String toString() {
        return processes.toString();
    }
}
