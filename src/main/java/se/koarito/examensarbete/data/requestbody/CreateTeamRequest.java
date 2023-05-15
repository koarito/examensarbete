package se.koarito.examensarbete.data.requestbody;

import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateTeamRequest {
    @Nonnull
    private String name;
    @Nonnull
    private long teamLeaderId;
    @Nonnull
    private Set<Long> devsIds;
}
