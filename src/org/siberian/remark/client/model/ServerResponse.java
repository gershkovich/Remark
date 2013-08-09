package org.siberian.remark.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/8/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerResponse implements IsSerializable
{
    private String id;

    private String nextStep;

    private String errorMessage;

    private String userName;

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
}
