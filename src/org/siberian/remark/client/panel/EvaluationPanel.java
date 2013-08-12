package org.siberian.remark.client.panel;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.siberian.remark.client.AppConstants;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.Evaluation;
import org.siberian.remark.client.model.Milestone;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.client.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/9/13
 * Time: 10:50 AM
 */
public class EvaluationPanel {
    private static final AppConstants APP_CONSTANTS = GWT.create(AppConstants.class);

    private Label feedbackMessageLabel;

    private Image ajaxLoadImage;

    private UpperBandPanel upperBandPanel;

    final HorizontalPanel personInfoPanel = new HorizontalPanel();

    Label initiateWorkLbl = new Label();

    public EvaluationPanel(Label feedbackMessageLabelIn, UpperBandPanel upperBandPanelIn, Image ajaxLoadImageIn) {

        feedbackMessageLabel = feedbackMessageLabelIn;

        upperBandPanel = upperBandPanelIn;

        ajaxLoadImage = ajaxLoadImageIn;
    }

    public void buildPanel() {
        // Create a three-pane layout with splitters.
        SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(15);


        initiateWorkLbl.setText("To start select a person to evaluate on the left panel below. Use search dialog to look them up.");

        personInfoPanel.add(initiateWorkLbl);


        personInfoPanel.setHeight("100px");

        personInfoPanel.setStyleName("personInfoPanel");

        // personInfoPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        personInfoPanel.setCellVerticalAlignment(initiateWorkLbl, HasVerticalAlignment.ALIGN_BOTTOM);

        splitLayoutPanel.addNorth(personInfoPanel, 100);

        splitLayoutPanel.setWidgetMinSize(personInfoPanel, 99);


        VerticalPanel verticalPanel = new VerticalPanel();

        splitLayoutPanel.addWest(verticalPanel, 250);


        VerticalPanel detailVerticalPanel = new VerticalPanel();

        Widget flexGrid = createMilestoneTable();


        splitLayoutPanel.add(detailVerticalPanel);


        detailVerticalPanel.add(flexGrid);


        TextBox searchBox = new TextBox();

        searchBox.setWidth("200");

        searchBox.setStyleName("search_box");


        HorizontalPanel searchPanel = new HorizontalPanel();

        searchPanel.add(searchBox);

        Image searchImage = new Image("images/search_icon.jpg");

        searchImage.setPixelSize(35, 35);

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


                    initiateWorkLbl.setText(selected);

                    final ToggleButton selectEvaluationTrack = new ToggleButton("Evaluation Tracks", "Evaluation Tracks?");


                    selectEvaluationTrack.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            if (selectEvaluationTrack.isDown()) {
//                    Window.alert("I have been toggled down");

                                selectEvaluationTrack.removeStyleName("personInfoButton_up");
                                selectEvaluationTrack.addStyleName("personInfoButton_down");

                                //load evaluation tracks from the server
                                RemarkService.App.getInstance().getEvaluationTracks(initiateWorkLbl.getText(), new EvaluationTrackAsyncCallback());


                            } else {
                                //   Window.alert("I have been toggled up");
                                selectEvaluationTrack.removeStyleName("personInfoButton_down");
                                selectEvaluationTrack.addStyleName("personInfoButton_up");
                            }
                        }
                    });

                    selectEvaluationTrack.setStyleName("personInfoButton");

                    personInfoPanel.add(selectEvaluationTrack);

                    //  selectEvaluationTrack.setStyleName("personInfoButton");

                    personInfoPanel.setCellVerticalAlignment(selectEvaluationTrack, HasVerticalAlignment.ALIGN_BOTTOM);


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

    /**
     * Add a row to the flex table.
     */
    private void addRow(FlexTable flexTable) {
        int numRows = flexTable.getRowCount();
        flexTable.setWidget(numRows, 0, new Image("images/blank.png"));
        flexTable.setWidget(numRows, 1, new Image("images/blank.png"));
        flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
    }

    /**
     * Remove a row from the flex table.
     */
    private void removeRow(FlexTable flexTable) {
        int numRows = flexTable.getRowCount();
        if (numRows > 1) {
            flexTable.removeRow(numRows - 1);
            flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
        }
    }


    public Cell createClickableCell() {

        ClickableTextCell cell = new ClickableTextCell() {
            @Override
            public void render(Context context, String value, SafeHtmlBuilder sb) {
                if (value != null) {
                    sb.appendEscaped(value);
                }
            }

            @Override
            public void onBrowserEvent(Context context, Element parent, String value,
                                       NativeEvent event, ValueUpdater<String> valueUpdater) {
                super.onBrowserEvent(context, parent, value, event, valueUpdater);
                if ("click".equals(event.getType())) {
                    onEnterKeyDown(context, parent, value, event, valueUpdater);

                }
            }

            @Override
            protected void onEnterKeyDown(Context context, Element parent, String value,
                                          NativeEvent event, ValueUpdater<String> valueUpdater) {
                if (valueUpdater != null) {
                    valueUpdater.update(value);
                }
            }
        };

        return cell;
    }


    private ArrayList<Milestone> getMilestones(int direction) {
        //create milestones for test
        ArrayList<Milestone> milestones = new ArrayList<Milestone>();


        Milestone milestone = new Milestone();

        milestone.setName("Information Gathering");

        createEvaluation(milestone, "Critical Deficiencies", "Does not collect accurate historical data");
        createEvaluation(milestone, "Critical Deficiencies", "Does not use physical exam to confirm history");
        createEvaluation(milestone, "Critical Deficiencies", "Relies exclusively on documentation of others to generate own database or differential diagnosis");
        createEvaluation(milestone, "Critical Deficiencies", "Fails to recognize patient’s central clinical problems");
        createEvaluation(milestone, "Critical Deficiencies", "Fails to recognize potentially life threatening problems");


        milestones.add(milestone);

        //-------------------

        milestone = new Milestone();

        milestone.setName("Information Gathering");

        createEvaluation(milestone, "Some Progress", "Inconsistently able to acquire accurate historical information in an organized fashion");
        createEvaluation(milestone, "Some Progress", "Does not perform an appropriately thorough physical exam or misses key physical exam findings");
        createEvaluation(milestone, "Some Progress", "Does not seek or is overly reliant on secondary data");
        createEvaluation(milestone, "Some Progress", "Inconsistently recognizes patients’ central clinical problem or develops limited differential diagnoses");

        milestones.add(milestone);

        return milestones;


    }

    private void createEvaluation(Milestone milestone, String category, String textOfEvaluation) {

        Evaluation evaluation = new Evaluation();

        evaluation.setText(textOfEvaluation);

        evaluation.setCategory(category);

        milestone.getEvaluations().add(evaluation);
    }

    public Widget createMilestoneTable() {
        // Create a Flex Table
        final FlexTable flexTable = new FlexTable();
        FlexTable.FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
        flexTable.addStyleName("cw-FlexTable");
        flexTable.setWidth("32em");
        flexTable.setCellSpacing(0);
        flexTable.setCellPadding(3);


        ArrayList<Milestone> milestones = getMilestones(1);


        int maxRows = 0;

        for (Milestone milestone : milestones) {
            if (milestone.getEvaluations().size() > maxRows) {
                maxRows = milestone.getEvaluations().size();
            }
        }


        int column = 0;


        for (Milestone milestone : milestones) {
            int row = 0;

            int totalRows = milestone.getEvaluations().size();

            for (Evaluation evaluation : milestone.getEvaluations()) {
                cellFormatter.setHorizontalAlignment(
                        row, column, HasHorizontalAlignment.ALIGN_LEFT);

                final ToggleButton toggleButton = new ToggleButton(evaluation.getText());

                toggleButton.setHeight("100%");

                toggleButton.setWidth("100%");

                toggleButton.addStyleName("milestone_button_up");
                toggleButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        if (toggleButton.isDown()) {
//                    Window.alert("I have been toggled down");

                            toggleButton.removeStyleName("milestone_button_up");
                            toggleButton.addStyleName("milestone_button_down");


                        } else {
                            //   Window.alert("I have been toggled up");
                            toggleButton.removeStyleName("milestone_button_down");
                            toggleButton.addStyleName("milestone_button_up");
                        }
                    }
                });

                flexTable.setWidget(row++, column, toggleButton);

                if (row == totalRows) {
                    //add rowspan to max
                    int span = maxRows - totalRows;

                    if (span > 0) {
                        cellFormatter.setRowSpan(row - 1,
                                column,
                                span + 1);
                    }

                }

            }

            column++;

        }

        return flexTable;
    }


    private class EvaluationTrackAsyncCallback implements AsyncCallback<ServerResponse> {
        public void onFailure(Throwable caught) {
            //headerPanel.successfulLogin( "", centerPanel, 900 );

            History.newItem(Constants.LOGIN);
        }

        public void onSuccess(ServerResponse result) {
            if (!StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE)) {
                History.newItem(Constants.SESSION_EXPIRED_CODE);
            } else if (result.getErrorMessage() == null || result.getErrorMessage().length() < 1) {


                //load evaluation list

            } else {
                History.newItem(Constants.LOGIN);
            }
        }
    }
}
