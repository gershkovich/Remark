package org.siberian.remark.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import org.siberian.remark.client.AppConstants;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.client.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/9/13
 * Time: 11:26 AM
 */
public class UpperBandPanel extends Composite
{

    private static final AppConstants APP_CONSTANTS = GWT.create(AppConstants.class);

    private VerticalPanel coreUpperBandPanel = new VerticalPanel();

    private Label feedbackMessageLabel = new Label();

    final HorizontalPanel upperBand = new HorizontalPanel();

   // final HorizontalPanel lowerBand = new HorizontalPanel();

   // final HorizontalPanel lowerLogoBand = new HorizontalPanel();

    private Image ajaxLoadImage = new Image("images/blank.png");

    private VerticalPanel centerPanel;

    private Image logoAppInUse = new Image("images/appTitle.png");

    public Timer sessionTimeoutTimer = new Timer()
    {

        public void run()
        {

            if ( terminateTimers )
            {
                this.cancel();

                return;
            }

            RemarkService.App.getInstance().checkForSessionTimeout(new SessionTimeoutAsyncCallback());
        }
    };

    public boolean terminateTimers = false;

    protected void onUnload()
    {

        terminateTimers = true;

        if ( sessionTimeoutTimer != null )
        {
            sessionTimeoutTimer.cancel();
        }
    }

    public UpperBandPanel()
    {

        startup();

        feedbackMessageLabel.setWordWrap(true);

        feedbackMessageLabel.setStyleName("errorMessage");

        coreUpperBandPanel.setStyleName("headerPanelBackground");

        initWidget(coreUpperBandPanel);
    }

    public void startup()
    {

        coreUpperBandPanel.clear();

        upperBand.clear();

        //lowerBand.clear();

        Image logoForLogin = new Image("images/appTitle.png");

        logoForLogin.setStyleName("logoForLogin");

        upperBand.setStyleName("upperBand");

        upperBand.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        upperBand.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        upperBand.add(logoForLogin);

        //		lowerBand.setHeight( "100%" );

       // lowerBand.setStyleName("lowerBand_init");

//        logoAppInUseLowBand.setStyleName("logoLowerBand");
//
//        logoAppInUseLowBand.setHeight("20px");

      //  lowerBand.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

     //   lowerBand.add(logoAppInUseLowBand);

        coreUpperBandPanel.setWidth("100%");

        coreUpperBandPanel.setHeight("65px");

        coreUpperBandPanel.setStyleName("headerPanelBackground");

        //		coreUpperBandPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_BOTTOM );

        coreUpperBandPanel.add(upperBand);

     //   coreUpperBandPanel.add(lowerBand);
    }


    public native void reloadApp() /*-{
        $wnd.location.reload();
    }-*/;

    private class LogoutUserAsyncCallback implements AsyncCallback<String>
    {

        public void onFailure(Throwable caught)
        {
            History.newItem(Constants.LOGIN);
           RootLayoutPanel.get().clear();
            reloadApp();

        }

        public void onSuccess(String result)
        {
            History.newItem(Constants.LOGIN);
            RootLayoutPanel.get().clear();
            reloadApp();

        }
    }

    private class SessionTimeoutAsyncCallback implements AsyncCallback<ServerResponse>
    {

        public void onFailure(Throwable caught)
        {


        }

        public void onSuccess(ServerResponse result)
        {

            if ( !StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE) )
            {
                History.newItem(Constants.SESSION_EXPIRED_CODE);
            }
        }
    }

    public void successfulLogin(String prettyPrintNameIn,
                                VerticalPanel centerPanelIn)
    {

        terminateTimers = false;

        sessionTimeoutTimer.scheduleRepeating(60000);

        centerPanel = centerPanelIn;

        upperBand.clear();

      //  lowerBand.clear();

        //lowerLogoBand.clear();

        coreUpperBandPanel.clear();

     //   lowerBand.setStyleName("lowerBand");

        //lowerLogoBand.setStyleName("lowerLogoBand");

        upperBand.setStyleName("upperBand");

        coreUpperBandPanel.setWidth("100%");

        coreUpperBandPanel.setHeight("65px");

        coreUpperBandPanel.setStyleName("headerPanelBackground");

        upperBand.setWidth("100%");

     //   lowerBand.setWidth("100%");

        //		lowerBand.setHeight( "100%" );

        logoAppInUse.setStyleName("logoAppInUse");

        //		logoAppInUse.setHeight( "45px" );

//        logoAppInUseLowBand.setStyleName("logoLowerBand");
//
//        logoAppInUseLowBand.setHeight("20px");

        final MenuBar mainMenuBar = new MenuBar();

        MenuItem helpMenuItem = new MenuItem("Help", true, new Command()
        {

            public void execute()
            {

                Window.open("IonTorrent_UserGuide.pdf",
                        "Ion Torrent User Guide",
                        "menubar=no,location=yes,resizable=yes,scrollbars=yes,status=yes");
            }
        });

        MenuItem logoutMenuItem = new MenuItem(APP_CONSTANTS.getSignOut(), true, new Command()
        {

            public void execute()
            {

                RemarkService.App.getInstance().logoutUser(new LogoutUserAsyncCallback());
            }
        });

        final MenuBar userNameMenuBar = new MenuBar(true);

        final MenuItem userNameMenuItem = new MenuItem(prettyPrintNameIn, userNameMenuBar);


        		userNameMenuBar.setWidth( "20%" );

        userNameMenuBar.setStyleName("customMenuItem");


        //		if ( securityMap.containsKey( Constants.SYSTEMS_MANAGER ) &&
        //			 securityMap.get( Constants.SYSTEMS_MANAGER ) )
        //		{
        //			userNameMenuBar.addItem( switchUserMenuItem );
        //		}

        helpMenuItem.setWidth("20%");

        logoutMenuItem.setWidth("20%");

        final MenuBar menuBar = new MenuBar( true );

        mainMenuBar.setStyleName("customMenuItem");

        mainMenuBar.addItem(userNameMenuItem);

        MenuItemSeparator separator3 = new MenuItemSeparator();

        mainMenuBar.addItem(helpMenuItem);

        mainMenuBar.addItem(logoutMenuItem);

        mainMenuBar.setAutoOpen(true);

        mainMenuBar.setAnimationEnabled(false);

        mainMenuBar.setFocusOnHoverEnabled(true);

        mainMenuBar.setAutoOpen(true);

        HorizontalPanel menuBarPanel = new HorizontalPanel();

        menuBarPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        menuBarPanel.add(mainMenuBar);

        //Image triangle = new Image( "/images/triangle4.gif" );

        //		triangle.setHeight( "30%" );
        //
        //		triangle.addStyleName( "triangle" );
        //
        //		menuBarPanel.add( triangle );

        //		menuBar.addCloseHandler( new CloseHandler<PopupPanel>()
        //		{
        //			public void onClose( CloseEvent<PopupPanel> event )
        //			{
        ////				specimenTextBox.selectAll();
        //
        ////				specimenTextBox.setFocus( true );
        //			}
        //		} );

        mainMenuBar.addCloseHandler(new CloseHandler<PopupPanel>()
        {

            public void onClose(CloseEvent<PopupPanel> event)
            {
                //				specimenTextBox.selectAll();

                //				specimenTextBox.setFocus( true );
            }
        });

        //		mainMenuBar.addStyleName( "headerRow" );


        coreUpperBandPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        coreUpperBandPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        upperBand.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        upperBand.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        upperBand.add(logoAppInUse);

        //lowerLogoBand.add(logoAppInUseLowBand);

       // lowerBand.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

//        lowerBand.add(lowerLogoBand);
//
//        lowerBand.setCellWidth(lowerLogoBand, "320");


        coreUpperBandPanel.add(upperBand);

        coreUpperBandPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

       // coreUpperBandPanel.add(lowerBand);

        upperBand.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        upperBand.add(new HTML("&nbsp;"));

        upperBand.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        upperBand.add(menuBarPanel);

        upperBand.add(new HTML("&nbsp;&nbsp;&nbsp;"));


     //   centerPanel.add(coreUpperBandPanel);

        EvaluationPanel evaluationPanel = new EvaluationPanel(feedbackMessageLabel,
                this,
                ajaxLoadImage);


        evaluationPanel.buildPanel();
    }

}
