package se.koarito.examensarbete.data.dto;

import se.koarito.examensarbete.data.enm.Role;

public interface UserDto {

    long getId();

    String getFirstName();

    String getLastName();

    Role getRole();
}
