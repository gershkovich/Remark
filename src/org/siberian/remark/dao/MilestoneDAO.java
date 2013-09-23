package org.siberian.remark.dao;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: petergershkovich
 * Date: 9/2/13
 * Time: 3:15 PM

 */
public class MilestoneDAO
{
    DBCollection milestones;

    public MilestoneDAO(final DB blogDatabase) {
        milestones = blogDatabase.getCollection("milestones");
    }

    public DBObject findById(String id) {
        DBObject milestonesOne = milestones.findOne(new BasicDBObject("_id", new ObjectId(id)));

        return milestonesOne;
    }

    public List<DBObject> findBySubSpecialty(final String subSpecialty) {
        List<DBObject> milestoneList;
        BasicDBObject query = new BasicDBObject("subSpecialty", subSpecialty);
        System.out.println("/subSpecialty query: " + query.toString());
        DBCursor cursor = milestones.find(query).sort(new BasicDBObject().append("_id", 1));
        try {
            milestoneList = cursor.toArray();
        } finally {
            cursor.close();
        }
        return milestoneList;
    }



    public void addSubSpecialty(final String subSpecialty, final int id) {

        BasicDBObject subSpecialtyDoc = new BasicDBObject("name", subSpecialty);

        WriteResult result = milestones.update(new BasicDBObject("_id", id),
                new BasicDBObject("$push", new BasicDBObject("subSpecialty", subSpecialtyDoc)), false, false);
    }


}