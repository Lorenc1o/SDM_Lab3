package com.bdma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    String id;
    String name;
    String dob;

    @Override
    public String toString(){
        return id + ";" + name + ";" + dob;
    }
}
