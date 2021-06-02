package com.example.pis_entrega1;

/**
 * Class to store data of the note and interact with it.
 */
public abstract class Notes {
    private String date;
    private String name;
    private String content;
    private String id;
    private int type;
    private boolean checked;

    /**
     * Constructor method of the class.
     */
    public Notes(){
        this.name = "";
    }

    /**
     * Constructor method of the class with parameters.
     * @param name String
     */
    public Notes(String name) {
        this.name = name;
    }

    /**
     *
     * @param name String
     * @param id String
     */
    public Notes(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter method of the attribute "id".
     * @return String
     */
    public String getID() {
        return id;
    }

    /**
     * Setter method of the attribute "id".
     * @param id String
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Getter method of the attribute "checked".
     * @return boolean
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Setter method of the attribute "checked".
     * @param checked boolean
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Getter method of the attribute "date".
     * @return String
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method of the attribute "date".
     * @param date String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Abstract method to save data of the note.
     */
    abstract void saveNote();

    /**
     * Getter method of the attribute "name".
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of the attribute "name".
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of the attribute "content".
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method of the attribute "content".
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Setter method of the attribute "type".
     * @param type int
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Abstract method to delete the note.
     */
    public abstract void delete();
}
