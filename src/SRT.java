public class SRT {
    private ProcessList processList;
    private SRTQueue queue;
    private long prev;
    private long time;
    private Process running;
    private int timeMultiplier;

    SRT(ProcessList pl) {
        this.processList = pl;
        this.queue = new SRTQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = 1;
        this.running = null;
    }

    SRT(ProcessList pl, int tm) {
        this.processList = pl;
        this.queue = new SRTQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = tm;
        this.running = null;
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

            // First run current process for elapsed time
            if (this.running != null) {
                this.running.execute(verschil);
            }

            // If finished, remove it
            if (this.running != null && this.running.isFinished()) {
                this.running = null;
            }

            // Add newly arrived processes
            while (processList.peekNextProcess() != null &&
                   processList.peekNextProcess().isArrived(this.time)) {
                queue.addProcess(processList.popNextProcess());
            }

            // If nothing is running, pick next
            if (this.running == null) {
                this.running = queue.getProcess();
                if (this.running != null) {
                    System.out.println(ctr++ + " -> " + this.running);
                }
            }
            // If something is running, check preemption
            else if (!queue.isEmpty()) {
                Process shortest = queue.peekProcess();

                if (shortest.getRemainingTime() < this.running.getRemainingTime()) {
                    queue.addProcess(this.running);     // put current back
                    this.running = queue.getProcess();  // take shortest
                    System.out.println(ctr++ + " -> " + this.running);
                }
            }
        }
    }
}