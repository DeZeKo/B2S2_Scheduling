import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.ndimage import gaussian_filter1d

frames: dict[str, pd.DataFrame] = {}
avg_frames: dict[str, pd.DataFrame] = {}

# Load CSVs
frames["fcfs"] = pd.read_csv("../raw_output/fcfs.csv")
frames["rr2"] = pd.read_csv("../raw_output/rr2.csv")
frames["rr4"] = pd.read_csv("../raw_output/rr4.csv")
frames["rr8"] = pd.read_csv("../raw_output/rr8.csv")
frames["spn"] = pd.read_csv("../raw_output/spn.csv")
frames["srt"] = pd.read_csv("../raw_output/srt.csv")
frames["hrrn"] = pd.read_csv("../raw_output/hrrn.csv")
frames["mlfb1"] = pd.read_csv("../raw_output/mlfb1.csv")
frames["mlfb2"] = pd.read_csv("../raw_output/mlfb2.csv")

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

# Plotting
for key, df in avg_frames.items():
    plt.plot(df["percentile"], df["ntat"], marker='', label=key)

plt.xlabel("Percentile of time required")
plt.ylabel("Normalized turnaround time")
plt.title("NTAT vs Percentile of Time Required")
plt.yscale("log")
plt.grid(True, which="both", linestyle="--")
plt.legend()
plt.show()

for key, df in avg_frames.items():
    x = df["percentile"].to_numpy()
    y = df["ntat"].to_numpy()

    # Sort just in case
    sorted_idx = np.argsort(x)
    x = x[sorted_idx]
    y = y[sorted_idx]

    # Apply Gaussian smoothing to y-values
    y_smooth = gaussian_filter1d(y, sigma=2)  # adjust sigma for smoothness

    plt.plot(x, y_smooth, label=key)

plt.xlabel("Percentile of Time Required")
plt.ylabel("Normalized Turnaround Time")
plt.title("NTAT vs Percentile of Time Required")
plt.yscale("log")
plt.grid(True, which="both", linestyle="--")
plt.legend()
plt.show()

# plt.plot(avg["ts"], avg["tw"], marker='s', color='red', label='TW')
# plt.xlabel("Time Slice (ts)")
# plt.ylabel("Average Waiting Time (TW)")
# plt.title("TW vs Time Slice")
# plt.grid(True)
# plt.legend()
# plt.show()