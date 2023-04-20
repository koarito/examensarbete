package se.koarito.examensarbete.data.requestbody;

import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateReviewRequest {
    @Nonnull
    private String jiraId;
    @Nonnull
    private String gitLink;
    @Nonnull
    private String branch;

    private Set<Long> reviewersIds;

}
