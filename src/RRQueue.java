import java.util.ArrayDeque;
import java.util.Queue;

public class RRQueue {
    private final Queue<Process> queue;

    RRQueue(){
        this.queue = new ArrayDeque<>();
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
    
    @Override
    public String toString(){
        return queue.toString();
    }
}
