package org.example;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public ArrayList<Node> neighbours;
    Node parent;
    public int x;
    public int y;
    public int mark;
    public Node(Node parent){
        this.neighbours = new ArrayList<Node>();
        this.parent = parent;
        this.mark=0;
    }
}
