import java.util.Queue;
import java.util.ArrayDeque;

public class ProcessList {
    private Queue<Process> processes;

    ProcessList(){
        processes = new ArrayDeque<>();
    }

    public void addProcess(Process p){
        processes.offer(p);
    }

    public Process peekNextProcess(){
        return processes.peek();
    }

    public Process popNextProcess(){
        return processes.poll();
    }

    public boolean hasNextProcess(){
        return !processes.isEmpty();
    }

    public String toString(){
        return processes.toString();
    }
}
