package org.siberian.remark.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.panel.LoginPanel;
import org.siberian.remark.client.panel.UpperBandPanel;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.client.utils.StringUtils;

public class Remark implements EntryPoint  , ValueChangeHandler<String>
{
    private static final AppConstants CONSTANTS = GWT.create(AppConstants.class);

    private final RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();

    private UpperBandPanel upperBandPanel = new UpperBandPanel();

    private VerticalPanel centerPanel = new VerticalPanel();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        centerPanel.setSize( "100%", "100%" );

        centerPanel.add(upperBandPanel);

        centerPanel.setStyleName("core_center_panel");

        centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        rootLayoutPanel.add( centerPanel );

        Window.enableScrolling( false );

        History.addValueChangeHandler(this);

        History.fireCurrentHistoryState();

    }

    public void onValueChange(ValueChangeEvent<String> event)
    {
        String historyToken = History.getToken();

        if ( historyToken.isEmpty() )
        {
            historyToken = "login";
        }

        if ( historyToken.equalsIgnoreCase( Constants.LOGIN ) )
        {
            RootLayoutPanel rp = RootLayoutPanel.get();

            rp.clear();

            rootLayoutPanel.add( centerPanel );

            if (upperBandPanel == null)
            {
                upperBandPanel = new UpperBandPanel();
            }

            upperBandPanel.startup();

            centerPanel.clear();

            centerPanel.setSize( "100%", "100%" );

            centerPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_TOP );

            centerPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

            centerPanel.add(upperBandPanel);

            final HorizontalPanel insideCenterPanel = new HorizontalPanel();

         //   insideCenterPanel.setWidth( Window.getClientWidth() - 150 + "px" );

            Window.addResizeHandler( new ResizeHandler()
            {
                public void onResize( ResizeEvent event )
                {
                  //  insideCenterPanel.setWidth( Window.getClientWidth() - 150 + "px" );
                }
            } );

            insideCenterPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_TOP );

            insideCenterPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

            final LoginPanel loginPanel = new LoginPanel();

            insideCenterPanel.add( loginPanel );

            centerPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_TOP );

            centerPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

            centerPanel.add( insideCenterPanel );

            Scheduler.get().scheduleDeferred( new Scheduler.ScheduledCommand()
            {
                public void execute()
                {
                    loginPanel.getLoginName().setFocus( true );
                }
            } );
        }
        else if ( historyToken.equalsIgnoreCase( Constants.SESSION_EXPIRED_CODE ) )
        {
            upperBandPanel.terminateTimers = true;

            RemarkService.App.getInstance().logoutUser( new LogoutUserAsyncCallback() );

        }
        else if ( historyToken.equalsIgnoreCase( Constants.SUCCESSFUL_LOGIN ) )
        {//
            centerPanel.clear();

            if (upperBandPanel == null)
            {
                upperBandPanel = new UpperBandPanel();
            }

            centerPanel.add(upperBandPanel);

            centerPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_TOP );

            centerPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

            centerPanel.setWidth( "100%" );

            RemarkService.App.getInstance().successfulLogin( new SuccessfulLoginAsyncCallback() );
        }

    }

    private class SuccessfulLoginAsyncCallback implements AsyncCallback<ServerResponse>
    {
        public void onFailure( Throwable caught )
        {
            //headerPanel.successfulLogin( "", centerPanel, 900 );

            History.newItem( Constants.LOGIN );
        }

        public void onSuccess( ServerResponse result )
        {
            if ( !StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase( Constants.SESSION_EXPIRED_CODE ) )
            {
                History.newItem( Constants.SESSION_EXPIRED_CODE );
            }
            else if ( result.getErrorMessage() == null || result.getErrorMessage().length() < 1 )
            {

                    if ( StringUtils.isEmpty( result.getUserName() ) )
                    {
                        History.newItem( Constants.LOGIN );
                    }
                    else
                    {
                        upperBandPanel.successfulLogin( result.getUserName(),
                                centerPanel);
                    }

            }
            else
            {
                History.newItem( Constants.LOGIN );
            }
        }
    }


    private class LogoutUserAsyncCallback implements AsyncCallback<String>
    {
        public void onFailure( Throwable caught )
        {   rootLayoutPanel.clear();
            History.newItem( Constants.LOGIN );
        }

        public void onSuccess( String result )
        {
            rootLayoutPanel.clear();
            History.newItem( Constants.LOGIN );
        }
    }



}
