package org.siberian.remark.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.siberian.remark.client.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: petergershkovich
 * Date: 9/1/13
 * Time: 2:10 PM
 */
public class Person implements IsSerializable
{

    private String id;
    private String lastName;
    private String firstName;
    private String middleName;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getFullName()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getLastName());

        stringBuilder.append(", ");

        stringBuilder.append(getFirstName());

        if (StringUtils.isNotEmpty(getMiddleName()))
        {
            stringBuilder.append(getMiddleName());
        }

        return stringBuilder.toString();


    }
}
