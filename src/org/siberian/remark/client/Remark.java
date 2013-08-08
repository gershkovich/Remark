package org.siberian.remark.client;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.siberian.remark.client.model.ServerResponse;

import java.util.ArrayList;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Remark implements EntryPoint {
    private static final AppConstants CONSTANTS = GWT.create(AppConstants.class);

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final RemarkServiceAsync remarkServiceAsync = GWT
            .create(RemarkService.class);
    private TextBox nameField;
    private Button btnButton;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        // Create a three-pane layout with splitters.
        SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(15);

        splitLayoutPanel.addNorth(new HTML("list"), 50);
        VerticalPanel verticalPanel = new VerticalPanel();
        splitLayoutPanel.addWest(verticalPanel, 250);

        splitLayoutPanel.add(new HTML("details"));



        TextBox searchBox = new TextBox();

        searchBox.setWidth("200");

        searchBox.setStyleName("search_box");
        
        
        HorizontalPanel searchPanel = new HorizontalPanel();
        
        searchPanel.add(searchBox);

        Image searchImage = new Image("images/search_icon.jpg") ;

        searchImage.setPixelSize(35,35);

        searchPanel.add(searchImage);





        verticalPanel.setStylePrimaryName("navigation_panel");

        verticalPanel.add(searchPanel);

        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        ArrayList<String> people = new ArrayList<String>();

        people.add("Hammond");

        people.add("Rebeca");

        people.add("Lui the 16th");

        TextCell textCell = new TextCell();

        // Create a CellList that uses the cell.
        CellList<String> cellList = new CellList<String>(textCell);

        cellList.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        // Add a selection model to handle user selection.
        final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
        cellList.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                String selected = selectionModel.getSelectedObject();
                if (selected != null) {


                    Window.alert("You selected: " + selected);
                }
            }
        });


        cellList.setStyleName("cell_list");

        // Set the total row count. This isn't strictly necessary, but it affects
        // paging calculations, so its good habit to keep the row count up to date.
        cellList.setRowCount(people.size(), true);

        // Push the data into the widget.
        cellList.setRowData(0, people);

        verticalPanel.add(cellList);


        textCell.handlesSelection();



        // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
        // resize events on the window to ensure that its children are informed of
        // possible size changes.

        StyleInjector.inject(".gwt-SplitLayoutPanel .gwt-SplitLayoutPanel-HDragger "
                + "{ background: darkseagreen;  }");

        StyleInjector.inject(".gwt-SplitLayoutPanel .gwt-SplitLayoutPanel-VDragger "
                + "{ width: 100%;  background: darkseagreen; }");



        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(splitLayoutPanel);
    }
    public String getNameFieldText() {
        return nameField.getText();
    }
    public void setNameFieldText(String text) {
        nameField.setText(text);
    }

    public Button getBtnButton() {
        return btnButton;
    }
}
