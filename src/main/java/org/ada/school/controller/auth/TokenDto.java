package org.ada.school.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter @Setter
public class TokenDto {

    String token;

    Date expirationDate;

}
