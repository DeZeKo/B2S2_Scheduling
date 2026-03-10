public class RR {
    private final ProcessList processList;
    private final ProcessList finished;

    private final RRQueue queue;
    private final int quantum;
    private long time;
    private Process running;

    RR(ProcessList pl, int q){
        processList = pl;
        finished = new ProcessList();

        quantum = q;
        queue = new RRQueue();
        running = null;
    }

    public String resultAsCSV() {
        return finished.toCSV();
    }

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