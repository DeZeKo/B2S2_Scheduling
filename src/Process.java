public class Process {
    private final int processID;
    private final int arrivalTime;
    private final int serviceTime;
    private long runningTime;

    Process(int pid, int at, int st){
        this.processID = pid;
        this.arrivalTime = at;
        this.serviceTime = st;
        this.runningTime = 0;
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public long getRemainingTime() {
        return serviceTime - runningTime;
    }

    public boolean isArrived(long time){
        return time >= arrivalTime;
    }

    public void execute(long time){
        this.runningTime += time;
    }

    public boolean isFinished(){
        return this.runningTime >= this.serviceTime;
    }

    @Override
    public String toString(){
        return "Process(pid=" + processID + ", at=" + arrivalTime + ", st=" + serviceTime + ")";
    }
}
