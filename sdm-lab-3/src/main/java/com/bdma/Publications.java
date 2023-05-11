package com.bdma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publications {

    String id;
    String title;
    String year;
    String publisher;
    String chair;
    String city;
    String area;

    @Override
    public String toString(){
        return id + ";" + title + ";" + year + ";" + publisher + ";" + chair + ";" + city + ";" + area;
    }
}
