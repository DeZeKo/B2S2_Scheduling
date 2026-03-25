import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statsmodels.nonparametric.smoothers_lowess import lowess

for am in (10000, 20000, 50000):
    frames: dict[str, pd.DataFrame] = {}
    avg_frames: dict[str, pd.DataFrame] = {}

    # Load CSVs
    frames["FCFS"] = pd.read_csv("../raw_output/" + str(am) + "_FCFS.csv")
    frames["RR2"] = pd.read_csv("../raw_output/" + str(am) + "_RR2.csv")
    frames["RR4"] = pd.read_csv("../raw_output/" + str(am) + "_RR4.csv")
    frames["RR8"] = pd.read_csv("../raw_output/" + str(am) + "_RR8.csv")
    frames["SPN"] = pd.read_csv("../raw_output/" + str(am) + "_SPN.csv")
    frames["SRT"] = pd.read_csv("../raw_output/" + str(am) + "_SRT.csv")
    frames["HRRN"] = pd.read_csv("../raw_output/" + str(am) + "_HRRN.csv")
    frames["MLFB1"] = pd.read_csv("../raw_output/" + str(am) + "_MLFB1.csv")
    frames["MLFB2"] = pd.read_csv("../raw_output/" + str(am) + "_MLFB2.csv")

    for key, df in frames.items():
        # Calculations
        df["tat"] = df["tf"] - df["ta"]
        df["tw"] = df["tat"] - df["ts"]
        df["ntat"] = df["tat"] / df["ts"]

        # Sort by service time
        df.sort_values(by="ts", inplace=True)

        # Group into 100 blocks
        group_size = len(df) // 100
        avg_df = df.groupby(np.arange(len(df)) // group_size)[["ts", "ntat", "tw"]].mean()

        # Add percentiles
        avg_df["percentile"] = np.linspace(0, 100, len(avg_df))
        avg_frames[key] = avg_df

    print(f"--- Metrics for dataset {am} ---")
    for key, df in frames.items():
        print(f"{key}(tat: {df['tat'].mean()}, ntat: {df['ntat'].mean()}, tw: {df['tw'].mean()})")
    print()

    # Plotting
    for key, df in avg_frames.items():
        x = df["percentile"].to_numpy()
        y = df["ntat"].to_numpy()

        smooth = lowess(y, x, frac=0.09)
        plt.plot(smooth[:, 0], smooth[:, 1], label=key)

    plt.xlabel("Percentile of Time Required")
    plt.ylabel("Normalized Turnaround Time")
    plt.title("NTAT vs Percentile of Time Required")
    plt.yscale("log")
    plt.grid(True, which="both", linestyle="--")
    plt.legend()
    plt.savefig("../graphs/" + str(am) + "_ntat.png")
    plt.close()

    for key, df in avg_frames.items():
        x = df["percentile"].to_numpy()
        y = df["tw"].to_numpy()

        smooth = lowess(y, x, frac=0.09)
        plt.plot(smooth[:, 0], smooth[:, 1], label=key)

    plt.xlabel("Percentile of Time Required")
    plt.ylabel("Average Waiting Time")
    plt.title("TW vs Time Slice")
    plt.grid(True)
    plt.legend()
    plt.savefig("../graphs/" + str(am) + "_tw.png")
    plt.close()
