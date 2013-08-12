package org.siberian.remark.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.siberian.remark.client.model.ServerResponse;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/8/13
 * Time: 11:33 AM
 *
 *
 */
@RemoteServiceRelativePath( "RemarkService" )
public interface RemarkService extends RemoteService
{

    ServerResponse getPeople(String commentText);

    ServerResponse checkForSessionTimeout();

    String logoutUser();

    ServerResponse checkCredentials(String loginNameText, String userPasswordText, boolean flag);

    ServerResponse successfulLogin();

    ServerResponse getEvaluationTracks(String text);

    /**
     * Utility/Convenience class.
     * Use RemarkService.App.getInstance() to access static instance of RemarkServiceAsync
     */
    public static class App
    {

        private static final RemarkServiceAsync ourInstance = ( RemarkServiceAsync ) GWT.create(RemarkService.class);

        public static RemarkServiceAsync getInstance()
        {

            return ourInstance;
        }
    }
}
