package org.siberian.remark.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import org.siberian.remark.client.AppConstants;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.client.utils.StringUtils;

public class LoginPanel extends Composite
{

    private static final AppConstants APP_CONSTANTS = GWT.create(AppConstants.class);

    private final TextBox loginName = new TextBox();

    private final PasswordTextBox userPassword = new PasswordTextBox();

    private Label validationLabel = new Label();

    public LoginPanel()
    {
        // Create a panel to hold all of the form widgets.
        VerticalPanel coreLoginVerticalPanel = new VerticalPanel();

        coreLoginVerticalPanel.setWidth("100%");

        coreLoginVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        coreLoginVerticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable loginTable = new FlexTable();

        Label loginInstructionsLabel = new Label("Please enter your login id and password");

        loginInstructionsLabel.setStyleName("interfaceLabel");

        loginTable.getFlexCellFormatter().setColSpan(0, 0, 2);

        loginTable.setWidget(0, 0, loginInstructionsLabel);

        Label userNameLabel = new Label(APP_CONSTANTS.getUserNameLabel());

        userNameLabel.setStyleName("loginLabel");

        // Create a TextBox, giving it a name so that it will be submitted.

        loginName.setName("userLogin");

        loginName.setTabIndex(1);

        loginName.setStyleName("lg_right_format");

        loginTable.setWidget(1, 0, userNameLabel);

        loginTable.setWidget(1, 1, loginName);

        Label userPasswordLabel = new Label(APP_CONSTANTS.getUserPasswordLabel());

        userPasswordLabel.setStyleName("loginLabel");

        userPassword.setName("userPassword");

        userPassword.setStyleName("lg_right_format");

        userPassword.setTabIndex(2);

        loginTable.setWidget(2, 0, userPasswordLabel);

        loginTable.setWidget(2, 1, userPassword);

        // Add a 'submit' button.
        Button submitButton = new Button("Submit", new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {

                if ( !loginName.getText().isEmpty() && !userPassword.getText().isEmpty() )
                {
                    RemarkService.App.getInstance().checkCredentials(loginName.getText(),
                            userPassword.getText(),
                            false,
                            new CheckCoPathPasswordAsyncCallback(
                                    userPassword,
                                    validationLabel));
                }
            }
        });

        submitButton.setStyleName("buttonFormat");

        submitButton.setTabIndex(3);

        loginTable.setWidget(3, 1, submitButton);

        validationLabel.setStyleName("validationLabel");

        userPassword.addKeyUpHandler(new KeyUpHandler()
        {

            public void onKeyUp(KeyUpEvent event)
            {

                if ( event.getNativeKeyCode() == KeyCodes.KEY_ENTER )
                {
                    if ( !loginName.getText().isEmpty() && !userPassword.getText().isEmpty() )
                    {
                        RemarkService.App.getInstance().checkCredentials(loginName.getText(),
                                userPassword.getText(),
                                false,
                                new CheckCoPathPasswordAsyncCallback(
                                        userPassword,
                                        validationLabel));
                    }
                }
            }
        });


        coreLoginVerticalPanel.add(loginTable);

        coreLoginVerticalPanel.add(new HTML("<br />"));

        coreLoginVerticalPanel.add(validationLabel);

        loginName.setFocus(true);

        initWidget(coreLoginVerticalPanel);
    }

    public TextBox getLoginName()
    {

        return loginName;
    }

    private class CheckCoPathPasswordAsyncCallback implements AsyncCallback<ServerResponse>
    {

        PasswordTextBox userPassword;

        Label validationLabel;

        public CheckCoPathPasswordAsyncCallback(PasswordTextBox userPasswordIn, Label validationLabelIn)
        {

            userPassword = userPasswordIn;

            validationLabel = validationLabelIn;
        }

        public void onFailure(Throwable caught)
        {

            userPassword.setText("");

            validationLabel.setText(APP_CONSTANTS.getFailedResponseError());
        }

        public void onSuccess(ServerResponse result)
        {

            userPassword.setText("");

            if ( !StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE) )
            {
                History.newItem(Constants.SESSION_EXPIRED_CODE);
            }
            else if ( result.getErrorMessage() == null || result.getErrorMessage().length() < 1 )
            {
                History.newItem(Constants.SUCCESSFUL_LOGIN);
            }
            else
            {
                validationLabel.setText(result.getErrorMessage());  //todo internationalize on server side
            }
        }
    }
}