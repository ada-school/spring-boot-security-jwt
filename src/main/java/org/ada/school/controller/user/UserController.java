package org.ada.school.controller.user;

import org.ada.school.repository.document.User;
import org.ada.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping( "/v1/user" )
public class UserController
{

    private final UserService userService;

    public UserController( @Autowired UserService userService )
    {
        this.userService = userService;
    }


    @GetMapping
    public List<User> all()
    {
        return userService.all();
    }

    @GetMapping( "/{id}" )
    public User findById( @PathVariable String id )
    {
        return userService.findById( id );
    }


    @PostMapping
    public ResponseEntity<User> create( @RequestBody UserDto userDto )
    {
        return ResponseEntity.ok( userService.create( userDto ) );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<User> update( @RequestBody UserDto userDto, @PathVariable String id )
    {
        return ResponseEntity.ok( userService.update( userDto, id ) );
    }

    @DeleteMapping( "/{id}" )
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> delete( @PathVariable String id )
    {
        return ResponseEntity.ok( userService.deleteById( id ) );
    }

}
