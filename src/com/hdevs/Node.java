package com.hdevs;

public class Node {
    private Node parent;
    private Node left;
    private Node right;
    private Character character;
    private int freq;
    private String binaryEq;



    public Node(Character character,int freq){
        this.left=null;
        this.right=null;
        this.character=character;
        this.freq = freq;

    }

    public Node(Character character, int freq, Node left, Node right){
        this.character=character;
        this.left=left;
        this.right=right;
        this.freq = freq;

    }


public Character getCharacter(){
        return this.character;
}

public void setCharacter(Character character){
        this.character=character;
}

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getBinaryEq() {
        return binaryEq;
    }

    public void setBinaryEq(String binaryEq) {
        this.binaryEq = binaryEq;
    }


}
