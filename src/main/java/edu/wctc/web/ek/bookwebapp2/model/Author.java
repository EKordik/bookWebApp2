package edu.wctc.web.ek.bookwebapp2.model;

import java.util.Date;

/**
 *
 * @author emmakordik
 */
public class Author {
    private String authorName;
    private int authorId;
    private Date dateCreated;

    public Author() {
    }

    public Author(int authorId, String authorName, Date dateCreated) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
    }

    
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date creationDate) {
        this.dateCreated = creationDate;
    }

    @Override
    public String toString() {
        return "Author{" + "authorName=" + authorName + ", authorId=" + authorId + ", creationDate=" + dateCreated + '}';
    }
    
    
}
