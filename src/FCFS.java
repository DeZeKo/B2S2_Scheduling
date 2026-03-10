public class FCFS {
    private final ProcessList processList;
    private final FCFSQueue queue;
    private long time;
    private Process running;
    private final ResponseRatio responseRatio;
    
    FCFS(ProcessList pl){
        this.processList = pl;
        this.queue = new FCFSQueue();
        this.running = null;
        this.responseRatio = new ResponseRatio();
    }

    public void execute(){
        this.time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || this.running != null) {
            this.time += 1;

            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(this.time)){
                Process p = processList.popNextProcess();
                queue.addProcess(p);
                responseRatio.markEnqueued(p, this.time);
            }

            if (this.running == null || this.running.isFinished()){
                if (this.running != null && this.running.isFinished()) {
                    responseRatio.markFinish(this.running);
                    // System.out.println("Process " + this.running.getProcessID() +
                    //         " R = " + responseRatio.getRatioForProcess(this.running.getProcessID()));
                }

                this.running = this.queue.getProcess();

                if (this.running != null) {
                    responseRatio.markDequeued(this.running, this.time);
                }
            }

            if (this.running != null) {
                this.running.execute(1);
            }
        }

        System.out.println("FCFS: Mean R = " + responseRatio.getMeanRatio());
    }
}
