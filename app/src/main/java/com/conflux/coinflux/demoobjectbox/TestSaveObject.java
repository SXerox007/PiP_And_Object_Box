package com.conflux.coinflux.demoobjectbox;


/**
 * Developer: SUMIT_THAKUR
 * Dated: 30/06/17.
 */

public class TestSaveObject {

    long id;
    private String name;


    public TestSaveObject(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public TestSaveObject() {
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

//    public ArrayList<String> getStrings() {
//        return strings;
//    }
//
//    public void setStrings(final ArrayList<String> strings) {
//        this.strings = strings;
//    }
}
