package se.koarito.examensarbete.data.dto;

import java.util.Set;

public interface TeamDto {

    long getId();

    String getName();

    SimpleUser getTeamLeader();

    Set<SimpleUser> getDevelopers();

    interface SimpleUser {
        long getId();

        String getFirstName();

        String getLastName();
    }
}
