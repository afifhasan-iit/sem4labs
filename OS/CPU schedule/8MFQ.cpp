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
    int level;
    int ct;
    int tat;
    int wt;
    bool added;
};

int main()
{
    int n;
    int q1Time, q2Time;

    cout << "Enter number of processes: ";
    cin >> n;

    cout << "Enter Time Quantum for Queue 1: ";
    cin >> q1Time;

    cout << "Enter Time Quantum for Queue 2: ";
    cin >> q2Time;

    vector<Process> p(n);

    for (int i = 0; i < n; i++)
    {
        p[i].id = i + 1;
        p[i].added = false;
        p[i].level = 1;

        cout << "Enter Arrival Time and Burst Time for P"
             << p[i].id << ": ";

        cin >> p[i].at >> p[i].bt;

        p[i].remaining = p[i].bt;
    }

    queue<int> q1;
    queue<int> q2;
    queue<int> q3;

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
        // Add newly arrived processes to Queue 1
        for (int i = 0; i < n; i++)
        {
            if (!p[i].added && p[i].at <= time)
            {
                q1.push(i);
                p[i].added = true;
            }
        }

        int index = -1;
        int runTime = 0;

        // Queue 1 - Highest Priority
        if (!q1.empty())
        {
            index = q1.front();
            q1.pop();

            runTime = q1Time;

            if (p[index].remaining < runTime)
            {
                runTime = p[index].remaining;
            }

            p[index].level = 1;
        }

        // Queue 2 - Medium Priority
        else if (!q2.empty())
        {
            index = q2.front();
            q2.pop();

            runTime = q2Time;

            if (p[index].remaining < runTime)
            {
                runTime = p[index].remaining;
            }

            p[index].level = 2;
        }

        // Queue 3 - Lowest Priority
        else if (!q3.empty())
        {
            index = q3.front();
            q3.pop();

            runTime = p[index].remaining;

            p[index].level = 3;
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

            order.push_back(-1);
            startTime.push_back(time);

            time = nextArrival;

            finishTime.push_back(time);

            continue;
        }

        int start = time;

        // Execute process
        p[index].remaining -= runTime;
        time += runTime;

        // Gantt Chart
        order.push_back(index);
        startTime.push_back(start);
        finishTime.push_back(time);

        // Add newly arrived processes
        for (int i = 0; i < n; i++)
        {
            if (!p[i].added && p[i].at <= time)
            {
                q1.push(i);
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
            // Demote the process to the next queue
            if (p[index].level == 1)
            {
                q2.push(index);
            }
            else if (p[index].level == 2)
            {
                q3.push(index);
            }
            else
            {
                q3.push(index);
            }
        }
    }

    cout << "\nMultilevel Feedback Queue Scheduling\n";

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