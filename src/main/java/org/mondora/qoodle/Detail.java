package org.mondora.qoodle;

import java.util.ArrayList;

public class Detail {

    private String what="";
    private ArrayList<SingleVote> whos;

    public Detail() {
        whos = new ArrayList<>();
    }

    public Detail(String what, ArrayList<SingleVote> whos) {
        this.what = what;
        this.whos = whos;
    }

    public Detail(String what) {
        this.what = what;
        whos = new ArrayList<>();
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public ArrayList<SingleVote> getWhos() {
        return whos;
    }

    public void setWhos(ArrayList<SingleVote> whos) {
        this.whos = whos;
    }

    public void addWho(SingleVote who)
    {
        this.whos.add(who);
    }


    @Override
    public String toString() {
        return "Detail{" +
                "what='" + what + '\'' +
                ", whos=" + whos +
                '}';
    }
}
