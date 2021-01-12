package com.hdevs;

import java.util.Comparator;

public class FreqComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getFreq()>o2.getFreq())
            return 1;
        else if(o1.getFreq()<o2.getFreq())
            return -1;

        return 0;
    }


}
