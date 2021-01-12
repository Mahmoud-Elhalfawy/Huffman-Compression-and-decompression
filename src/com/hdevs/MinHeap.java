package com.hdevs;

public class MinHeap {
    public int[] Heap;
    private int size;
    private int maxsize;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    public MinHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MAX_VALUE;
    }

    // Returns position of parent
    private int parent(int pos)
    {
        return pos / 2;
    }

    // Below two functions return left and
    // right children.
    private int leftChild(int pos)
    {
        int y=2*pos;
        int x=(y<=size)?y:0;
        return x;
    }
    private int rightChild(int pos)
    {
        int y=2*pos+1;
        int x=(y<=size)?y:y-1;
        return x;
    }

    // Returns true of given node is leaf
    private boolean isLeaf(int pos)
    {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos)
    {
        int tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    public void minHeapify(int pos)
    {
        int smallest = 0;
        int l=leftChild(pos);
        int r=rightChild(pos);
        if (isLeaf(pos))
            return;

        if (Heap[l] < Heap[pos]) {
            smallest=l;
        }
        else {
            smallest=pos;
        }
        if(Heap[r]<Heap[smallest])
            smallest=r;

        if(smallest!=pos) {
            swap(smallest, pos);
            minHeapify(smallest);
        }
    }

    // Inserts a new element to max heap
    public void insert(int element)
    {
        Heap[++size] = element;

        // Traverse up and fix violated property
        int current = size;
        while (Heap[current] > Heap[parent(current)] && current>0) {
            swap(current, parent(current));
            current = parent(current);
        }


    }

    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + Heap[i] + " LEFT CHILD : " +
                    Heap[2 * i] + " RIGHT CHILD :" + Heap[2 * i + 1]);
            System.out.println();
        }
    }

    // Remove an element from max heap
    public int extractMin()
    {
        int popped = Heap[1];
        Heap[1] = Heap[size--];
        minHeapify(1);
        return popped;
    }



}
