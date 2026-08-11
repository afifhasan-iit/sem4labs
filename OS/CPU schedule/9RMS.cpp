#include <iostream>
#include <vector>
using namespace std;

struct Process
{
    int id;
    int execution;
    int period;
    int priority;
    int remaining;
};

int main()
{
    int n;
    int simulationTime;

    cout << "Enter number of processes: ";
    cin >> n;

    cout << "Enter Simulation Time: ";
    cin >> simulationTime;

    vector<Process> p(n);

    for (int i = 0; i < n; i++)
    {
        p[i].id = i + 1;

        cout << "Enter Execution Time and Period for P"
             << p[i].id << ": ";

        cin >> p[i].execution >> p[i].period;

        p[i].remaining = 0;
    }

    // Assign priority according to period
    // Smaller period = Higher priority
    for (int i = 0; i < n; i++)
    {
        p[i].priority = p[i].period;
    }

    vector<int> order;
    vector<int> startTime;
    vector<int> finishTime;

    int time = 0;

    while (time < simulationTime)
    {
        int index = -1;
        int highestPriority = 999999;

        // Check which process has a job ready
        for (int i = 0; i < n; i++)
        {
            // Release a new job at the beginning of every period
            if (time % p[i].period == 0)
            {
                if (p[i].remaining == 0)
                {
                    p[i].remaining = p[i].execution;
                }
            }

            // Find highest priority ready process
            if (p[i].remaining > 0)
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
        }
        else
        {
            order.push_back(index);
            startTime.push_back(time);

            // Execute for 1 unit
            p[index].remaining--;

            time++;

            finishTime.push_back(time);
        }
    }

    cout << "\nRate Monotonic Scheduling\n";

    cout << "PID\tExecution\tPeriod\tPriority\n";

    for (int i = 0; i < n; i++)
    {
        cout << "P" << p[i].id << "\t"
             << p[i].execution << "\t\t"
             << p[i].period << "\t"
             << p[i].priority << endl;
    }

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

    // Print last block
    if (order[start] == -1)
    {
        cout << "|\tIDLE\t";
    }
    else
    {
        cout << "|\tP" << p[order[start]].id << "\t";
    }

    cout << "|\n";

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