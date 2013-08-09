package org.siberian.remark.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.ServerResponse;

public class RemarkServiceImpl extends RemoteServiceServlet implements RemarkService
{

    public ServerResponse getPeople(String commentText)
    {

        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setId("Bla bla ");

        return serverResponse;
    }

    public ServerResponse checkForSessionTimeout()
    {

        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String logoutUser()
    {

        return "logout";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServerResponse checkCredentials(String loginNameText, String userPasswordText, boolean flag)
    {
        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServerResponse successfulLogin()
    {

        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }
}