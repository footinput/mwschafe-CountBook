package com.example.mwschafe_CountBook;

import java.io.Serializable;

/**
 * Created by Max Schafer on 2017-09-30
 */
public class Counter implements Serializable {
    /**
     * The Name.
     */

    public String name;

    /**
     * The Date.
     */
    public String date;

    /**
     * Gets date.
     *
     * @return the date
     */

    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets name.
     *
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets currentValue.
     *
     * @return the currentValue
     */

    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Add current value int.
     *
     * @return the int
     */
    public void addCurrentValue() {
        currentValue++;
    }

    /**
     * Sub current value int.
     *
     * @return the int
     */
    public void subCurrentValue() {
        currentValue--;
    }

    /**
     * Sets currentValue.
     *
     * @param currentValue the currentValue
     */

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Gets initialValue.
     *
     * @return the initialValue
     */

    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Sets initialValue.
     *
     * @param initialValue the initialValue
     */

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */

    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * The Neck.
     */

    public int currentValue;

    /**
     * The Bust.
     */

    public int initialValue;

    /**
     * The Comment.
     */

    public String comment;

    /**
     * Instantiates a new Counter.
     */

    public Counter() {
    }
}
