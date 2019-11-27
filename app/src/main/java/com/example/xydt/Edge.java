package com.example.xydt;

public class Edge {
    public int from;
    public int to;
    public double length;
    public int index;
    public Edge(int from,int to,double length,int index){
        this.from=from;
        this.to=to;
        this.length=length;
        this.index=index;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", length=" + length +
                ", index=" + index +
                '}';
    }
}
