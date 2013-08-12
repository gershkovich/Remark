package org.siberian.remark.client.model;

/**
 * Created with IntelliJ IDEA.
 * User: petergershkovich
 * Date: 8/10/13
 * Time: 9:55 AM

 */
public class Evaluation {
    private int seq;

    private String category;

    private String text;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
