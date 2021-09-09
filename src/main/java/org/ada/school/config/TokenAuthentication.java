package org.ada.school.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthentication
        extends AbstractAuthenticationToken
{
    String token;

    String subject;

    List<String> roles;

    public TokenAuthentication( String token, String subject, List<String> roles )
    {
        super( null );
        this.token = token;
        this.subject = subject;
        this.roles = roles;
    }

    @Override
    public boolean isAuthenticated()
    {
        return !token.isEmpty() && !subject.isEmpty() && !roles.isEmpty();
    }

    @Override
    public Object getCredentials()
    {
        return token;
    }

    @Override
    public Object getPrincipal()
    {
        return subject;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities()
    {
        return roles.stream().map( role -> new SimpleGrantedAuthority( "ROLE_" + role ) ).collect(
                Collectors.toList() );
    }


}