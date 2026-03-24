public abstract class Algorithm {
    protected final ProcessList processList;
    protected final ProcessList finished;
    protected long time;
    protected Process running;
    
    protected Algorithm(ProcessList pl){
        this.processList = pl;
        this.finished = new ProcessList();
        this.running = null;
    }

    public abstract void execute();

    public String resultAsCSV() {
        return finished.toCSV();
    }
}