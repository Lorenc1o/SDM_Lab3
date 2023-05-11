package com.bdma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
