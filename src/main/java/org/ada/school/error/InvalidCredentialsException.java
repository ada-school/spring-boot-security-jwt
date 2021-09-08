package org.ada.school.error;

public class InvalidCredentialsException extends InternalServerErrorException
{
   public InvalidCredentialsException()
   {
      super( new ServerErrorResponseDto( "Invalid username or password", ErrorCodeEnum.INVALID_USER_CREDENTIALS,
      HttpStatus.UNAUTHORIZED ), HttpStatus.UNAUTHORIZED );
   }
}

