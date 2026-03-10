public class SPN {
    private final ProcessList processList;
    private final ProcessList finished;

    private final SPNQueue queue;
    private long time;
    private Process running;

    SPN(ProcessList pl) {
        processList = pl;
        finished = new ProcessList();

        queue = new SPNQueue();
        running = null;
    }

    public String resultAsCSV() {
        return finished.toCSV();
    }

    public void execute() {
        time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {

            // 1. New arrivals

            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                queue.addProcess(processList.popNextProcess());
            }

            // 2. Select next process

            if (running == null || running.isFinished()) {
                if (running != null) {
                    running.setFinishTime(time);
                    finished.addProcess(running);
                }
                running = queue.getProcess();
            }

            // 3. Execute
            
            time += 1;
            if (running != null) {
                running.execute(1);
            }
        }
    }
}
