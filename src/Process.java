public class Process {
    private int processID;
    private int arrivalTime;
    private int serviceTime;

    Process(int pid, int at, int st){
        this.processID = pid;
        this.arrivalTime = at;
        this.serviceTime = st;
    }

    public String toString(){
        return "Process(pid=" + processID + ", at=" + arrivalTime + ", st=" + serviceTime + ")";
    }
}
