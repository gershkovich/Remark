package org.siberian.remark.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.panel.LoginPanel;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/8/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RemarkServiceAsync
{
    void getPeople(String commentText, AsyncCallback<ServerResponse> async);


    void checkForSessionTimeout( AsyncCallback<ServerResponse> async);

    void logoutUser(AsyncCallback<String> async);

    void checkCredentials(String loginNameText, String userPasswordText, boolean flag,
                          AsyncCallback<ServerResponse> async);

    void successfulLogin(AsyncCallback<ServerResponse> async);
}
