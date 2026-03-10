import java.util.ArrayDeque;
import java.util.Queue;

public class ProcessList {
    private final Queue<Process> processes;

    ProcessList(){
        processes = new ArrayDeque<>();
    }

    ProcessList(ProcessList pl) {
        processes = new ArrayDeque<>(pl.processes);
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

    @Override
    public String toString(){
        return processes.toString();
    }
}
