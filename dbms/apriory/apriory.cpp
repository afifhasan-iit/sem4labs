#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <string>
#include <algorithm>

using namespace std;



map<int, string> itemDictionary = {
    {0, "milk"},
    {1, "bread"},
    {2, "butter"},
    {3, "eggs"}
};

string getItemName(int itemID) {
    return itemDictionary[itemID];
}


vector<vector<int>> getTransactions() {
    //0 = milk, 1 = bread, 2 = butter, 3 = eggs

    vector<vector<int>> transactions = {
        {0, 1, 2},    
        {0, 1},       
        {1, 2, 3},    
        {0, 3},       
        {0, 1, 3},    
        {1, 2, 0}     
    };

    return transactions;
}



// itemset{0, 1} -> {milk, bread}
string itemsetToString(set<int>& itemset) {
    string result = "{";
    bool first = true;
    for (int itemID : itemset) {
        if (!first) result += ", ";
        result += getItemName(itemID);
        first = false;
    }
    result += "}";
    return result;
}

void printTransactions(vector<vector<int>>& transactions) {
    cout << " ## TRANSACTIONS ##\n";
    for (int i = 0; i < transactions.size(); i++) {
        cout << "T" << i + 1 << ": ";
        for (int itemID : transactions[i])
            cout << getItemName(itemID) << " ";
        cout << "\n";
    }
}



// count how many transactions contain ALL items in the given itemset
int calculateSupport(set<int>& itemset, vector<vector<int>>& transactions) {
    int count = 0;
    for (auto& transaction : transactions) {
        bool allItemsFound = true;
        for (int itemID : itemset) {
            if (find(transaction.begin(), transaction.end(), itemID) == transaction.end()) {
                allItemsFound = false;
                break;
            }
        }
        if (allItemsFound) count++;
    }
    return count;
}



//get all combination of size k+1 from frequent itemsets of size k
vector<set<int>> generateCandidates(vector<set<int>>& frequentItemsets) {
    vector<set<int>> candidates;

    // try all possible pairs
    for (int i = 0; i < frequentItemsets.size(); i++) {
        for (int j = i + 1; j < frequentItemsets.size(); j++) {

            set<int> merged;
            for (int itemID : frequentItemsets[i]) merged.insert(itemID);
            for (int itemID : frequentItemsets[j]) merged.insert(itemID);

            // only keep if size grew by exactly 1 
            if (merged.size() == frequentItemsets[i].size() + 1)
                candidates.push_back(merged);
        }
    }


    sort(candidates.begin(), candidates.end());
    candidates.erase(unique(candidates.begin(), candidates.end()), candidates.end());

    return candidates;
}



map<set<int>, int> runApriori(vector<vector<int>>& transactions, int minSupport) {

    map<set<int>, int> allFrequentItemsets;

    // count each individual item from all transactions 
    map<int, int> singleItemCounts;    // itemID -> number of items in transactions
    for (auto& transaction : transactions)
        for (int itemID : transaction)
            singleItemCounts[itemID]++;



    // filter out items that do not meet minSupport
    vector<set<int>> currentLevelFrequent;
    for (auto& [itemID, count] : singleItemCounts) {
        if (count >= minSupport) {
            set<int> singleItemset = {itemID};
            currentLevelFrequent.push_back(singleItemset);
            allFrequentItemsets[singleItemset] = count;
        }
    }

    //  if grow bigger
    while (!currentLevelFrequent.empty()) {


        vector<set<int>> candidateSets = generateCandidates(currentLevelFrequent);

        vector<set<int>> nextLevelFrequent;
        for (auto& candidate : candidateSets) {
            int support = calculateSupport(candidate, transactions);
            if (support >= minSupport) {
                nextLevelFrequent.push_back(candidate);
                allFrequentItemsets[candidate] = support;
            }
        }

        currentLevelFrequent = nextLevelFrequent;   
    }

    return allFrequentItemsets;
}



void printFrequentItemsets(map<set<int>, int>& allFrequentItemsets) {
    // group itemsets by size  {size -> list of (itemset, support) pairs}
    map<int, vector<pair<set<int>, int>>> groupedBySize;
    for (auto& [itemset, support] : allFrequentItemsets)
        groupedBySize[itemset.size()].push_back({itemset, support});

    cout << "\n===== FREQUENT ITEMSETS =====\n";
    for (auto& [size, itemsets] : groupedBySize) {
        cout << "\nFrequent " << size << "-itemsets:\n";
        for (auto& [itemset, support] : itemsets)
            cout << "  " << itemsetToString(itemset) << "   support: " << support << "\n";
    }
}



void findSubsets(vector<int>& items, int start, set<int>& current, vector<set<int>>& result) {

    if (!current.empty() && current.size() < items.size())
        result.push_back(current);

    for (int i = start; i < items.size(); i++) {
        current.insert(items[i]);              
        findSubsets(items, i + 1, current, result); 
        current.erase(items[i]);               
    }
}


vector<set<int>> getAllSubsets(set<int>& itemset) {
    vector<int> items(itemset.begin(), itemset.end());  
    vector<set<int>> subsets;
    set<int> current;
    findSubsets(items, 0, current, subsets);
    return subsets;
}

void printAssociationRules(map<set<int>, int>& allFrequentItemsets, double minConfidence) {
    cout << "\n===== ASSOCIATION RULES (confidence >= "
         << (int)(minConfidence * 100) << "%) =====\n\n";

    bool anyRuleFound = false;

    for (auto& [itemset, itemsetSupport] : allFrequentItemsets) {
        if (itemset.size() < 2) continue;   // need at least 2 items to form a rule

        // get all possible left sides of the rule
        set<int> itemsetCopy = itemset;     // copy needed since map keys are const internally
        vector<set<int>> leftSideCandidates = getAllSubsets(itemsetCopy);

        for (auto& leftSide : leftSideCandidates) {
            // right side = itemset - left side
            set<int> rightSide;
            for (int itemID : itemset)
                if (leftSide.find(itemID) == leftSide.end())
                    rightSide.insert(itemID);

            if (rightSide.empty()) continue;

            // confidence = support(full itemset) / support(left side)
            int leftSideSupport = allFrequentItemsets.count(leftSide)
                                        ? allFrequentItemsets.at(leftSide)      
                                        : 0;

            if (leftSideSupport == 0) continue;

            double confidence = (double)itemsetSupport / leftSideSupport;

            if (confidence >= minConfidence) {
                cout << itemsetToString(leftSide)
                     << "  =>  "
                     << itemsetToString(rightSide)
                     << "   confidence: " << (int)(confidence * 100) << "%"
                     << "   support: " << itemsetSupport << "\n";
                anyRuleFound = true;
            }
        }
    }

    if (!anyRuleFound)
        cout << "No rules found with this confidence threshold.\n";
}



int main() {
    int    minSupport    = 2;
    double minConfidence = 0.6;   // 60%

    vector<vector<int>> transactions = getTransactions();

    // printTransactions(transactions);
    // cout << "\nMinimum Support:    " << minSupport << "\n";
    // cout << "Minimum Confidence: " << (int)(minConfidence * 100) << "%\n";

    map<set<int>, int> allFrequentItemsets = runApriori(transactions, minSupport);

    printFrequentItemsets(allFrequentItemsets);

    printAssociationRules(allFrequentItemsets, minConfidence);

    return 0;
}
