public class Main {
    public static void main(String[] args) {
        ProcessReader pr = new ProcessReader("processen10000.xml");
        try {
            ProcessList pl = pr.read();
            RR rr = new RR(pl, 1);
            rr.execute();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }
}