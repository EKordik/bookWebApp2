package edu.wctc.web.ek.bookwebapp2.controller;


import edu.wctc.web.ek.bookwebapp2.entity.Author;
import edu.wctc.web.ek.bookwebapp2.service.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities. This class determines
 * what action should take place when a request comes in from a web page
 * and sends the user on to the correct web page.
 *
 * @author ekordik
 * @version 1.00
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String INDEX_PAGE = "/index.jsp";
    private static final String BOOK_ADD_PAGE = "/editAddBook.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "insert";
    private static final String ADD_FROM_BOOK_ACTION = "addAuthor";
    private static final String ADD_UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String UPDATE_FIND_ACTION = "findUpdate";
    private static final String UPDATE_PAGE = "/editAuthor.jsp";
    private static final String HOME_ACTION = "home";
    private static final String AJAX_FIND_ALL_ACTION = "ajaxFindAll";
    private static final String AJAX_FIND_BY_ID_ACTION = "ajaxFindByID";


        
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. This class determines which actions to preform and sets the
     * destination page for to send the user too.
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
        AuthorService authorService = (AuthorService) ctx.getBean("authorService");
        PrintWriter out = response.getWriter();
        
        //Sets a default page
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        //Variables for processing data
        String authorID;
        String authorName;
        String date;
        Author author;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            
            /*
             Determine what action to take based on a passed on QueryString
             Parameter
             */
            switch(action){
                case AJAX_FIND_ALL_ACTION:
                    List<Author> authors = authorService.findAll();
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    
                    authors.forEach((authorObj) -> {
                        jsonArrayBuilder.add(Json.createObjectBuilder()
                                .add("authorId", authorObj.getAuthorId())
                                .add("authorName", authorObj.getAuthorName())
                                .add("dateAdded", authorObj.getDateCreated().toString())
                        );
                    });
                    
                    JsonArray authorsJson = jsonArrayBuilder.build();
                    response.setContentType("application/json");
                    out.write(authorsJson.toString());
                    out.flush();
                    return;
                    
                case AJAX_FIND_BY_ID_ACTION:
                    break;
                case LIST_ACTION: 
                    refreshAuthorsList(request, authorService);
                    break;
                case ADD_ACTION:
                    
                    destination = UPDATE_PAGE;
                    break;
                case ADD_FROM_BOOK_ACTION:
                    authorName = request.getParameter("addName");
                    author = new Author(0);
                    author.setAuthorName(authorName);                  
                    author.setDateCreated(new Date());
                    
                    authorService.edit(author);
                    
                    request.setAttribute("newAuthor", authorName);
                    request.setAttribute("Authors", authorService.findAll());
                    destination = BOOK_ADD_PAGE;
                    break;
                case DELETE_ACTION: 
                    authorID = request.getParameter("deleteAuthor");
                    author = authorService.findById(authorID);
                    authorService.remove(author);
                    
                    refreshAuthorsList(request, authorService);
                    break;
                case ADD_UPDATE_ACTION: 
                    authorName = request.getParameter("updateName");
                    authorID = request.getParameter("updateId");
                    date = request.getParameter("updateDate");
                    Date dateAdd = date == null ? new Date() : format.parse(date);
                    
                    Author a;
                    if(authorID == null){
                        a = new Author();
                        a.setAuthorName(authorName);
                        
                        a.setDateCreated(dateAdd);
                    }else{
                        a = authorService.findByIdAndLoadBooks(authorID);
                        a.setAuthorName(authorName);
                        if(date != null){
                            a.setDateCreated(dateAdd);
                        }
                        
                    }
                    authorService.edit(a);
                
                    refreshAuthorsList(request, authorService);
                    destination = LIST_PAGE;
                    break;
                case UPDATE_FIND_ACTION:
                    authorID = request.getParameter("updateAuthor");
                    author = authorService.findByIdAndLoadBooks(authorID);
                    
                    request.setAttribute("author", author);
                    destination = UPDATE_PAGE;
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
    
    
    private void refreshAuthorsList(HttpServletRequest request, AuthorService service) 
            throws ClassNotFoundException, SQLException{
            List<Author> authors = service.findAll();
            request.setAttribute("authors", authors);   
    }

    
    /**
     * Initializes the variables with the correct servlet context information
     * from the web.xml file. This allows for dependency inject to be done one
     * time when the control is first initialized.
     * 
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException{
      
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
