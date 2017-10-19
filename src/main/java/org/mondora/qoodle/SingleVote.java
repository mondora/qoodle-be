package org.mondora.qoodle;

public class SingleVote {

    private String name = "";
    private String realName = "";
    private int count = 0;

    public SingleVote(String name, int count) {
        this.name = name;
        this.realName = "";
        this.count = count;
    }

    public SingleVote(String name, String realName, int count) {
        this.name = name;
        this.realName = realName;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "SingleVote{" + "name='" + name + '\'' + ", realName='" + realName + '\'' + ", count=" + count + '}';
    }
}
