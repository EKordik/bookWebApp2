package edu.wctc.web.ek.bookwebapp2.controller;


import edu.wctc.web.ek.bookwebapp2.entity.Author;
import edu.wctc.web.ek.bookwebapp2.service.AuthorFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "insert";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String UPDATE_FIND_ACTION = "findUpdate";
    private static final String UPDATE_PAGE = "/editAuthor.jsp";
    private static final String PREFERENCE_PAGE = "/preferences.jsp";
    private static final String PREF_ACTION = "preference";
    private static final String PREF_SET_ACTION = "setPref";
    private static final String HOME_ACTION = "home";
    private static final String DEFAULT_BTN_CLASS = "btn-primary";

    
    @Inject
    private AuthorFacade service;
    
    //Sets a session object with a default theme for the page. This can be changed by the user
    String themeColor = null;

        
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
        
        //Sets default color
        HttpSession session = request.getSession();
        
        if(themeColor == null){
            themeColor = "btn-primary";
        }
        session.setAttribute("btnClass", themeColor);
        
        //Sets a default page
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        //Variables for processing data
        String authorID;
        String authorName;
        String date;
        Author author;
        DateFormat format = new SimpleDateFormat();
        
        try {
            
            /*
             Determine what action to take based on a passed on QueryString
             Parameter
             */
            switch(action){
                case LIST_ACTION: 
                    refreshAuthorsList(request, service);
                    break;
                case ADD_ACTION:
                    authorName = request.getParameter("addName");
                    author = new Author(0);
                    author.setAuthorName(authorName);                  
                   author.setDateCreated(new Date());
                    
                    service.edit(author);
                
                    refreshAuthorsList(request, service);
                    break;
                case DELETE_ACTION: 
                    authorID = request.getParameter("deleteAuthor");
                    author = service.find(new Integer(authorID));
                    service.remove(author);
                    
                    refreshAuthorsList(request, service);
                    break;
                case UPDATE_ACTION: 
                    authorName = request.getParameter("updateName");
                    authorID = request.getParameter("updateId");
                    
                    Author a = service.find(new Integer(authorID));
                    a.setAuthorName(authorName);
                    
                    service.edit(a);
                
                    refreshAuthorsList(request, service);
                    destination = LIST_PAGE;
                    break;
                case UPDATE_FIND_ACTION:
                    authorID = request.getParameter("updateAuthor");
                    author = service.find(new Integer(authorID));
                    
                    request.setAttribute("author", author);
                    destination = UPDATE_PAGE;
                    break;
                case PREF_ACTION:
                    destination = PREFERENCE_PAGE;
                    break;
                case PREF_SET_ACTION:
                    themeColor = request.getParameter("themeColor");

                    session.setAttribute("btnClass", themeColor);

                    destination = PREFERENCE_PAGE;
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
    
    
    private void refreshAuthorsList(HttpServletRequest request, AuthorFacade service) 
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
