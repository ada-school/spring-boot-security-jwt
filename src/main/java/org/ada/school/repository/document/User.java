package org.ada.school.repository.document;

import org.ada.school.controller.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class User
{
    @Id
    String id;

    String name;

    String lastName;

    @Indexed( unique = true )
    String email;

    Date createdAt;

    public User()
    {
    }

    public User( String name, String lastName, String email )
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        createdAt = new Date();
    }

    public User( UserDto userDto )
    {
        this( userDto.getName(), userDto.getLastName(), userDto.getEmail() );
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void update( UserDto userDto )
    {
        this.name = userDto.getName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
    }


}
