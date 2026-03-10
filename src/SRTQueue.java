import java.util.PriorityQueue;
import java.util.Queue;

public class SRTQueue {
    private Queue<Process> processes;

    SRTQueue() {
        processes = new PriorityQueue<>(
            (p1, p2) -> {
                if (p1.getRemainingTime() != p2.getRemainingTime()) {
                    return Long.compare(p1.getRemainingTime(), p2.getRemainingTime());
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

    public Process peekProcess() {
        return processes.peek();
    }

    public boolean isEmpty() {
        return processes.isEmpty();
    }

    public String toString() {
        return processes.toString();
    }
}
