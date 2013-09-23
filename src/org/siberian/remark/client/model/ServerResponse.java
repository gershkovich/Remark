package org.siberian.remark.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/8/13
 * Time: 11:38 AM
 */
public class ServerResponse implements IsSerializable
{
    private String id;

    private String nextStep;

    private String errorMessage;

    private String userName;

    private String evaluationPath;

    private ArrayList<Learner> learners = new ArrayList<Learner>();

    private ArrayList<EvaluationAxis> evaluationAxises = new ArrayList<EvaluationAxis>();

    private EvaluationAxis evaluationAxis = null;

    public String getId()
    {

        return id;
    }

    public void setId(String id)
    {

        this.id = id;
    }

    public String getNextStep()
    {

        return nextStep;
    }

    public void setNextStep(String nextStep)
    {

        this.nextStep = nextStep;
    }

    public String getErrorMessage()
    {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {

        this.errorMessage = errorMessage;
    }

    public String getUserName()
    {

        return userName;
    }

    public void setUserName(String userName)
    {

        this.userName = userName;
    }

    public String getEvaluationPath() {
        return evaluationPath;
    }

    public void setEvaluationPath(String evaluationPath) {
        this.evaluationPath = evaluationPath;
    }

    public ArrayList<Learner> getLearners()
    {
        return learners;
    }

    public ArrayList<EvaluationAxis> getEvaluationAxises()
    {
        return evaluationAxises;
    }


    public EvaluationAxis getEvaluationAxis()
    {
        return evaluationAxis;
    }

    public void setEvaluationAxis(EvaluationAxis evaluationAxis)
    {
        this.evaluationAxis = evaluationAxis;
    }
}
