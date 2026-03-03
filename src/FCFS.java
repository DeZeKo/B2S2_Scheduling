public class FCFS {
    private ProcessList processList;
    private FCFSQueue queue;
    private long prev;
    private long time;
    private Process running;
    private int timeMultiplier;
    
    FCFS(ProcessList pl){
        this.processList = pl;
        this.queue = new FCFSQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = 1;
        this.running = null;
    }

    FCFS(ProcessList pl, int tm){
        this.processList = pl;
        this.queue = new FCFSQueue();
        this.prev = System.nanoTime();
        this.timeMultiplier = tm;
        this.running = null;
    }

    private long getDeltaTime(){
        long now = System.nanoTime();
        long deltaMicros = (now - prev) / 1_000L / this.timeMultiplier;  // integer microseconds
        prev = now;
        return deltaMicros;
    }

    public void excecute(){
        this.time = 0;
        while (true) {
            long verschil = getDeltaTime();
            this.time += verschil;
            while (processList.peekNextProcess().isArrived(this.time)){
                queue.addProcess(processList.popNextProcess());
            }
            if (this.running == null || this.running.isFinished()){
                this.running = this.queue.getProcess();
            }
            this.running.running(verschil);
        }
    }
}
