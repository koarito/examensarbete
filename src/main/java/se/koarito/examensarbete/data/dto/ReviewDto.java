package se.koarito.examensarbete.data.dto;

import se.koarito.examensarbete.data.enm.Status;

import java.util.Set;

public interface ReviewDto {
    long getId();

    String getJiraId();

    String getGitLink();

    String getBranch();

    SimpleUser getAuthor();

    Status getStatus();

    Set<SimpleFeedback> getGrades();

    Set<SimpleUser> getReviewers();

    interface SimpleFeedback {
        long getId();

        Boolean getGrade();

        SimpleUser getUser();
    }

    interface SimpleUser {
        long getId();

        String getFirstName();

        String getLastName();
    }
}
