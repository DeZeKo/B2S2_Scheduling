# Scheduling Algorithms Simulator (Java)

A Java-based simulator for **process scheduling algorithms**, developed for the Operating Systems 1 scheduling assignment.  
The project simulates different CPU scheduling strategies on XML process datasets and compares their performance using turnaround time, normalized turnaround time, and waiting time.

---

## Authors

- Kobe Decalf
- Zenn De Craene

---

## Features

- Process scheduling simulation
- XML-based process input
- CSV export of simulation results
- Python plotting script for graphs
- Performance metrics:
  - Turnaround Time (TAT)
  - Normalized Turnaround Time (nTAT)
  - Waiting Time (Tw)

---

## Implemented Algorithms

- First Come, First Served (FCFS)
- Round Robin (RR)
  - Time quantum 2
  - Time quantum 4
  - Time quantum 8
- Shortest Process Next (SPN)
- Shortest Remaining Time (SRT)
- Highest Response Ratio Next (HRRN)
- Multi-Level Feedback (MLFB)
  - MLFB1: 1, 2, 4, 8, 16
  - MLFB2: 8, 16, 32, 64, 128

---

## Input

The simulator reads process datasets from the `data_sets` folder.
Each process contains:

- Process ID
- Arrival time
- Service time

Included datasets:

- `processen10000.xml`
- `processen20000.xml`
- `processen50000.xml`

---

## Output

The simulator writes the result of each algorithm to the `raw_output` folder as CSV files.
The output format is:

    pid,ta,ts,tf

Where:

- `pid` – process ID
- `ta` – arrival time
- `ts` – service time
- `tf` – finish time

Graphs are stored in the `graphs` folder.

---

## How to Run

### Requirements

- Java 17+ recommended
- Python 3 installed, only needed for graph generation
- Python packages for plotting:
  - pandas
  - numpy
  - matplotlib
  - statsmodels

### Run the Simulation

From the project root:

```bash
javac src/*.java
java -cp src Main
```

This runs all implemented algorithms for all included datasets and exports the results to `raw_output`.

### Generate Graphs

After running the Java simulation, generate the graphs with:

```bash
python3 src/plotter.py
```

The generated graphs will be saved in the `graphs` folder.
