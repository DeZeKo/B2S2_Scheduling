import java.util.List;
import java.util.ArrayList;

public class ProcessList {
    private List<Process> processList;

    ProcessList(){
        processList = new ArrayList<>();
    }

    public void addProcess(Process p){
        processList.add(p);
    }

    public String toString(){
        return processList.toString();
    }
}
