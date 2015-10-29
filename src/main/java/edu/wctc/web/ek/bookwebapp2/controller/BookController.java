package edu.wctc.web.ek.bookwebapp2.controller;

import edu.wctc.web.ek.bookwebapp2.entity.Author;
import edu.wctc.web.ek.bookwebapp2.entity.Book;
import edu.wctc.web.ek.bookwebapp2.service.AuthorService;
import edu.wctc.web.ek.bookwebapp2.service.BookService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author emmakordik
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String INDEX_PAGE = "/index.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_UPDATE_ACTION = "updateAdd";
    private static final String ADD_EDIT_ACTION = "addEdit";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String FIND_AUTHOR_ACTION = "showByAuthor";
    private static final String UPDATE_PAGE = "/editAddBook.jsp";
    private static final String HOME_ACTION = "home";
   
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx = 
                WebApplicationContextUtils.getWebApplicationContext(sctx);
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
        BookService bookService = (BookService) ctx.getBean("bookService");
        
        //Sets a default page
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        //Variables for processing dat        
        try {
            
            /*
             Determine what action to take based on a passed on QueryString
             Parameter
             */
            switch(action){
                case LIST_ACTION: 
                    destination = LIST_PAGE;
                    refreshBooksList(request, bookService);
                    break;
                case ADD_UPDATE_ACTION:
                    destination = UPDATE_PAGE;
                    
                    String bookID = request.getParameter("updateBook");
                    if(bookID != null){
                        Book book = bookService.findById(bookID);
                        request.setAttribute("book", book);
                    }
                    
                    request.setAttribute("authors", authService.findAll());

                    break;
                case ADD_EDIT_ACTION: 
                    
                    destination = LIST_PAGE;
                        String title = request.getParameter("title");
                        String isbn = request.getParameter("isbn");
                        String authorId = request.getParameter("author");
                        Author author = authService.findById(authorId);
                    Book book; 
                    
                    if(request.getParameter("updateId") == null){
                        book = new Book(0);
                        book.setAuthorId(author);
                        book.setIsbn(isbn);
                        book.setTitle(title);
                    }else{
                        String bookId = request.getParameter("updateId");
                        book = bookService.findById(bookId);
                        book.setAuthorId(author);
                        book.setIsbn(isbn);
                        book.setTitle(title);
                    }
                    bookService.edit(book);
                    refreshBooksList(request, bookService);
                    break;
                case DELETE_ACTION: 
                    String bookId = request.getParameter("deleteBook");
                    Book bookDelete = bookService.findById(bookId);
                    bookService.remove(bookDelete);
                    
                    destination = LIST_PAGE;
                    refreshBooksList(request,bookService);
                    break;
                case FIND_AUTHOR_ACTION:
                    destination = LIST_PAGE;
                    String authorName = request.getParameter("searchAuthor");
                    List<Book> books = bookService.findBooksforAuthor(authorName);
                    if(books.isEmpty()){
                        request.setAttribute("errMsg", "Author has no books. Check that the name is entered correctly");
                    }else{
                        request.setAttribute("books", books);
                        request.setAttribute("search", "author");
                    }
                    break;
                case HOME_ACTION:
                    destination = INDEX_PAGE;
                    break;
                default: 
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
            }
            
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    private void refreshBooksList(HttpServletRequest request, BookService service) 
            throws ClassNotFoundException, SQLException{
            List<Book> books = service.findAll();
            request.setAttribute("books", books);   
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
