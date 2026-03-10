public class SRT {
    private final ProcessList processList;
    private final SRTQueue queue;
    private long time;
    private Process running;
    private final ResponseRatio responseRatio;

    SRT(ProcessList pl) {
        this.processList = pl;
        this.queue = new SRTQueue();
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    public void execute() {
        this.time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || this.running != null) {
            this.time += 1;

            if (this.running != null) {
                this.running.execute(1);
            }

            if (this.running != null && this.running.isFinished()) {
                responseRatio.markFinish(this.running);
                // System.out.println("Process " + this.running.getProcessID() +
                //         " R = " + responseRatio.getRatioForProcess(this.running.getProcessID()));
                this.running = null;
            }

            while (processList.peekNextProcess() != null &&
                   processList.peekNextProcess().isArrived(this.time)) {
                Process p = processList.popNextProcess();
                queue.addProcess(p);
                responseRatio.markEnqueued(p, this.time);
            }

            if (this.running == null) {
                this.running = queue.getProcess();
                if (this.running != null) {
                    responseRatio.markDequeued(this.running, this.time);
                }
            } else if (!queue.isEmpty()) {
                Process shortest = queue.peekProcess();

                if (shortest.getRemainingTime() < this.running.getRemainingTime()) {
                    queue.addProcess(this.running);
                    responseRatio.markEnqueued(this.running, this.time);

                    this.running = queue.getProcess();
                    responseRatio.markDequeued(this.running, this.time);
                }
            }
        }

        System.out.println("SRT: Mean R = " + responseRatio.getMeanRatio());
    }
}
