#include <iostream>
#include <vector>
using namespace std;

struct Process
{
    int id;
    int at;
    int bt;
    int remaining;
    int ct;
    int tat;
    int wt;
};

int main()
{
    int n;

    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> p(n);

    for (int i = 0; i < n; i++)
    {
        p[i].id = i + 1;

        cout << "Enter Arrival Time and Burst Time for P" << p[i].id << ": ";
        cin >> p[i].at >> p[i].bt;

        p[i].remaining = p[i].bt;
    }

    int time = 0;
    int completed = 0;

    float totalWT = 0;
    float totalTAT = 0;

    // Gantt Chart data
    vector<int> order;
    vector<int> startTime;
    vector<int> finishTime;

    while (completed < n)
    {
        int index = -1;
        int shortest = 999999;

        // Find process with shortest remaining time
        for (int i = 0; i < n; i++)
        {
            if (p[i].at <= time && p[i].remaining > 0)
            {
                if (p[i].remaining < shortest)
                {
                    shortest = p[i].remaining;
                    index = i;
                }
            }
        }

        // CPU is idle
        if (index == -1)
        {
            int nextArrival = 999999;

            for (int i = 0; i < n; i++)
            {
                if (p[i].remaining > 0 && p[i].at < nextArrival)
                {
                    nextArrival = p[i].at;
                }
            }

            order.push_back(-1);
            startTime.push_back(time);

            time++;

            finishTime.push_back(time);

            continue;
        }

        // Start time of this execution
        startTime.push_back(time);

        // Execute for 1 unit
        p[index].remaining--;
        time++;

        // Store process in Gantt Chart
        order.push_back(index);
        finishTime.push_back(time);

        // Process completed
        if (p[index].remaining == 0)
        {
            p[index].ct = time;
            p[index].tat = p[index].ct - p[index].at;
            p[index].wt = p[index].tat - p[index].bt;

            totalWT += p[index].wt;
            totalTAT += p[index].tat;

            completed++;
        }
    }

    cout << "\nShortest Remaining Time First Scheduling\n";

    cout << "PID\tAT\tBT\tCT\tTAT\tWT\n";

    for (int i = 0; i < n; i++)
    {
        cout << "P" << p[i].id << "\t"
             << p[i].at << "\t"
             << p[i].bt << "\t"
             << p[i].ct << "\t"
             << p[i].tat << "\t"
             << p[i].wt << endl;
    }

    cout << "\nAverage Waiting Time: " << totalWT / n << endl;
    cout << "Average Turnaround Time: " << totalTAT / n << endl;

    // Gantt Chart
    cout << "\nGantt Chart:\n";

    // Combine consecutive same processes
    int start = 0;

    for (int i = 0; i < order.size(); i++)
    {
        if (i == 0)
        {
            continue;
        }

        if (order[i] != order[i - 1])
        {
            if (order[start] == -1)
                cout << "|\tIDLE\t";
            else
                cout << "|\tP" << p[order[start]].id << "\t";

            start = i;
        }
    }

    // Print last block
    if (order[start] == -1)
        cout << "|\tIDLE\t";
    else
        cout << "|\tP" << p[order[start]].id << "\t";

    cout << "|\n";

    // Print time
    cout << "0";

    start = 0;

    for (int i = 1; i < order.size(); i++)
    {
        if (order[i] != order[i - 1])
        {
            cout << "\t\t" << finishTime[i - 1];
        }
    }

    cout << "\t\t" << finishTime.back() << endl;

    return 0;
}