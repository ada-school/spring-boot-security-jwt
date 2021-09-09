package org.ada.school.controller.auth;

public class LoginDto
{
    String email;

    String password;

    public LoginDto( String email, String password )
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}