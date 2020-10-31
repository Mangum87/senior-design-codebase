package com.fitbitsample;

import io.paperdb.Book;
import io.paperdb.Paper;


/**
 * Key/Value store used to save OAuth tokens and
 * user data.
 */
public class PaperDB {
    private static PaperDB paperDB;

    private PaperDB() {
    }

    /**
     * Get instance of PaperDB object.
     * @return
     */
    public static PaperDB getInstance() {
        if (paperDB == null) {
            paperDB = new PaperDB();
        }
        return paperDB;
    }

    /**
     * Get book the key/value pairs are stored in.
     * @return Book object
     */
    public Book get() {
        return Paper.book();
    }


    /**
     * Write a key/value pair.
     * @param key Key to search by
     * @param value Value of key
     */
    public void write(String key, Object value) {
        Paper.book().write(key, value);
    }

}