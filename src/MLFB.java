public class MLFB extends Algorithm{
    private final MLFBQueue queue;
    private int runningLevel;
    private int quantumUsed;

    MLFB(ProcessList pl, int[] quantums) {
        super(pl);

        queue = new MLFBQueue(quantums);
        runningLevel = -1;
        quantumUsed = 0;
    }

    @Override
    public void execute() {
        time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {

            // 1. New arrivals
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                queue.addNewProcess(processList.popNextProcess());
            }

            // 2. Select next process

            if (running != null && running.isFinished()) {
                running.setFinishTime(time);
                finished.addProcess(running);
                running = null;
                runningLevel = -1;
                quantumUsed = 0;
            }
            if (running != null && queue.hasHigherPriorityProcess(runningLevel)) {
                queue.addProcess(running, runningLevel);
                running = null;
                runningLevel = -1;
                quantumUsed = 0;
            }
            if (running == null) {
                MLFBQueue.QueueEntry next = queue.getNextProcess();
                if (next != null) {
                    running = next.process;
                    runningLevel = next.level;
                    quantumUsed = 0;
                }
            }

            // 3. Execute

            time += 1;
            if (running != null) {
                running.execute(1);
                quantumUsed += 1;
            }

            // 4. Post-tick logic

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
