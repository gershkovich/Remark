package org.siberian.remark.client.panel;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.siberian.remark.client.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/9/13
 * Time: 10:50 AM
 */
public class EvaluationPanel
{
    private static final AppConstants APP_CONSTANTS = GWT.create(AppConstants.class);

    private Label feedbackMessageLabel;

    private Image ajaxLoadImage;

    private UpperBandPanel upperBandPanel;

    public EvaluationPanel(Label feedbackMessageLabelIn, UpperBandPanel upperBandPanelIn, Image ajaxLoadImageIn)
    {

        feedbackMessageLabel = feedbackMessageLabelIn;

        upperBandPanel = upperBandPanelIn;

        ajaxLoadImage = ajaxLoadImageIn;
    }

    public void buildPanel()
    {
       // Create a three-pane layout with splitters.
                SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(15);

                splitLayoutPanel.addNorth(upperBandPanel, 80);

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
}
