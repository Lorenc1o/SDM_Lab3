package com.bdma;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Publications {

    String id;
    String title;
    String year;
    String publisher;
    String chair;
    String city;
    String area;
    String type;
    String venueId;

    @Override
    public String toString(){
        return id + ";" + title + ";" + year + ";" + publisher + ";" + chair + ";" + city + ";" + area + ";" + type + ";" + venueId;
    }
}
