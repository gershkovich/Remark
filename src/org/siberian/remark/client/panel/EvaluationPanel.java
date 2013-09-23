package org.siberian.remark.client.panel;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.siberian.remark.client.AppConstants;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.Evaluation;
import org.siberian.remark.client.model.EvaluationAxis;
import org.siberian.remark.client.model.Learner;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.client.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pg86
 * Date: 8/9/13
 * Time: 10:50 AM
 */
public class EvaluationPanel extends Composite
{

    private static final AppConstants APP_CONSTANTS = GWT.create(AppConstants.class);

    final SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();

    final VerticalPanel northPanel = new VerticalPanel();

    final HorizontalPanel learnerInfoPanel = new HorizontalPanel();

    final VerticalPanel learnerListPanel = new VerticalPanel();

    VerticalPanel detailVerticalPanel = new VerticalPanel();

    private Learner selectedLearner = null;


    /**
     * A custom {@link Cell} used to render a {@link Learner}.
     */
    private static class LearnerCell extends AbstractCell<Learner>
    {
        @Override
        public void render(Context context, Learner value, SafeHtmlBuilder sb)
        {
            if (value != null)
            {
                sb.appendEscaped(value.getFullName());
            }
        }
    }

    public EvaluationPanel()
    {
        initWidget(splitLayoutPanel);

    }

    public Widget buildPanel(UpperBandPanel upperBandPanel)
    {
        northPanel.setWidth("100%");

        northPanel.add(upperBandPanel);

        northPanel.add(learnerInfoPanel);

        northPanel.setCellVerticalAlignment(learnerInfoPanel, HasVerticalAlignment.ALIGN_BOTTOM);

        Label initiateWorkLbl = new Label();

        initiateWorkLbl.setText("To start select a person to evaluate on the left panel below. Use search dialog to look them up.");

        learnerInfoPanel.add(initiateWorkLbl);

        // learnerInfoPanel.setHeight("100px");

        learnerInfoPanel.setStyleName("learnerInfoPanel");

        // learnerInfoPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        learnerInfoPanel.setCellVerticalAlignment(initiateWorkLbl, HasVerticalAlignment.ALIGN_BOTTOM);

        splitLayoutPanel.addNorth(northPanel, 100);

        splitLayoutPanel.setWidgetMinSize(northPanel, 99);

        splitLayoutPanel.addWest(learnerListPanel, 250);

        splitLayoutPanel.add(detailVerticalPanel);

        final TextBox searchBox = new TextBox();

        searchBox.setWidth("200");

        searchBox.setStyleName("search_box");

        HorizontalPanel searchPanel = new HorizontalPanel();

        searchPanel.add(searchBox);

        searchBox.addKeyUpHandler(new KeyUpHandler()
        {
            @Override
            public void onKeyUp(KeyUpEvent event)
            {
                if (event.getNativeKeyCode() == 13 || event.getNativeKeyCode() == 32)
                {

                    if (StringUtils.isNotEmpty(searchBox.getText()))
                    {
                        RemarkService.App.getInstance().getPeople(searchBox.getText(), new LearnerListAsyncCallback());
                    }
                }
            }
        });

        Image searchImage = new Image("images/search_icon.jpg");

        searchImage.setPixelSize(35, 35);

        searchPanel.add(searchImage);

        learnerListPanel.setStylePrimaryName("navigation_panel");

        learnerListPanel.add(searchPanel);

        learnerListPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        //TODO get a list of people who are related to the current user


        // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
        // resize events on the window to ensure that its children are informed of
        // possible size changes.

        StyleInjector.inject(".gwt-SplitLayoutPanel .gwt-SplitLayoutPanel-HDragger "
                + "{ background: darkseagreen;  }");

        StyleInjector.inject(".gwt-SplitLayoutPanel .gwt-SplitLayoutPanel-VDragger "
                + "{ width: 100%;  background: darkseagreen; }");

        return this;
    }

    private void manageLearnerSelection(final SingleSelectionModel<Learner> selectionModel)
    {
        //sell with the name of evaluated person is selected
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler()
        {

            public void onSelectionChange(SelectionChangeEvent event)
            {

                Learner learner = selectionModel.getSelectedObject();

                if (learner != null)
                {
                    selectedLearner = learner;

                    final Label initiateWorkLbl = new Label();

                    initiateWorkLbl.setText(learner.getFullName());

                    final ToggleButton selectEvaluationTrack = new ToggleButton("Evaluation Tracks", "Evaluation Tracks");


                    selectEvaluationTrack.addClickHandler(new ClickHandler()
                    {
                        public void onClick(ClickEvent event)
                        {
                            if (selectEvaluationTrack.isDown() && selectedLearner.getId() != null)
                            {
//                    Window.alert("I have been toggled down");

                                selectEvaluationTrack.removeStyleName("personInfoButton_up");
                                selectEvaluationTrack.addStyleName("personInfoButton_down");

                                //load evaluation tracks from the server
                                RemarkService.App.getInstance().getEvaluationTracks(selectedLearner.getId(), new EvaluationTracksAsyncCallback());


                            } else
                            {
                                //   Window.alert("I have been toggled up");
                                selectEvaluationTrack.removeStyleName("personInfoButton_down");
                                selectEvaluationTrack.addStyleName("personInfoButton_up");
                            }
                        }
                    });

                    selectEvaluationTrack.setStyleName("personInfoButton");

                    learnerInfoPanel.clear();

                    learnerInfoPanel.add(initiateWorkLbl);

                    learnerInfoPanel.add(selectEvaluationTrack);

                    //  selectEvaluationTrack.setStyleName("personInfoButton");

                    learnerInfoPanel.setCellVerticalAlignment(initiateWorkLbl, HasVerticalAlignment.ALIGN_BOTTOM);

                    learnerInfoPanel.setCellVerticalAlignment(selectEvaluationTrack, HasVerticalAlignment.ALIGN_BOTTOM);


                }
            }
        });
    }


    /**
     * Add a row to the flex table.
     */
    private void addRow(FlexTable flexTable)
    {
        int numRows = flexTable.getRowCount();
        flexTable.setWidget(numRows, 0, new Image("images/blank.png"));
        flexTable.setWidget(numRows, 1, new Image("images/blank.png"));
        flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
    }

    /**
     * Remove a row from the flex table.
     */
    private void removeRow(FlexTable flexTable)
    {
        int numRows = flexTable.getRowCount();
        if (numRows > 1)
        {
            flexTable.removeRow(numRows - 1);
            flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
        }
    }


    public Cell createClickableCell()
    {

        ClickableTextCell cell = new ClickableTextCell()
        {
            @Override
            public void render(Context context, String value, SafeHtmlBuilder sb)
            {
                if (value != null)
                {
                    sb.appendEscaped(value);
                }
            }

            @Override
            public void onBrowserEvent(Context context, Element parent, String value,
                                       NativeEvent event, ValueUpdater<String> valueUpdater)
            {
                super.onBrowserEvent(context, parent, value, event, valueUpdater);
                if ("click".equals(event.getType()))
                {
                    onEnterKeyDown(context, parent, value, event, valueUpdater);

                }
            }

            @Override
            protected void onEnterKeyDown(Context context, Element parent, String value,
                                          NativeEvent event, ValueUpdater<String> valueUpdater)
            {
                if (valueUpdater != null)
                {
                    valueUpdater.update(value);
                }
            }
        };

        return cell;
    }


    public Widget createMilestoneTable(EvaluationAxis evaluationAxis)
    {
        // Create a Flex Table
        final FlexTable flexTable = new FlexTable();
        FlexTable.FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
        flexTable.setStyleName("cw-FlexTable");
        flexTable.setCellSpacing(0);
        flexTable.setCellPadding(3);


        int maxRows = evaluationAxis.getMaxEvaluationsInCollection();

        int column = 0;

        createMilestoneColumn("Deficiency", evaluationAxis.getDeficiency(), flexTable, cellFormatter, maxRows, column++);
        createMilestoneColumn("Progress I", evaluationAxis.getProgress1(), flexTable, cellFormatter, maxRows, column++);
        createMilestoneColumn("Progress II", evaluationAxis.getProgress2(), flexTable, cellFormatter, maxRows, column++);
        createMilestoneColumn("Unsupervised", evaluationAxis.getUnsupervised(), flexTable, cellFormatter, maxRows, column++);
        createMilestoneColumn("Aspirational", evaluationAxis.getAspirational(), flexTable, cellFormatter, maxRows, column);

        return flexTable;
    }

    private void createMilestoneColumn(String header, List<Evaluation> evaluations,
                                       FlexTable flexTable,
                                       FlexTable.FlexCellFormatter cellFormatter,
                                       int maxRows, int column)
    {

        cellFormatter.setStyleName(0, column, "flexTableHeader");
        flexTable.setText(0, column, header);

        int row = 1;

        int totalRows = evaluations.size();

        for (int i = 0; i < maxRows; i++)
        {
            cellFormatter.setStyleName(row, column, "flexTableCell");

            if (i < totalRows)
            {

                Evaluation evaluation = evaluations.get(i);

                final ToggleButton toggleButton = new ToggleButton(evaluation.getText());

                toggleButton.setHeight("100%");

                toggleButton.setWidth("100%");

                toggleButton.addStyleName("milestone_button_up");
                toggleButton.addClickHandler(new ClickHandler()
                {
                    public void onClick(ClickEvent event)
                    {
                        if (toggleButton.isDown())
                        {
//                    Window.alert("I have been toggled down");

                            toggleButton.removeStyleName("milestone_button_up");
                            toggleButton.addStyleName("milestone_button_down");


                        } else
                        {
                            //   Window.alert("I have been toggled up");
                            toggleButton.removeStyleName("milestone_button_down");
                            toggleButton.addStyleName("milestone_button_up");
                        }
                    }
                });

                flexTable.setWidget(row++, column, toggleButton);

            } else
            {
                flexTable.setText(row++, column, "");
            }


        }

//        for (Evaluation evaluation : evaluations)
//        {
//
//            cellFormatter.setStyleName(row, column, "flexTableCell");
//
//
//            if (row == totalRows)
//            {
//                //add rowspan to max
//                int span = maxRows - totalRows;
//
//                if (span > 0)
//                {
//                    cellFormatter.setRowSpan(row - 1,
//                            column,
//                            span + 1);
//                }
//
//            }
//
//        }
    }


    private class EvaluationTracksAsyncCallback implements AsyncCallback<ServerResponse>
    {
        public void onFailure(Throwable caught)
        {

            History.newItem(Constants.LOGIN);
        }

        public void onSuccess(ServerResponse result)
        {
            if (!StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE))
            {
                History.newItem(Constants.SESSION_EXPIRED_CODE);

            } else
            {
                if (result.getErrorMessage() == null || result.getErrorMessage().length() < 1)
                {
                    //load evaluation list

                    detailVerticalPanel.clear();

                    //create a  table
                    final FlexTable axisFlexTable = new FlexTable();

                    axisFlexTable.setStyleName("axis_table");

                    axisFlexTable.setCellSpacing(1);

                    HTMLTable.CellFormatter formatter = axisFlexTable.getFlexCellFormatter();

                    int currentRow = 0;
                    int currentColumn = 0;
                    int position = 0;

                    for (EvaluationAxis evaluationAxis : result.getEvaluationAxises())
                    {


                        if (position > 0 && position % 4 == 0)
                        {
                            currentRow++;
                            currentColumn = 0;
                        }


                        HTML axisLabel = new HTML(SafeHtmlUtils.fromTrustedString(evaluationAxis.getName() + "<sup>[" + evaluationAxis.getLearningCategory() + "]</sup>"));


                        axisLabel.setStyleName("axis_label");
                        axisLabel.getElement().setAttribute("id", evaluationAxis.getId());

                        axisFlexTable.setWidget(currentRow, currentColumn, axisLabel);
                        formatter.setStyleName(currentRow, currentColumn, "axis_style");

                        currentColumn++;
                        position++;

                    }


                    axisFlexTable.addClickHandler(new ClickHandler()
                    {
                        @Override
                        public void onClick(ClickEvent event)
                        {
                            int cellIndex = axisFlexTable.getCellForEvent(event).getCellIndex();
                            int rowIndex = axisFlexTable.getCellForEvent(event).getRowIndex();

                            Element element = (Element) axisFlexTable.getCellForEvent(event).getElement().getFirstChild();

                            //load evaluation tracks from the server
                            RemarkService.App.getInstance().getEvaluationAxis(element.getAttribute("id"), new EvaluationAxisAsyncCallback());

//                            Window.alert(element.getAttribute("id") + " " + cellIndex + " " + rowIndex);
                        }
                    });


                    detailVerticalPanel.add(axisFlexTable);


                } else
                {
                    History.newItem(Constants.LOGIN);
                }
            }
        }
    }


    private class LearnerListAsyncCallback implements AsyncCallback<ServerResponse>
    {

        public void onFailure(Throwable caught)
        {
            //headerPanel.successfulLogin( "", centerPanel, 900 );

            History.newItem(Constants.LOGIN);
        }

        public void onSuccess(ServerResponse result)
        {
            ProvidesKey<Learner> keyProvider = new ProvidesKey<Learner>()
            {
                public Object getKey(Learner item)
                {
                    return (item == null) ? null : item.getId();
                }
            };

            if (!StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE))
            {
                History.newItem(Constants.SESSION_EXPIRED_CODE);

            } else if (result.getErrorMessage() == null || result.getErrorMessage().length() < 1)
            {

                // Create a CellList using the keyProvider.
                CellList<Learner> cellList = new CellList<Learner>(new LearnerCell(), keyProvider);

                cellList.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

                // Add a selection model to handle user selection.
                final SingleSelectionModel<Learner> selectionModel = new SingleSelectionModel<Learner>(keyProvider);

                cellList.setSelectionModel(selectionModel);

                manageLearnerSelection(selectionModel);

                cellList.setStyleName("cell_list");

                cellList.setRowCount(result.getLearners().size(), true);

                // Push the data into the widget.
                cellList.setRowData(0, result.getLearners());


                if (learnerListPanel.getWidgetCount() > 1)
                {
                    learnerListPanel.remove(1);
                }

                learnerListPanel.add(cellList);

            } else
            {
                History.newItem(Constants.LOGIN);
            }
        }
    }

    //Press on specific axis

    private class EvaluationAxisAsyncCallback implements AsyncCallback<ServerResponse>
    {
        public void onFailure(Throwable caught)
        {

            History.newItem(Constants.LOGIN);
        }

        public void onSuccess(ServerResponse result)
        {
            if (!StringUtils.isEmpty(result.getNextStep()) && result.getNextStep().equalsIgnoreCase(Constants.SESSION_EXPIRED_CODE))
            {
                History.newItem(Constants.SESSION_EXPIRED_CODE);

            } else
            {
                if (result.getErrorMessage() == null || result.getErrorMessage().length() < 1)
                {
                    //load evaluation list
                    Label label = new Label();

                    label.setText(result.getEvaluationAxis().getName());

                    label.setStyleName("axis_top_label");

                    int widgetCount = learnerInfoPanel.getWidgetCount();

                    for (int i = 0; i < widgetCount; i++)
                    {
                        //remove all after the second widget - that is after evaluation track

                        if (i > 1)
                        {
                            learnerInfoPanel.remove(i);
                        }
                    }

                    learnerInfoPanel.add(label);

                    detailVerticalPanel.clear();

                    Widget milestoneTable = createMilestoneTable(result.getEvaluationAxis());

                    detailVerticalPanel.add(milestoneTable);


                    detailVerticalPanel.add(getCommentPanel("Remedial Comment: "));

                    detailVerticalPanel.add(getCommentPanel("Encouraging Comment: "));


                } else
                {
                    History.newItem(Constants.LOGIN);
                }
            }
        }
    }

    private Widget getCommentPanel(String commentLabelText)
    {
        HorizontalPanel additionalCommentHPanel = new HorizontalPanel();

        additionalCommentHPanel.setStyleName("comment_panel");

        Label additionalCommentLbl = new Label(commentLabelText);

        additionalCommentLbl.setStyleName("comment_label_title");

        TextArea textArea = new TextArea();

        textArea.setCharacterWidth(100);
        textArea.setVisibleLines(5);

        additionalCommentHPanel.add(additionalCommentLbl);
        additionalCommentHPanel.add(textArea);

        additionalCommentHPanel.setCellHorizontalAlignment(additionalCommentLbl, HasHorizontalAlignment.ALIGN_LEFT);

        additionalCommentHPanel.setCellHorizontalAlignment(textArea, HasHorizontalAlignment.ALIGN_RIGHT);


        return additionalCommentHPanel;
    }

}
