package shop.support.ipwebshopsupport.beans;

import java.io.Serializable;

public class MessageBean implements Serializable {

    private Integer id;
    private String text;
    private boolean seen;
    private String userId;

    public MessageBean(){}

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getUserId() {
        return userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

