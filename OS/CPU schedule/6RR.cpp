#include <iostream>
#include <vector>
#include <queue>
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
    bool added;
};

int main()
{
    int n;
    int quantum;

    cout << "Enter number of processes: ";
    cin >> n;

    cout << "Enter Time Quantum: ";
    cin >> quantum;

    vector<Process> p(n);

    for (int i = 0; i < n; i++)
    {
        p[i].id = i + 1;
        p[i].added = false;

        cout << "Enter Arrival Time and Burst Time for P"
             << p[i].id << ": ";

        cin >> p[i].at >> p[i].bt;

        p[i].remaining = p[i].bt;
    }

    int time = 0;
    int completed = 0;

    float totalWT = 0;
    float totalTAT = 0;

    queue<int> q;

    // Gantt Chart data
    vector<int> order;
    vector<int> startTime;
    vector<int> finishTime;

    while (completed < n)
    {
        // Add newly arrived processes to queue
        for (int i = 0; i < n; i++)
        {
            if (!p[i].added && p[i].at <= time)
            {
                q.push(i);
                p[i].added = true;
            }
        }

        // If queue is empty, CPU is idle
        if (q.empty())
        {
            int nextArrival = 999999;

            for (int i = 0; i < n; i++)
            {
                if (!p[i].added && p[i].at < nextArrival)
                {
                    nextArrival = p[i].at;
                }
            }

            order.push_back(-1);
            startTime.push_back(time);

            time = nextArrival;

            finishTime.push_back(time);

            continue;
        }

        int index = q.front();
        q.pop();

        int start = time;

        // Execute for one time quantum
        int runTime = quantum;

        if (p[index].remaining < quantum)
        {
            runTime = p[index].remaining;
        }

        p[index].remaining -= runTime;
        time += runTime;

        // Add newly arrived processes
        for (int i = 0; i < n; i++)
        {
            if (!p[i].added && p[i].at <= time)
            {
                q.push(i);
                p[i].added = true;
            }
        }

        // Gantt Chart
        order.push_back(index);
        startTime.push_back(start);
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
        else
        {
            // Put the process back into the queue
            q.push(index);
        }
    }

    cout << "\nRound Robin Scheduling\n";

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
        if (order[i] == -1)
        {
            cout << "|\tIDLE\t";
        }
        else
        {
            cout << "|\tP" << p[order[i]].id << "\t";
        }
    }

    cout << "|\n";

    cout << startTime[0];

    for (int i = 0; i < finishTime.size(); i++)
    {
        cout << "\t\t" << finishTime[i];
    }

    cout << endl;

    return 0;
}