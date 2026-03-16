import java.util.ArrayDeque;
import java.util.Queue;

public class MLFBQueue {
    private final Queue<Process>[] queues;
    private final int[] quantums;

    @SuppressWarnings("unchecked")
    MLFBQueue(int[] quantums) {
        if (quantums == null || quantums.length != 5) {
            throw new IllegalArgumentException("MLFB must have exactly 5 levels.");
        }

        this.quantums = quantums.clone();
        this.queues = new ArrayDeque[5];

        for (int i = 0; i < 5; i++) {
            queues[i] = new ArrayDeque<>();
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
        queues[0].offer(p);
    }

    public void addProcess(Process p, int level) {
        queues[level].offer(p);
    }

    public QueueEntry getNextProcess() {
        for (int i = 0; i < queues.length; i++) {
            if (!queues[i].isEmpty()) {
                return new QueueEntry(queues[i].poll(), i);
            }
        }
        return null;
    }

    public boolean hasHigherPriorityProcess(int currentLevel) {
        for (int i = 0; i < currentLevel; i++) {
            if (!queues[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int demoteLevel(int currentLevel) {
        if (currentLevel < queues.length - 1) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MLFBQueue{");
        for (int i = 0; i < queues.length; i++) {
            sb.append("L").append(i).append("=").append(queues[i]);
            if (i < queues.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}