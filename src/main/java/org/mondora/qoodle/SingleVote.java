package org.mondora.qoodle;

public class SingleVote {

    private String name = "";
    private int count = 0;

    public SingleVote(String name, int count) {
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "SingleVote{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
