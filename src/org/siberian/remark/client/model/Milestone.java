package org.siberian.remark.client.model;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: petergershkovich
 * Date: 8/10/13
 * Time: 9:54 AM
 */
public class Milestone {

    private int seq;

    private String name;

    private LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public LinkedList<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(LinkedList<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
