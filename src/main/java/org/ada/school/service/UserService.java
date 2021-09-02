package org.ada.school.service;

import org.ada.school.controller.UserDto;
import org.ada.school.exception.UserNotFoundException;
import org.ada.school.repository.document.User;

import java.util.List;

public interface UserService
{
    User create( UserDto userDto );

    User findById( String id )
        throws UserNotFoundException;

    List<User> all();

    boolean deleteById( String id );

    User update( UserDto userDto, String id );
}
