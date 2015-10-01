package edu.wctc.web.ek.bookwebapp2.controller;

import edu.wctc.web.ek.bookwebapp2.model.Author;
import edu.wctc.web.ek.bookwebapp2.model.AuthorDAO;
import edu.wctc.web.ek.bookwebapp2.model.IAuthorDAO;
import edu.wctc.web.ek.bookwebapp2.model.AuthorService;
//import edu.wctc.web.demo.bookwebapp.model.ConnPoolAuthorDao;
import edu.wctc.web.ek.bookwebapp2.model.DatabaseStrategy;
import edu.wctc.web.ek.bookwebapp2.model.MySqlDb;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * The main controller for author-related activities
 *
 * @author ekordik
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "insert";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";

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
        
      
       
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        
        try {
            
            AuthorService service = injectDependencesGetAuthorService();
            

            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
            switch(action){
                case LIST_ACTION: {
                    refreshAuthorsList(request, service);
                    destination = LIST_PAGE;
                }break;
                case ADD_ACTION:{
                    String authorName = request.getParameter("addName");
                    String date = request.getParameter("addDate");
                
                    service.saveAuthor(authorName, date);
                
                    refreshAuthorsList(request, service);
                    destination = LIST_PAGE;
                }break;
                case DELETE_ACTION: {
                    String authorID = request.getParameter("deleteAuthor");
                    service.deleteAuthor(authorID);
                
                    List<Author> authors = null;
                    authors = service.getAllAuthors();
                
                    request.setAttribute("authors", authors);
                    destination = LIST_PAGE;
                }break;
                case UPDATE_ACTION: {
                    String authorName = request.getParameter("updateName");
                    String dateCreated = request.getParameter("updateDate");
                    String authorId = request.getParameter("updateId");
                
                    service.updateAuthor(authorId, authorName, dateCreated);
                
                    refreshAuthorsList(request, service);
                    destination = LIST_PAGE;
                }break;
                default: {
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                }
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
            List<Author> authors = service.getAllAuthors();
            request.setAttribute("authors", authors);
           
    }

    private AuthorService injectDependencesGetAuthorService() throws Exception{
            String dbClassName = this.getServletContext().getInitParameter("dbStrategy");
            String driverClass = this.getServletContext().getInitParameter("driverClass");
            String url = this.getServletContext().getInitParameter("url");
            String userName = this.getServletContext().getInitParameter("username");
            String password = this.getServletContext().getInitParameter("password");
            String authorDAOClassName = this.getServletContext().getInitParameter("authorDAO");
            
            Class dbClass = Class.forName(dbClassName);
            DatabaseStrategy db =  (DatabaseStrategy)dbClass.newInstance();
            
            IAuthorDAO authorDao;
            Class daoClass = Class.forName(authorDAOClassName);
            Constructor constructor = daoClass.getConstructor(new Class[] {
                DatabaseStrategy.class, String.class, String.class, String.class, String.class});
                   
            if(constructor != null){
                Object[] constructorArgs = new Object[]{db, driverClass, url, userName, password};
                authorDao = (IAuthorDAO)constructor.newInstance(constructorArgs);
                
            }else{
                authorDao = new AuthorDAO(db, "com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", "root", "admin");
                
                            /*
             Here's what the connection pool version looks like.
             */
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("jdbc/book");
//            AuthorDaoStrategy authDao = new ConnPoolAuthorDao(ds, new MySqlDbStrategy());
//            AuthorService authService = new AuthorService(authDao);
            }
            
            return new AuthorService(authorDao);
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
