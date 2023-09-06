package shop.support.ipwebshopsupport.beans;

import java.io.Serializable;

public class HelloWorldBean implements Serializable {

    private String name;
    public HelloWorldBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String sayHello(){
        return "Hello "+name+"!";
    }
}
