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
    int queue;
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

    cout << "Enter Time Quantum for Queue 1: ";
    cin >> quantum;

    vector<Process> p(n);

    for (int i = 0; i < n; i++)
    {
        p[i].id = i + 1;
        p[i].added = false;

        cout << "Enter Arrival Time, Burst Time and Queue Number for P"
             << p[i].id << ": ";

        cin >> p[i].at >> p[i].bt >> p[i].queue;

        p[i].remaining = p[i].bt;
    }

    queue<int> q1;
    queue<int> q2;

    int time = 0;
    int completed = 0;

    float totalWT = 0;
    float totalTAT = 0;

    // Gantt Chart
    vector<int> order;
    vector<int> startTime;
    vector<int> finishTime;

    while (completed < n)
    {
        // Add newly arrived processes
        for (int i = 0; i < n; i++)
        {
            if (!p[i].added && p[i].at <= time)
            {
                if (p[i].queue == 1)
                {
                    q1.push(i);
                }
                else
                {
                    q2.push(i);
                }

                p[i].added = true;
            }
        }

        // Queue 1 has higher priority
        if (!q1.empty())
        {
            int index = q1.front();
            q1.pop();

            int runTime = quantum;

            if (p[index].remaining < quantum)
            {
                runTime = p[index].remaining;
            }

            int start = time;

            p[index].remaining -= runTime;
            time += runTime;

            // Add to Gantt Chart
            order.push_back(index);
            startTime.push_back(start);
            finishTime.push_back(time);

            // Add newly arrived processes
            for (int i = 0; i < n; i++)
            {
                if (!p[i].added && p[i].at <= time)
                {
                    if (p[i].queue == 1)
                    {
                        q1.push(i);
                    }
                    else
                    {
                        q2.push(i);
                    }

                    p[i].added = true;
                }
            }

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
                // Put it back at the end of Queue 1
                q1.push(index);
            }
        }

        // Queue 2 uses FCFS
        else if (!q2.empty())
        {
            int index = q2.front();
            q2.pop();

            int start = time;

            // Run Queue 2 process until it finishes
            time += p[index].remaining;

            p[index].remaining = 0;

            // Add to Gantt Chart
            order.push_back(index);
            startTime.push_back(start);
            finishTime.push_back(time);

            p[index].ct = time;
            p[index].tat = p[index].ct - p[index].at;
            p[index].wt = p[index].tat - p[index].bt;

            totalWT += p[index].wt;
            totalTAT += p[index].tat;

            completed++;

            // Add newly arrived processes
            for (int i = 0; i < n; i++)
            {
                if (!p[i].added && p[i].at <= time)
                {
                    if (p[i].queue == 1)
                    {
                        q1.push(i);
                    }
                    else
                    {
                        q2.push(i);
                    }

                    p[i].added = true;
                }
            }
        }

        // CPU is idle
        else
        {
            int nextArrival = 999999;

            for (int i = 0; i < n; i++)
            {
                if (!p[i].added && p[i].at < nextArrival)
                {
                    nextArrival = p[i].at;
                }
            }

            int start = time;

            time = nextArrival;

            order.push_back(-1);
            startTime.push_back(start);
            finishTime.push_back(time);
        }
    }

    cout << "\nMultilevel Queue Scheduling\n";

    cout << "PID\tAT\tBT\tQueue\tCT\tTAT\tWT\n";

    for (int i = 0; i < n; i++)
    {
        cout << "P" << p[i].id << "\t"
             << p[i].at << "\t"
             << p[i].bt << "\t"
             << p[i].queue << "\t"
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