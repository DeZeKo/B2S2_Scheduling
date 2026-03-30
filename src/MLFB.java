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

    private boolean rescedule_needed(){
        // rescedule can happen if no process running, process finished, process used quantum or higher priority process arrived
        return running == null || running.isFinished() || quantumUsed >= queue.getQuantum(runningLevel) || queue.hasHigherPriorityProcess(runningLevel);
    }

    @Override
    public void execute() {
        time = 0;

        int nextLevel;
        MLFBQueue.QueueEntry next;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {

            // 1. New arrivals
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                queue.addNewProcess(processList.popNextProcess());
            }

            // 2. Select next process
            if (rescedule_needed()) {
                if (running != null) {
                    if (running.isFinished()) {         // case 1: process finished
                        running.setFinishTime(time);
                        finished.addProcess(running);
                    }
                    else if (quantumUsed >= queue.getQuantum(runningLevel)) {       // case 2: process used quantum
                        nextLevel = queue.demoteLevel(runningLevel);
                        queue.addProcess(running, nextLevel);
                    } 
                    else if (queue.hasHigherPriorityProcess(runningLevel)) {        // case 3: higher priority
                        queue.addProcess(running, runningLevel);
                    }
                }

                next = queue.getNextProcess();      // pick next process
                if (next != null) {
                    running = next.process;
                    runningLevel = next.level;
                    quantumUsed = 0;
                } else {
                    running = null;
                    runningLevel = -1;
                    quantumUsed = 0;
                }
            }

            // 3. Execute
            time += 1;
            if (running != null) {
                running.execute(1);
                quantumUsed += 1;
            }            
        }
    }
}
