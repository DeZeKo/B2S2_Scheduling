public class SRT {
    private ProcessList processList;
    private SRTQueue queue;
    private long prev;
    private long time;
    private Process running;
    private int timeMultiplier;
    private ResponseRatio responseRatio;

    SRT(ProcessList pl) {
        this.processList = pl;
        this.queue = new SRTQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = 1;
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    SRT(ProcessList pl, int tm) {
        this.processList = pl;
        this.queue = new SRTQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = tm;
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    private long getDeltaTime() {
        long now = System.nanoTime();
        long deltaMicros = (now - this.prev) / 1_000L / this.timeMultiplier;
        this.prev = now;
        return deltaMicros;
    }

    public void execute() {
        int ctr = 0;
        this.time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || this.running != null) {
            long verschil = getDeltaTime();
            this.time += verschil;

            if (this.running != null) {
                this.running.execute(verschil);
            }

            if (this.running != null && this.running.isFinished()) {
                responseRatio.markFinish(this.running);
                System.out.println("Process " + this.running.getProcessID() +
                        " R = " + responseRatio.getRatioForProcess(this.running.getProcessID()));
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
                    System.out.println(ctr++ + " -> " + this.running);
                }
            } else if (!queue.isEmpty()) {
                Process shortest = queue.peekProcess();

                if (shortest.getRemainingTime() < this.running.getRemainingTime()) {
                    queue.addProcess(this.running);
                    responseRatio.markEnqueued(this.running, this.time);

                    this.running = queue.getProcess();
                    responseRatio.markDequeued(this.running, this.time);

                    System.out.println(ctr++ + " -> " + this.running);
                }
            }
        }

        System.out.println("Mean R = " + responseRatio.getMeanRatio());
    }
}
