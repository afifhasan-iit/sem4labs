#include <iostream>
#include <vector>
using namespace std;

struct Process
{
    int id;
    int at;
    int bt;
    int ct;
    int tat;
    int wt;
    bool done;
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
        p[i].done = false;

        cout << "Enter Arrival Time and Burst Time for P"
             << p[i].id << ": ";
        cin >> p[i].at >> p[i].bt;
    }

    int time = 0;
    int completed = 0;

    float totalWT = 0;
    float totalTAT = 0;

    // For Gantt Chart
    vector<string> order;
    vector<int> startTime;
    vector<int> finishTime;

    while (completed < n)
    {

        int index = -1;
        int shortestBT = 999999;

        // Find the shortest available process
        for (int i = 0; i < n; i++)
        {
            if (!p[i].done && p[i].at <= time)
            {

                if (p[i].bt < shortestBT)
                {
                    shortestBT = p[i].bt;
                    index = i;
                }
            }
        }

        // If no process is available, CPU is idle
        if (index == -1)
        {

            int nextArrival = 999999;

            for (int i = 0; i < n; i++)
            {
                if (!p[i].done && p[i].at < nextArrival)
                {
                    nextArrival = p[i].at;
                }
            }

            order.push_back("IDLE");
            startTime.push_back(time);

            time = nextArrival;

            finishTime.push_back(time);

            continue;
        }

        // Start executing the selected process
        startTime.push_back(time);

        time += p[index].bt;

        finishTime.push_back(time);

        order.push_back("P" + to_string(p[index].id));

        // Calculate times
        p[index].ct = time;
        p[index].tat = p[index].ct - p[index].at;
        p[index].wt = p[index].tat - p[index].bt;

        p[index].done = true;
        completed++;

        totalWT += p[index].wt;
        totalTAT += p[index].tat;
    }

    // Display result
    cout << "\nSJF Scheduling\n";

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

    cout << "\nAverage Waiting Time: "
         << totalWT / n << endl;

    cout << "Average Turnaround Time: "
         << totalTAT / n << endl;

    // Gantt Chart
    cout << "\nGantt Chart:\n";

    for (int i = 0; i < order.size(); i++)
    {
        cout << "|\t" << order[i] << "\t";
    }

    cout << "|\n";

    for (int i = 0; i < startTime.size(); i++)
    {
        cout << startTime[i] << "\t\t";
    }

    cout << finishTime.back() << endl;

    return 0;
}