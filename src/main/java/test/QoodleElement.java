package test;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class QoodleElement extends Insertable{

    @Id
    private long elId;
    private String name;
    private int min;
    private int max;
    private String umoption;
    private String coinoption = "€";
    private float price;
    private int counter;
    private String imgUrl;

    public QoodleElement() {
        this.elId = 0;
        this.name = "";
        this.min = 0;
        this.max = 99999;
        this.umoption = "";
        this.coinoption = "";
        this.price = 0.0f;
        this.counter = 0;
        this.imgUrl = "";
    }


    public QoodleElement(long elId, String name, String umoption, String coinoption, float price, int counter, String imgUrl) {
        this.elId = elId;
        this.name = name;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }


    public QoodleElement(String name, String umoption, String coinoption, float price, int counter, String imgUrl) {
        this.name = name;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }

    public QoodleElement(long elId, String name, String umoption, float price, int counter, String imgUrl) {
        this.elId = elId;
        this.name = name;
        this.umoption = umoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }

    public QoodleElement(long elId, String name, String umoption, String coinoption, int counter, String imgUrl) {
        this.elId = elId;
        this.name = name;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = 0.0f;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }


    public QoodleElement(long elId, String name, int min, int max, String umoption, String coinoption, float price, int counter, String imgUrl) {
        this.elId = elId;
        this.name = name;
        this.min = min;
        this.max = max;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }

    public QoodleElement(String name, int min, int max, String umoption, String coinoption, float price, int counter, String imgUrl) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }
    public QoodleElement(String name, int min, int max, String coinoption, float price, int counter, String imgUrl) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.umoption = umoption;
        this.coinoption = coinoption;
        this.price = price;
        this.counter = counter;
        this.imgUrl = imgUrl;
    }


    public long getElId() {
        return elId;
    }

    public void setElId(long elId) {
        this.elId = elId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getUmoption() {
        return umoption;
    }

    public void setUmoption(String umoption) {
        this.umoption = umoption;
    }

    public String getCoinoption() {
        return coinoption;
    }

    public void setCoinoption(String coinoption) {
        this.coinoption = coinoption;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    @Override
    public String toString() {
        return "QoodleElement{" +
                "elId=" + elId +
                ", name='" + name + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", umoption='" + umoption + '\'' +
                ", coinoption='" + coinoption + '\'' +
                ", price=" + price +
                ", counter=" + counter +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    @Override
    public void insert( String name, Datastore ds) {

        long nuovoId = this.inserisci(name, ds);
            this.setElId(nuovoId);
            ds.save(this);

    }
}
