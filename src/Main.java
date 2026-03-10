public class Main {
    public static void main(String[] args) {
        ProcessReader pr = new ProcessReader("processen10000.xml");
        try {
            ProcessList pl = pr.read();
            FCFS fcfs = new FCFS(pl);
            fcfs.excecute();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }
}