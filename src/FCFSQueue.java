import java.util.LinkedList;
import java.util.Queue;

public class FCFSQueue {
    private Queue<Process> queue;

    FCFSQueue(){
        this.queue = new LinkedList<>();
    }

    public void addProcess(Process p){
        this.queue.offer(p);
    }

    public Process getProcess(){
        return this.queue.poll();
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    public int size(){
        return this.queue.size();
    }
    
    public String toString(){
        return queue.toString();
    }
}
