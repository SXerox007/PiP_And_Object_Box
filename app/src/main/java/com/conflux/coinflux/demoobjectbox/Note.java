package com.conflux.coinflux.demoobjectbox;

/**
 * Developer: SUMIT_THAKUR
 * Dated: 29/06/17.
 */

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Generated;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.apihint.Internal;

@Entity
public class Note {

    @Id
    long id;

    String text;
    String comment;
    Date date;

    @Generated(1759314840)
    @Internal
    /** This constructor was generated by ObjectBox and may change any time. */
    public Note(long id, String text, String comment, Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    @Generated(1272611929)
    public Note() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
