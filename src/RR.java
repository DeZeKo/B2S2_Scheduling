public class RR {
    private final ProcessList processList;
    private final RRQueue queue;
    private final int quantum;
    private long time;
    private Process running;
    
    RR(ProcessList pl, int q){
        this.processList = pl;
        this.quantum = q;
        this.queue = new RRQueue();
        this.running = null;
    }

    public void execute(){
        time = 0;
        int progress = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {
            time += 1;
            progress += 1;

            // Test for new arrivals
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)){
                queue.addProcess(processList.popNextProcess());
            }

            // Set a new process
            if (running == null || running.isFinished() || progress >= quantum) {
                if (running != null && !running.isFinished()) {
                    queue.addProcess(running);
                }
                running = queue.getProcess();
                progress = 0;
            }

            // Execute the current process for a quantum, or until it finishes
            if (this.running != null) {
                this.running.execute(1);
                
            }
        }
    }
}
