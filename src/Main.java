public class Main {
    public static void main(String[] args) {
        ProcessReader pr = new ProcessReader("processen10000.xml");
        try {
            ProcessList pl = pr.read();

            FCFS fcfs = new FCFS(new ProcessList(pl));
            RR rr2 = new RR(new ProcessList(pl), 2);
            RR rr4 = new RR(new ProcessList(pl), 4);
            RR rr8 = new RR(new ProcessList(pl), 8);
            SPN spn = new SPN(new ProcessList(pl));
            SRT srt = new SRT(new ProcessList(pl));

            fcfs.execute();
            rr2.execute();
            rr4.execute();
            rr8.execute();
            spn.execute();
            srt.execute();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }
}