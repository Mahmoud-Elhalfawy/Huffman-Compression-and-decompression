package com.hdevs;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class HuffmanTree {
    PriorityQueue<Node> priorityQueue;
    public HashMap<Character,Integer> frequencies;

    public void setEncodedMap(HashMap<Character, String> encodedMap) {
        this.encodedMap = encodedMap;
    }

    private HashMap<Character, String> encodedMap=new HashMap<>();
    private StringBuilder encodedBuilder;
    private StringBuilder decodedBuilder=new StringBuilder();
    public  Node root;


    public HuffmanTree(){
        this.frequencies =Main.frequencies;
        priorityQueue=new PriorityQueue<>(new FreqComparator());
    }


    public HashMap<Character,String> buildTree(){
        fillQueue();
        while(priorityQueue.size()!=1){
        Node left=priorityQueue.poll();
        Node right=priorityQueue.poll();
        priorityQueue.add(new Node(null,left.getFreq()+right.getFreq(),left,right));

        }

         root=priorityQueue.peek();
        encode(root,"");
        StringBuilder codes=new StringBuilder();
        System.out.println("* Character\t\t Code\t\t New Code\t\t\t\t");
        codes.append("* Character\t\t Code\t\t New Code\t\t\t\t\n");
        for (Map.Entry<Character, String> entry:encodedMap.entrySet()) {
            String code= "* "+entry.getKey() + "\t\t\t\t"
                    +"| "+Integer.toBinaryString((int)entry.getKey().charValue()) + "\t\t"
                    +"| "+entry.getValue()+ "\t\t";
            System.out.println(code);
            codes.append(code).append("\n");
        }

        return encodedMap;
    }


    public void fillQueue(){
        for (Map.Entry<Character, Integer> entry:frequencies.entrySet())
            priorityQueue.add(new Node(entry.getKey(),entry.getValue()));
    }

    public void setEncodedBuilder(StringBuilder encodedBuilder) {
        this.encodedBuilder = encodedBuilder;
    }

    public void encode(Node root, String binaryEq){

            if (root == null) {
                return;
            }

            // Found a leaf node
            if (isLeaf(root)) {
                encodedMap.put(root.getCharacter(), binaryEq.length() > 0 ? binaryEq : "1");
                root.setBinaryEq(binaryEq.length() > 0 ? binaryEq : "1");
            }

            encode(root.getLeft(), binaryEq + '0');
            encode(root.getRight(), binaryEq + '1');
        }



public boolean isLeaf(Node node){

    return node.getLeft() == null && node.getRight() == null;

}


public String printEncoded(String file){



     encodedBuilder = new StringBuilder();
    for (char c: file.toCharArray()) {
        encodedBuilder.append(encodedMap.get(c));
    }

    return encodedBuilder.toString();


}

    public  int decode(Node root, int chPointer, StringBuilder encodedBuilder)
    {
        if (root == null) {
            return chPointer;
        }

        // Found a leaf node
        if (isLeaf(root)) {
            decodedBuilder.append(root.getCharacter());
            return chPointer;
        }

        chPointer++;

        root = (encodedBuilder.charAt(chPointer) == '0') ? root.getLeft() : root.getRight();
        chPointer = decode(root, chPointer, encodedBuilder);
        return chPointer;
    }


    public String printDecoded() {

            int chPointer = -1;
            while (chPointer < encodedBuilder.length() - 1) {
                chPointer = decode(root, chPointer, encodedBuilder);
            }

            return decodedBuilder.toString();
    }

}
