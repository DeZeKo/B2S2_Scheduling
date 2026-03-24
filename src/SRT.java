public class SRT extends Algorithm {
    private final SRTQueue queue;

    SRT(ProcessList pl) {
        super(pl);
        this.queue = new SRTQueue();
    }

    @Override
    public void execute() {
        time = 0;

        while (processList.hasNextProcess() || !queue.isEmpty() || running != null) {
            
            // 1. New arrivals

            while (processList.peekNextProcess() != null && processList.peekNextProcess().isArrived(time)) {
                queue.addProcess(processList.popNextProcess());
            }

            // 2. Select next process

            if (running != null && running.isFinished()) {
                running.setFinishTime(time);
                finished.addProcess(running);
                running = null;
            }

            if (running == null) {
                running = queue.getProcess();
            } else if (!queue.isEmpty()) {
                Process shortest = queue.peekProcess();
                if (shortest.getRemainingTime() < running.getRemainingTime()) {
                    queue.addProcess(running);
                    running = queue.getProcess();
                }
            }

            // 3. Execute
            
            time += 1;
            if (running != null) {
                running.execute(1);
            }
        }
    }
}
