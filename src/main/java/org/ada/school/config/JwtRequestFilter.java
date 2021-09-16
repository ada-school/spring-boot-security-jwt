package org.ada.school.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.ada.school.utils.Constants.CLAIMS_ROLES_KEY;
import static org.ada.school.utils.Constants.COOKIE_NAME;

@Component
public class JwtRequestFilter
        extends OncePerRequestFilter
{
    @Value( "${app.secret}" )
    String secret;

    public JwtRequestFilter()
    {
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
            throws ServletException, IOException
    {
        String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION );

        if ( HttpMethod.OPTIONS.name().equals( request.getMethod() ) )
        {
            response.setStatus( HttpServletResponse.SC_OK );
            filterChain.doFilter( request, response );
        }
        else
        {
            try
            {
                Optional<Cookie> optionalCookie =
                        request.getCookies() != null ? Arrays.stream( request.getCookies() ).filter(
                                cookie -> Objects.equals( cookie.getName(), COOKIE_NAME ) ).findFirst() : Optional.empty();

                String headerJwt = null;
                if ( authHeader != null && authHeader.startsWith( "Bearer " ) )
                {
                    headerJwt = authHeader.substring( 7 );
                }
                String token = optionalCookie.isPresent() ? optionalCookie.get().getValue() : headerJwt;

                if ( token != null )
                {
                    Jws<Claims> claims = Jwts.parser().setSigningKey( secret ).parseClaimsJws( token );
                    Claims claimsBody = claims.getBody();
                    String subject = claimsBody.getSubject();
                    List<String> roles  = claims.getBody().get( CLAIMS_ROLES_KEY , ArrayList.class);

                    if (roles == null) {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token roles");
                    } else {
                        SecurityContextHolder.getContext().setAuthentication( new TokenAuthentication( token, subject, roles));
                    }

                    request.setAttribute( "claims", claimsBody );
                    request.setAttribute( "jwtUserId", subject );
                    request.setAttribute("jwtUserRoles", roles);

                }
                filterChain.doFilter( request, response );
            }
            catch ( MalformedJwtException e )
            {
                response.sendError( HttpStatus.BAD_REQUEST.value(), "Missing or wrong token" );
            }
            catch ( ExpiredJwtException e )
            {
                response.sendError( HttpStatus.UNAUTHORIZED.value(), "Token expired or malformed" );
            }
        }
    }

}