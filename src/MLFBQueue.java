import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MLFBQueue {
    private final List<Queue<Process>> queues;
    private final int[] quantums;

    MLFBQueue(int[] quantums) {
        if (quantums == null || quantums.length != 5) {
            throw new IllegalArgumentException("MLFB must have exactly 5 levels.");
        }

        this.quantums = quantums.clone();
        this.queues = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            queues.add(new ArrayDeque<>());
        }
    }

    public static class QueueEntry {
        public final Process process;
        public final int level;

        QueueEntry(Process process, int level) {
            this.process = process;
            this.level = level;
        }
    }

    public void addNewProcess(Process p) {
        queues.get(0).offer(p);
    }

    public void addProcess(Process p, int level) {
        queues.get(level).offer(p);
    }

    public QueueEntry getNextProcess() {
        for (int i = 0; i < queues.size(); i++) {
            if (!queues.get(i).isEmpty()) {
                return new QueueEntry(queues.get(i).poll(), i);
            }
        }
        return null;
    }

    public boolean hasHigherPriorityProcess(int currentLevel) {
        for (int i = 0; i < currentLevel; i++) {
            if (!queues.get(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int demoteLevel(int currentLevel) {
        if (currentLevel < queues.size() - 1) {
            return currentLevel + 1;
        }
        return currentLevel;
    }

    public int getQuantum(int level) {
        return quantums[level];
    }

    public boolean isEmpty() {
        for (Queue<Process> q : queues) {
            if (!q.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        int total = 0;
        for (Queue<Process> q : queues) {
            total += q.size();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MLFBQueue{");
        for (int i = 0; i < queues.size(); i++) {
            sb.append("L").append(i).append("=").append(queues.get(i));
            if (i < queues.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
