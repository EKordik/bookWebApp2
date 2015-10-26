package edu.wctc.web.ek.bookwebapp2.service;

import edu.wctc.web.ek.bookwebapp2.entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author emmakordik
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {
    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    public List<Book> findBooksbyAuthor(String authorName){
        String jpql = "select b from Book b where b.authorId.authorName = ?1";
        TypedQuery<Book> q = getEntityManager().createQuery(jpql, Book.class);
        q.setParameter(1, authorName);
        return q.getResultList();
    }
}
