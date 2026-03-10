public class Process {
    private final int processID;
    private final int arrivalTime;
    private final int serviceTime;
    private long runningTime;
    private long finishTime;

    Process(int pid, int at, int st){
        this.processID = pid;
        this.arrivalTime = at;
        this.serviceTime = st;
        this.runningTime = 0;
        this.finishTime = -1;
    }
    
    Process(Process other){
        this.processID = other.processID;
        this.arrivalTime = other.arrivalTime;
        this.serviceTime = other.serviceTime;
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

    public void setFinishTime(long time) {
        this.finishTime = time;
    }

    public long getRemainingTime() {
        return serviceTime - runningTime;
    }

    public boolean isArrived(long time) {
        return time >= arrivalTime;
    }

    public void execute(long time) {
        this.runningTime += time;
    }

    public boolean isFinished() {
        return this.runningTime >= this.serviceTime;
    }

    public String toCSV() {
        return processID + "," + arrivalTime + "," + serviceTime + "," + finishTime;
    }

    @Override
    public String toString(){
        return "Process(pid=" + processID + ", at=" + arrivalTime + ", st=" + serviceTime + ")";
    }
}
