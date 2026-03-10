public class SPN {
    private final ProcessList processList;
    private final SPNQueue queue;
    private long time;
    private Process running;
    private final ResponseRatio responseRatio;

    SPN(ProcessList pl) {
        this.processList = pl;
        this.queue = new SPNQueue();
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    public void execute() {
        this.time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || this.running != null) {
            this.time += 1;

            while (processList.peekNextProcess() != null &&
                   processList.peekNextProcess().isArrived(this.time)) {
                Process p = processList.popNextProcess();
                queue.addProcess(p);
                responseRatio.markEnqueued(p, this.time);
            }

            if (this.running == null || this.running.isFinished()) {
                if (this.running != null && this.running.isFinished()) {
                    responseRatio.markFinish(this.running);
                    // System.out.println("Process " + this.running.getProcessID() +
                    //         " R = " + responseRatio.getRatioForProcess(this.running.getProcessID()));
                }

                this.running = queue.getProcess();

                if (this.running != null) {
                    responseRatio.markDequeued(this.running, this.time);
                }
            }

            if (this.running != null) {
                this.running.execute(1);
            }
        }

        System.out.println("SPN: Mean R = " + responseRatio.getMeanRatio());
    }
}
