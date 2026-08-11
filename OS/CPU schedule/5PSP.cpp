#include <iostream>
#include <vector>
using namespace std;

struct Process
{
    int id;
    int at;
    int bt;
    int remaining;
    int priority;
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

        cout << "Enter Arrival Time, Burst Time and Priority for P"
             << p[i].id << ": ";

        cin >> p[i].at >> p[i].bt >> p[i].priority;

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
        int highestPriority = 999999;

        // Find highest priority available process
        for (int i = 0; i < n; i++)
        {
            if (p[i].at <= time && p[i].remaining > 0)
            {
                if (p[i].priority < highestPriority)
                {
                    highestPriority = p[i].priority;
                    index = i;
                }
            }
        }

        // CPU is idle
        if (index == -1)
        {
            order.push_back(-1);
            startTime.push_back(time);

            time++;

            finishTime.push_back(time);

            continue;
        }

        // Store start time
        startTime.push_back(time);

        // Execute for 1 unit
        p[index].remaining--;
        time++;

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

    cout << "\nPriority Scheduling - Preemptive\n";

    cout << "PID\tAT\tBT\tPriority\tCT\tTAT\tWT\n";

    for (int i = 0; i < n; i++)
    {
        cout << "P" << p[i].id << "\t"
             << p[i].at << "\t"
             << p[i].bt << "\t"
             << p[i].priority << "\t\t"
             << p[i].ct << "\t"
             << p[i].tat << "\t"
             << p[i].wt << endl;
    }

    cout << "\nAverage Waiting Time: "
         << totalWT / n << endl;

    cout << "Average Turnaround Time: "
         << totalTAT / n << endl;

    // Gantt Chart
    cout << "\nGantt Chart:\n";

    int start = 0;

    for (int i = 1; i < order.size(); i++)
    {
        if (order[i] != order[i - 1])
        {
            if (order[start] == -1)
            {
                cout << "|\tIDLE\t";
            }
            else
            {
                cout << "|\tP" << p[order[start]].id << "\t";
            }

            start = i;
        }
    }

    // Print last process
    if (order[start] == -1)
    {
        cout << "|\tIDLE\t";
    }
    else
    {
        cout << "|\tP" << p[order[start]].id << "\t";
    }

    cout << "|\n";

    // Print time values
    cout << "0";

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