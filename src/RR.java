public class RR extends Algorithm {
    private final RRQueue queue;
    private final int quantum;

    RR(ProcessList pl, int q){
        super(pl);

        quantum = q;
        queue = new RRQueue();
    }

    @Override
    public void execute(){
        time = 0;
        
        int q_prog = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {

            // 1. New arrivals
            
            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)){
                queue.addProcess(processList.popNextProcess());
            }

            // 2. Select next process

            if (running == null || running.isFinished() || q_prog >= quantum) {
                if (running != null) {
                    if (!running.isFinished()) {
                        queue.addProcess(running);
                    } else {
                        running.setFinishTime(time);
                        finished.addProcess(running);
                    } 
                } 
                running = queue.getProcess();
                q_prog = 0;
            }

            // 3. Execute

            time += 1;
            if (running != null) {
                running.execute(1);
                q_prog += 1;
            }
        }
    }
}
