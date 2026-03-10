public class FCFS {
    private ProcessList processList;
    private FCFSQueue queue;
    private long prev;
    private long time;
    private Process running;
    private int timeMultiplier;
    private ResponseRatio responseRatio;
    
    FCFS(ProcessList pl){
        this.processList = pl;
        this.queue = new FCFSQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = 1;
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    FCFS(ProcessList pl, int tm){
        this.processList = pl;
        this.queue = new FCFSQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = tm;
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    private long getDeltaTime(){
        long now = System.nanoTime();
        long deltaMicros = (now - this.prev) / 1_000L / this.timeMultiplier;
        this.prev = now;
        return deltaMicros;
    }

    public void execute(){
        int ctr = 0;
        this.time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || this.running != null) {
            long verschil = getDeltaTime();
            this.time += verschil;

            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(this.time)){
                Process p = processList.popNextProcess();
                queue.addProcess(p);
                responseRatio.markEnqueued(p, this.time);
            }

            if (this.running == null || this.running.isFinished()){
                if (this.running != null && this.running.isFinished()) {
                    responseRatio.markFinish(this.running);
                    System.out.println("Process " + this.running.getProcessID() +
                            " R = " + responseRatio.getRatioForProcess(this.running.getProcessID()));
                }

                this.running = this.queue.getProcess();

                if (this.running != null) {
                    responseRatio.markDequeued(this.running, this.time);
                    System.out.println(ctr++);
                }
            }

            if (this.running != null) {
                this.running.execute(verschil);
            }
        }

        System.out.println("Mean R = " + responseRatio.getMeanRatio());
    }
}
