package com.bdma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paper {

    String id;
    String paperTitle;
    String area;
    String authorID;
    String corrAuthorID;
    String publicationId;
    String reviewer_1;
    String reviewer_2;
    String review_1;
    String review_2;
    String paperType;
    String paperAbstract;
    String decisions_1;
    String decisions_2;
    String year;
    String url;
    String venueId;

    @Override
    public String toString() {
        return id + ";" + paperTitle + ";" + area + ";" + authorID + ";" + corrAuthorID + ";" + publicationId + ";" + venueId + ";" + reviewer_1 + ";" + reviewer_2 + ";" + review_1 + ";" + review_2 + ";" + paperType + ";" + paperAbstract + ";" + decisions_1 + ";" + decisions_2 + ";" + year + ";" + url;

    }
}
