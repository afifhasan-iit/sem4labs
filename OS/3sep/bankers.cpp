#include <iostream>
#include <vector>
using namespace std;

bool isSafe(vector<vector<int>>& allocation, vector<vector<int>>& maximum, vector<int>& available,
            int n, int m) {
                
    vector<vector<int>> need(n, vector<int>(m));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            need[i][j] = maximum[i][j] - allocation[i][j];
        }
    }
    cout << "\nNeed Matrix:\n";
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cout << need[i][j] << " ";
        }
        cout << endl;
    }
    vector<int> work = available;
    vector<bool> finish(n, false);
    vector<int> safeSequence;

    while ((int)safeSequence.size() < n) {  // fixed: cast to int
        bool found = false;
        for (int i = 0; i < n; i++) {
            if (!finish[i]) {
                bool canExecute = true;
                for (int j = 0; j < m; j++) {
                    if (need[i][j] > work[j]) {
                        canExecute = false;
                        break;
                    }
                }

                if (canExecute) {
                    for (int j = 0; j < m; j++) {
                        work[j] += allocation[i][j];
                    }
                    finish[i] = true;
                    safeSequence.push_back(i);
                    found = true;
                }
            }
        }
        if (!found) {
            cout << "\nSystem is in an UNSAFE state!" << endl;
            return false;
        }
    }

    cout << "\nSystem is in a SAFE state." << endl;
    cout << "Safe Sequence: ";
    for (int i = 0; i < n; i++) {
        cout << "P" << safeSequence[i];
        if (i != n - 1) {
            cout << " -> ";
        }
    }
    cout << endl;
    return true;
}

int main() {
    int n = 5;
    int m = 3;

    // SAFE STATE EXAMPLE
    vector<vector<int>> allocation1 = {
        {0, 1, 0},
        {2, 0, 0},
        {3, 0, 2},
        {2, 1, 1},
        {0, 0, 2}
    };

    vector<vector<int>> maximum1 = {
        {7, 5, 3},
        {3, 2, 2},
        {9, 0, 2},
        {2, 2, 2},
        {4, 3, 3}
    };

    vector<int> available1 = {3, 3, 2};


    // UNSAFE STATE EXAMPLE
    vector<vector<int>> allocation2 = {
        {0, 1, 0},
        {2, 0, 0},
        {3, 0, 2},
        {2, 1, 1},
        {0, 0, 2}
    };

    vector<vector<int>> maximum2 = {
        {7, 5, 3},
        {3, 2, 2},
        {9, 0, 2},
        {2, 2, 2},
        {4, 3, 3}
    };

    vector<int> available2 = {0, 0, 0};


    cout << "Safe example:\n";
    isSafe(allocation1, maximum1, available1, n, m);

    cout << "\nUnsafe example:\n";
    isSafe(allocation2, maximum2, available2, n, m);

    return 0;
}