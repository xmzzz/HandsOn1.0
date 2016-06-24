package com.xmz.handson10.data;

/**
 * Created by jinxu on 2016/6/21.
 */
public class River {

    private String type;

    private String name;

    private int num;

    private String function1;

    private String function2;

    private String function3;

    private String imageres;

    private String function;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFunction1() {
        return function1;
    }

    public void setFunction1(String function1) {
        this.function1 = function1;
    }

    public String getFunction2() {
        return function2;
    }

    public void setFunction2(String function2) {
        this.function2 = function2;
    }

    public String getFunction3() {
        return function3;
    }

    public void setFunction3(String function3) {
        this.function3 = function3;
    }

    public String getImageres() {
        return imageres;
    }

    public void setImageres(String imageres) {
        this.imageres = imageres;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return type + " " + name  + " " + num + " " + function1 + " " + function2
                + " " + function3 + " " + imageres + " " + function;
    }
}
