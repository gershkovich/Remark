package org.siberian.remark.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.*;
import org.siberian.remark.client.RemarkService;
import org.siberian.remark.client.model.Evaluation;
import org.siberian.remark.client.model.EvaluationAxis;
import org.siberian.remark.client.model.Learner;
import org.siberian.remark.client.model.ServerResponse;
import org.siberian.remark.client.utils.Constants;
import org.siberian.remark.dao.MilestoneDAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemarkServiceImpl extends RemoteServiceServlet implements RemarkService
{

    public ServerResponse getPeople(String commentText)
    {
        ServerResponse serverResponse = new ServerResponse();

        Learner learner = new Learner();

        learner.setFirstName("Barbaduk");
        learner.setLastName("Lamar");
        learner.setId("id1");

        serverResponse.getLearners().add(learner);

        learner = new Learner();

        learner.setFirstName("Anna");
        learner.setLastName("Cooper");
        learner.setId("id2");

        serverResponse.getLearners().add(learner);

        learner = new Learner();

        learner.setFirstName("Nina");
        learner.setLastName("Franklin");
        learner.setId("id3");

        serverResponse.getLearners().add(learner);

        return serverResponse;
    }

    public ServerResponse checkForSessionTimeout()
    {

        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String logoutUser()
    {

        return "logout";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServerResponse checkCredentials(String loginNameText, String userPasswordText, boolean flag)
    {
        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServerResponse successfulLogin()
    {

        ServerResponse serverResponse = new ServerResponse();

        serverResponse.setUserName("Peter");

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServerResponse getEvaluationTracks(String learnerId)
    {


        ServerResponse serverResponse = new ServerResponse();

        MongoClient mongoClient = null;

        try
        {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));

            final DB blogDatabase = mongoClient.getDB("remark");

            MilestoneDAO milestoneDAO = new MilestoneDAO(blogDatabase);

            List<DBObject> milestones = milestoneDAO.findBySubSpecialty("Internal Medicine");

            for (DBObject dbObject : milestones)
            {
                Map map = dbObject.toMap();

                EvaluationAxis evaluationAxis = new EvaluationAxis();

                evaluationAxis.setId(dbObject.get("_id").toString());

                evaluationAxis.setSeq(Integer.valueOf(dbObject.get("seq").toString()));

                evaluationAxis.setName(dbObject.get("axis").toString());

                evaluationAxis.setLearningCategory(dbObject.get("learningCategory").toString());

                serverResponse.getEvaluationAxises().add(evaluationAxis);
            }

        } catch (UnknownHostException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally
        {
            if (mongoClient != null)
            {
                mongoClient.close();
            }
        }

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServerResponse getEvaluationAxis(String id)
    {
        ServerResponse serverResponse = new ServerResponse();

        MongoClient mongoClient = null;

        try
        {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));

            final DB blogDatabase = mongoClient.getDB("remark");

            MilestoneDAO milestoneDAO = new MilestoneDAO(blogDatabase);

            DBObject axis = milestoneDAO.findById(id);

            EvaluationAxis evaluationAxis = new EvaluationAxis();

            evaluationAxis.setId(axis.get("_id").toString());

            evaluationAxis.setSeq(Integer.valueOf(axis.get("seq").toString()));

            evaluationAxis.setName(axis.get("axis").toString());

            evaluationAxis.setLearningCategory(axis.get("learningCategory").toString());

            setMilestoneTypes(axis, evaluationAxis, Constants.ASPIRATIONAL);
            setMilestoneTypes(axis, evaluationAxis, Constants.DEFICIENCY);
            setMilestoneTypes(axis, evaluationAxis, Constants.PROGRESS1);
            setMilestoneTypes(axis, evaluationAxis, Constants.PROGRESS2);
            setMilestoneTypes(axis, evaluationAxis, Constants.UNSUPERVISED);

            serverResponse.setEvaluationAxis(evaluationAxis);


        } catch (UnknownHostException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally
        {
            if (mongoClient != null)
            {
                mongoClient.close();
            }
        }

        return serverResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void setMilestoneTypes(DBObject axis, EvaluationAxis evaluationAxis, String category)
    {
        ArrayList<BasicDBObject> milestones = (ArrayList<BasicDBObject>) axis.get(category);

        for (DBObject milestone : milestones)
        {
            Evaluation evaluation = new Evaluation();

            evaluation.setText(milestone.get("name").toString());

            evaluation.setSeq(Integer.valueOf(milestone.get("seq").toString()));

            evaluation.setCategory(category);

            if (category.equalsIgnoreCase(Constants.ASPIRATIONAL))
            {
                evaluationAxis.getAspirational().add(evaluation);

            } else if (category.equalsIgnoreCase(Constants.DEFICIENCY))

            {
                evaluationAxis.getDeficiency().add(evaluation);

            } else if (category.equalsIgnoreCase(Constants.PROGRESS1))

            {
                evaluationAxis.getProgress1().add(evaluation);

            } else if (category.equalsIgnoreCase(Constants.PROGRESS2))

            {
                evaluationAxis.getProgress2().add(evaluation);
            } else if (category.equalsIgnoreCase(Constants.UNSUPERVISED))

            {
                evaluationAxis.getUnsupervised().add(evaluation);
            }
        }
    }
}