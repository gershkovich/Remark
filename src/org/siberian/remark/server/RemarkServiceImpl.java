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
}