package com.learnreactiveprogramming.imperative;

import java.util.ArrayList;
import java.util.List;

public class ImperativeExample {
   public static void main(String[] args) {
        var nameList = List.of("alex", "ben", "chloe", "adam", "adam");
        var newNamesList = namesGreaterThanSize(nameList, 3);

        System.out.println("newNamesList : " + newNamesList);
    }

    private static List<String> namesGreaterThanSize(List<String> nameList, int size) {
        var newNameList = new ArrayList<String>();

        for(String name : nameList) {
            if(name.length() > size && !newNameList.contains(name)) {
                newNameList.add(name.toUpperCase());
            }
        }
        return newNameList;
    }
}
