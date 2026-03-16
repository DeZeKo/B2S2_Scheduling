public class MLFB {
    private final ProcessList processList;
    private final ProcessList finished;
    private final MLFBQueue queue;

    private long time;
    private Process running;
    private int runningLevel;
    private int quantumUsed;

    MLFB(ProcessList pl, int[] quantums) {
        this.processList = pl;
        this.finished = new ProcessList();
        this.queue = new MLFBQueue(quantums);

        this.running = null;
        this.runningLevel = -1;
        this.quantumUsed = 0;
    }

    public String resultAsCSV() {
        return finished.toCSV();
    }

    public void execute() {
        time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {

            // 1. New arrivals: always enter top queue
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                queue.addNewProcess(processList.popNextProcess());
            }

            // 2. Finish current process if done
            if (running != null && running.isFinished()) {
                running.setFinishTime(time);
                finished.addProcess(running);
                running = null;
                runningLevel = -1;
                quantumUsed = 0;
            }

            // 3. Preempt if a higher-priority queue has work
            if (running != null && queue.hasHigherPriorityProcess(runningLevel)) {
                queue.addProcess(running, runningLevel);
                running = null;
                runningLevel = -1;
                quantumUsed = 0;
            }

            // 4. If CPU idle, dispatch next process
            if (running == null) {
                MLFBQueue.QueueEntry next = queue.getNextProcess();
                if (next != null) {
                    running = next.process;
                    runningLevel = next.level;
                    quantumUsed = 0;
                }
            }

            // 5. Execute one tick
            time += 1;
            if (running != null) {
                running.execute(1);
                quantumUsed += 1;
            }

            // 6. If process finished exactly after this tick, loop will handle it next round
            // 7. If quantum expired and process not finished, demote
            if (running != null && !running.isFinished() && quantumUsed >= queue.getQuantum(runningLevel)) {
                int nextLevel = queue.demoteLevel(runningLevel);
                queue.addProcess(running, nextLevel);
                running = null;
                runningLevel = -1;
                quantumUsed = 0;
            }
        }
    }
}
