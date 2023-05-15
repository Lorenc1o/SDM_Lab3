package com.bdma;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Person {

    String id;
    String name;
    String dob;
    String affiliation;

    @Override
    public String toString(){
        return id + ";" + name + ";" + dob + ";" + affiliation;
    }
}
