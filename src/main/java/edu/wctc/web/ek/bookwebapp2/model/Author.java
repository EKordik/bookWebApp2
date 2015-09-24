package edu.wctc.web.ek.bookwebapp2.model;

import java.util.Date;

/**
 *
 * @author emmakordik
 */
public class Author {
    private String authorName;
    private int authorId;
    private Date creationDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Author{" + "authorName=" + authorName + ", authorId=" + authorId + ", creationDate=" + creationDate + '}';
    }
    
    
}
