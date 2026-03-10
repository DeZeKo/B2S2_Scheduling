public class Process {
    private int processID;
    private int arrivalTime;
    private int serviceTime;
    private long runningTime;

    Process(int pid, int at, int st){
        this.processID = pid;
        this.arrivalTime = at;
        this.serviceTime = st;
        this.runningTime = 0;
    }

    public boolean isArrived(long time){
        return time >= arrivalTime;
    }

    public void execute(long time){
        this.runningTime += time;
    }

    public boolean isFinished(){
        return this.runningTime > this.serviceTime;
    }

    public String toString(){
        return "Process(pid=" + processID + ", at=" + arrivalTime + ", st=" + serviceTime + ")";
    }
}
