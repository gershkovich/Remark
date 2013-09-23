package org.siberian.remark.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: petergershkovich
 * Date: 8/10/13
 * Time: 9:54 AM
 */
public class EvaluationAxis implements IsSerializable
{
    private String id;

    private int seq;

    private String name;

    private String learningCategory;

    private String subSpecialty;

    private List<Evaluation> deficiency = new ArrayList<Evaluation>();

    private List<Evaluation> progress1 = new ArrayList<Evaluation>();

    private List<Evaluation> progress2 = new ArrayList<Evaluation>();

    private List<Evaluation> unsupervised = new ArrayList<Evaluation>();

    private List<Evaluation> aspirational = new ArrayList<Evaluation>();

    private List<Evaluation> evaluations = new ArrayList<Evaluation>();

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



    public String getLearningCategory()
    {
        return learningCategory;
    }

    public void setLearningCategory(String learningCategory)
    {
        this.learningCategory = learningCategory;
    }

    public String getSubSpecialty()
    {
        return subSpecialty;
    }

    public void setSubSpecialty(String subSpecialty)
    {
        this.subSpecialty = subSpecialty;
    }

    public List<Evaluation> getDeficiency()
    {
        return deficiency;
    }

    public void setDeficiency(List<Evaluation> deficiency)
    {
        this.deficiency = deficiency;
    }

    public List<Evaluation> getProgress1()
    {
        return progress1;
    }

    public void setProgress1(List<Evaluation> progress1)
    {
        this.progress1 = progress1;
    }

    public List<Evaluation> getProgress2()
    {
        return progress2;
    }

    public void setProgress2(List<Evaluation> progress2)
    {
        this.progress2 = progress2;
    }

    public List<Evaluation> getUnsupervised()
    {
        return unsupervised;
    }

    public void setUnsupervised(List<Evaluation> unsupervised)
    {
        this.unsupervised = unsupervised;
    }

    public List<Evaluation> getAspirational()
    {
        return aspirational;
    }

    public void setAspirational(List<Evaluation> aspirational)
    {
        this.aspirational = aspirational;
    }

    public List<Evaluation> getEvaluations()
    {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations)
    {
        this.evaluations = evaluations;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getMaxEvaluationsInCollection()
    {
        int max = 0;

        if (aspirational.size() > max) max = aspirational.size();

        if (deficiency.size() > max) max = deficiency.size();

        if (progress1.size() > max) max = progress1.size();

        if (progress2.size() > max) max = progress2.size();

        if (unsupervised.size() > max) max = unsupervised.size();

        return max;

    }
}
