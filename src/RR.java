public class RR {
    private final ProcessList processList;
    private final RRQueue queue;
    private final int quantum;
    private long time;
    private Process running;
    private final ResponseRatio responseRatio;

    RR(ProcessList pl, int q){
        this.processList = pl;
        this.quantum = q;
        this.queue = new RRQueue();
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    public void execute(){
        time = 0;
        int progress = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {
            time += 1;
            progress += 1;

            // New arrivals enter ready queue -> start waiting
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                Process p = processList.popNextProcess();
                queue.addProcess(p);
                responseRatio.markEnqueued(p, time);
            }

            // If current process finished, record ratio
            if (running != null && running.isFinished()) {
                responseRatio.markFinish(running);
                // System.out.println("Process " + running.getProcessID() +
                //         " R = " + responseRatio.getRatioForProcess(running.getProcessID()));
                running = null;
                progress = 0;
            }

            // Quantum expired or CPU idle -> choose next process
            if (running == null || progress >= quantum) {
                // Put current running process back in ready queue if not finished
                if (running != null && !running.isFinished()) {
                    queue.addProcess(running);
                    responseRatio.markEnqueued(running, time);
                }

                running = queue.getProcess();

                // Process leaves ready queue -> stops waiting
                if (running != null) {
                    responseRatio.markDequeued(running, time);
                }

                progress = 0;
            }

            // Execute current process for 1 time unit
            if (running != null) {
                running.execute(1);

                // If it finishes right after execution, record ratio now
                if (running.isFinished()) {
                    responseRatio.markFinish(running);
                    // System.out.println("Process " + running.getProcessID() +
                    //         " R = " + responseRatio.getRatioForProcess(running.getProcessID()));
                    running = null;
                    progress = 0;
                }
            }
        }

        System.out.println("RR" + this.quantum +": Mean R = " + responseRatio.getMeanRatio());
    }
}