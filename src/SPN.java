public class SPN {
    private ProcessList processList;
    private SPNQueue queue;
    private long prev;
    private long time;
    private Process running;
    private int timeMultiplier;

    SPN(ProcessList pl) {
        this.processList = pl;
        this.queue = new SPNQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = 1;
        this.running = null;
    }

    SPN(ProcessList pl, int tm) {
        this.processList = pl;
        this.queue = new SPNQueue();
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

            while (processList.peekNextProcess() != null &&
                   processList.peekNextProcess().isArrived(this.time)) {
                queue.addProcess(processList.popNextProcess());
            }

            if (this.running == null || this.running.isFinished()) {
                this.running = queue.getProcess();
                if (this.running != null) {
                    System.out.println(ctr++ + " -> " + this.running);
                }
            }

            if (this.running != null) {
                this.running.execute(verschil);
            }
        }
    }
}