package com.bdma;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Venue {

    String id;
    String venueType;
    String conferenceType;
    String name;
    String editor;
    String chair;
    String url;
    String publication;
    String area;
    String issn;
    String periodicity;

    @Override
    public String toString(){
        return id+ ";" + area + ";" + publication+ ";" + venueType+ ";" + conferenceType+ ";" + editor+ ";" + url+ ";" + name+ ";" + chair + ";" + issn + ";" + periodicity;
    }

}
