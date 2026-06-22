#include <bits/stdc++.h>
using namespace std;

const int ORDER = 3; // order = max children per internal node, or max keys per leaf node


struct Node
{
    vector<int> keys; // keys storage
    vector<Node *> child; // child pointers (only for internal nodes)
    Node *next; 
    int keyCount;
    bool isLeaf;
    int maxKeys;

    Node(bool leaf)
    {
        isLeaf = leaf;
        keyCount = 0;
        next = NULL;

        if (isLeaf)
        {
            maxKeys = ORDER;
            keys.assign(ORDER + 1, 0);
        }
        else
        {
            maxKeys = ORDER - 1;
            keys.assign(ORDER, 0);
            child.assign(ORDER + 2, NULL);      //2 extra for temporary storage during splits (one extra key and one extra child pointer)
        }
    }
};


void displayTree(Node *root, int level = 0)
{
    if (root == NULL)
        return;

    cout << "Level " << level << " [" << (root->isLeaf ? "LEAF" : "INTERNAL") << "] : ";

    for (int i = 0; i < root->keyCount; i++)
    {
        cout << root->keys[i] << " ";
    }
    cout << endl;

    if (!root->isLeaf)
    {
        for (int i = 0; i <= root->keyCount; i++)
        {
            displayTree(root->child[i], level + 1);
        }
    }
}

//printing all the leaf nodes in a chain using next pointer (left to right)
void displayLeafChain(Node *root)
{
    if (root == NULL)
        return;

    // find leftmost leaf
    Node *current = root;
    while (!current->isLeaf)
    {
        current = current->child[0];    //child[0] is the leftmost child of every node
    }

    cout << "Leaf chain: ";
    while (current != NULL)
    {
        cout << "[ ";
        for (int i = 0; i < current->keyCount; i++)
        {
            cout << current->keys[i] << " ";
        }
        cout << "] ";
        if (current->next != NULL)
            cout << "-> ";
        current = current->next;
    }
    cout << endl;
}

// return pointer to leaf node where key should be (or is) located, returns NULL if tree is empty
Node *findLeaf(Node *root, int key)
{
    if (root == NULL)
        return NULL;

    Node *current = root;

    while (!current->isLeaf)
    {
        int i = 0;
        while (i < current->keyCount && key >= current->keys[i])
        {
            i++;
        }
        current = current->child[i];
    }

    return current; //if not found returns leaf where it should be, if found also returns the same leaf
}

bool search(Node *root, int key)    //return true if key is found, false otherwise
{
    Node *leaf = findLeaf(root, key);

    if (leaf == NULL)
        return false;

    for (int i = 0; i < leaf->keyCount; i++)
    {
        if (leaf->keys[i] == key)
            return true;
    }

    return false;
}

// target leaf is given, 
void insertInLeaf(Node *leaf, int key)      //inserts key into leaf node in sorted order (assumes leaf has space)
{                                           
    int i = leaf->keyCount - 1;

    while (i >= 0 && leaf->keys[i] > key)
    {
        leaf->keys[i + 1] = leaf->keys[i];  //push bigger keys to right
        i--;
    }

    leaf->keys[i + 1] = key;
    leaf->keyCount++;
}

// target node is given, insert key and right child in sorted order (assumes internal node has space)
void insertInInternal(Node *node, int key, Node *rightChild)    
{
    int i = node->keyCount - 1;

    while (i >= 0 && node->keys[i] > key)   //key should go left
    {
        node->keys[i + 1] = node->keys[i];
        node->child[i + 2] = node->child[i + 1];
        i--;
    }

    node->keys[i + 1] = key;
    node->child[i + 2] = rightChild;
    node->keyCount++;
}


// splits a leaf when full and new key needs to be inserted, returns new leaf node created after split
Node *splitLeaf(Node *leaf, int key)
{
    // create temp array with all keys including new key
    vector<int> temp(ORDER + 1, 0);
    int i = 0, j = 0;

    while (i < leaf->keyCount && leaf->keys[i] < key)   //helps cut the array in perfect halves when we insert the new key in sorted order
    {
        temp[j++] = leaf->keys[i++];
    }
    temp[j++] = key;
    while (i < leaf->keyCount)
    {
        temp[j++] = leaf->keys[i++];
    }

    Node *newLeaf = new Node(true);

    int split = (ORDER + 1) / 2;

    leaf->keyCount = split;
    for (int i = 0; i < split; i++)
    {
        leaf->keys[i] = temp[i];
    }

    // right leaf gets remaining keys
    newLeaf->keyCount = (ORDER + 1) - split;
    for (int i = 0; i < newLeaf->keyCount; i++)
    {
        newLeaf->keys[i] = temp[split + i];
    }

    // link leaves  
    newLeaf->next = leaf->next;
    leaf->next = newLeaf;   //seq is leaf -> newLeaf -> old next leaf (if any)

    return newLeaf;      // first key of new leaf will be pushed up to parent as separator key
}

// splits an internal node when full and new key+child needs to be inserted, returns new internal node created after split
Node *splitInternal(Node *node, int key, Node *rightChild)
{
    // create temp arrays
    vector<int> tempKeys(ORDER, 0);
    vector<Node *> tempChild(ORDER + 1, NULL);

    int i = 0, j = 0;

    while (i < node->keyCount && node->keys[i] < key)
    {
        tempKeys[j] = node->keys[i];
        tempChild[j] = node->child[i];
        i++;
        j++;
    }

    tempKeys[j] = key;
    tempChild[j] = node->child[i];
    tempChild[j + 1] = rightChild;
    j++;

    while (i < node->keyCount)
    {
        tempKeys[j] = node->keys[i];
        tempChild[j + 1] = node->child[i + 1];
        i++;
        j++;
    }

    // split point
    int split = ORDER / 2;  

    node->keyCount = split;
    for (int i = 0; i < split; i++)
    {
        node->keys[i] = tempKeys[i];
        node->child[i] = tempChild[i];
    }
    node->child[split] = tempChild[split]; // one extra for ceiling

    Node *newInternal = new Node(false);

    // right node gets keys after split+1
    newInternal->keyCount = (ORDER - 1) - split;
    for (int i = 0; i < newInternal->keyCount; i++)
    {
        newInternal->keys[i] = tempKeys[split + 1 + i];
        newInternal->child[i] = tempChild[split + 1 + i];
    }
    newInternal->child[newInternal->keyCount] = tempChild[ORDER];

    
    return newInternal;
}

// key is on some children of node, but not exactly on node, so pass key to subtree and check if it causes split, if it does, handle it
Node *insertInternal(Node *node, int key, Node **childToInsert, int *keyToInsert)
{

    if (node->isLeaf)
    {
        if (node->keyCount < node->maxKeys)
        {
            // leaf not full
            insertInLeaf(node, key);
            *childToInsert = NULL;
            return node;    //return leaf node where key was inserted
        }
        else
        {
            // leaf full, need to split
            Node *newLeaf = splitLeaf(node, key);
            *keyToInsert = newLeaf->keys[0]; 
            *childToInsert = newLeaf;       
            return node;
        }
    }
    else
    {
        //  node is not lead, find child to go to
        int i = 0;
        while (i < node->keyCount && key >= node->keys[i])
        {
            i++;
        }

        Node *splitChild = NULL;
        int splitKey = 0;   

        insertInternal(node->child[i], key, &splitChild, &splitKey);

        if (splitChild == NULL)
        {
            *childToInsert = NULL;
            return node;
        }

        

        if (node->keyCount < node->maxKeys) // this node is not full, can insert splitKey and splitChild here
        {
            // internal node not full
            insertInInternal(node, splitKey, splitChild);       
            return node;
        }
        else
        {
            // internal node full, need to split
            Node *newInternal = splitInternal(node, splitKey, splitChild); 

            
            vector<int> tempKeys(ORDER, 0);
            vector<Node *> tempChild(ORDER + 1, NULL);

            int idx = 0, j = 0;
            while (idx < node->keyCount && node->keys[idx] < splitKey)
            {
                tempKeys[j] = node->keys[idx];
                tempChild[j] = node->child[idx];
                idx++;
                j++;
            }
            tempKeys[j] = splitKey;
            tempChild[j] = node->child[idx];
            tempChild[j + 1] = splitChild;
            j++;

            while (idx < node->keyCount)
            {
                tempKeys[j] = node->keys[idx];
                tempChild[j + 1] = node->child[idx + 1];
                idx++;
                j++;
            }

            int split = ORDER / 2;
            *keyToInsert = tempKeys[split]; 
            *childToInsert = newInternal;

            return node;
        }
    }
}

Node *insert(Node *root, int key)
{
    if (root == NULL)
    {
        root = new Node(true);
        root->keys[0] = key;
        root->keyCount = 1;
        return root;
    }

    Node *splitChild = NULL;    // if root splits, this will point to new node created after split
    int splitKey = 0;

    insertInternal(root, key, &splitChild, &splitKey);

    if (splitChild != NULL)
    {
        // root split, create new root
        Node *newRoot = new Node(false);
        newRoot->keys[0] = splitKey;
        newRoot->child[0] = root;
        newRoot->child[1] = splitChild;
        newRoot->keyCount = 1;
        return newRoot;
    }

    return root;
}





int main()
{
    Node *root = NULL;

    cout << "=== B+ Tree (Order " << ORDER << ") ===" << endl
         << endl;

    // Insertions
    cout << "Inserting: 5, 15, 25, 35, 45, 55, 40, 30, 20" << endl;
    vector<int> insertKeys = {5, 15, 25, 35, 45, 55, 40, 30, 20};
    for (int key : insertKeys)
    {
        root = insert(root, key);
    }

    cout << "\nTree structure:" << endl;
    displayTree(root);

    cout << "\n";
    displayLeafChain(root);

    // Search
    cout << "\n=== Search ===" << endl;
    vector<int> searchKeys = {25, 100};
    for (int key : searchKeys)
    {
        cout << "Search " << key << ": " << (search(root, key) ? "FOUND" : "NOT FOUND") << endl;
    }



    return 0;
}