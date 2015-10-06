package edu.wctc.web.ek.bookwebapp2.model;

import java.util.Date;

/**
 * An author Object that has a name, unique identifier, and the date the author
 * was created.
 * 
 * @author emmakordik
 * @version 1.00
 */
public class Author {
    private String authorName;
    private int authorId;
    private Date dateCreated;

    /**
     * An Empty constructor for the author. Does not do anything.
     */
    public Author() {
    }

    /**
     * A constructor that accepts the unique identifier, name, and date the
     * author was created. This method can be used if you know all the data when
     * you are created an instance of this object.
     * 
     * @param authorId - an int containing the unique identifier for the author
     * @param authorName - A String with the author's name
     * @param dateCreated - The date the author was created
     */
    public Author(int authorId, String authorName, Date dateCreated) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
    }

    
    /**
     * Gets the author's name
     * 
     * @return - A String with the author's name
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets the author's name
     * 
     * @param authorName - A String with the author's name
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Gets the unique identifier for the author
     * 
     * @return - an int with the author's unique identifier
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets the author's unique identifier
     * 
     * @param authorId - an int with the unique identifier
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets the date that the author was created on.
     * 
     * @return - A Date object
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date that the author was created on
     * 
     * @param creationDate - A Date object
     */
    public void setDateCreated(Date creationDate) {
        this.dateCreated = creationDate;
    }

    /**
     * This method tells you that the object is an author, what the name is,
     * what the unique identifier is, and on what date the autor was created.
     * 
     * @return - A String
     */
    @Override
    public String toString() {
        return "Author{" + "authorName=" + authorName + ", authorId=" + authorId + ", creationDate=" + dateCreated + '}';
    }

    /**
     * HashCode for the author. Uses the author's unique identifier to generate
     * the hash code.
     * 
     * @return - An int with the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.authorId;
        return hash;
    }

    /**
     * Uses the author's unique identifier to determine if another object is
     * equal to this author.
     * 
     * @param obj- The object to be compared with this object. 
     * @return - true if the objects are equal and false if they are not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }
    
    
    
}
