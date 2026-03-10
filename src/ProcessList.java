import java.util.ArrayDeque;
import java.util.Queue;

public class ProcessList {
    private final Queue<Process> processes;

    ProcessList(){
        processes = new ArrayDeque<>();
    }

    ProcessList(ProcessList pl) {
        processes = new ArrayDeque<>();
        for (Process p : pl.processes) {
            processes.offer(new Process(p));
        }
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

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();

        sb.append("id,ta,ts,tf").append(sep);  // id, arrivalTime, serviceTime, finishTime

        for (Process p: processes) {
            sb.append(p.toCSV()).append(sep);
        }

        return sb.toString();
    }

    @Override
    public String toString(){
        return processes.toString();
    }
}
