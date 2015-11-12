
package edu.wctc.web.ek.bookwebapp2.controller;

import edu.wctc.web.ek.bookwebapp2.entity.Users;
import edu.wctc.web.ek.bookwebapp2.service.UserService;
import java.io.IOException;
import java.util.List;
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
 *
 * @author emmakordik
 */
@WebServlet(name = "PreferencesController", urlPatterns = {"/PreferencesController"})
public class PreferencesController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String INDEX_PAGE = "/index.jsp";
    private static final String PREFERENCE_PAGE = "/preferences.jsp";
    private static final String ADD_EDIT_USER_PAGE = "/addEditUser.jsp";
    private static final String PREF_ACTION = "preference";
    private static final String PREF_SET_ACTION = "setPref";
    private static final String ADD_USER = "insert";
    private static final String ADD_EDIT_USER = "addEditUser";
    private static final String HOME_ACTION = "home";
    private static final String ACTION_PARAM = "action";

    
    //Sets a session object with a default theme for the page. This can be changed by the user
    String themeColor = null;
    
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
        UserService userService = (UserService) ctx.getBean("userService");
        //Sets default color
        HttpSession session = request.getSession();
        
        if(themeColor == null){
            themeColor = "btn-primary";
        }
        session.setAttribute("btnClass", themeColor);
        
        //Sets a default page
        String destination = INDEX_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        
        try {
            
            /*
             Determine what action to take based on a passed on QueryString
             Parameter
             */
            switch(action){

                case PREF_ACTION:
                    destination = PREFERENCE_PAGE;
                    refreshUserList(request, userService);
                    break;
                case PREF_SET_ACTION:
                    themeColor = request.getParameter("themeColor");
                    session.setAttribute("btnClass", themeColor);
                    destination = PREFERENCE_PAGE;
                    refreshUserList(request, userService);
                    break;
                case ADD_USER:
                    destination = PREFERENCE_PAGE;
                    refreshUserList(request, userService);
                    break;
                case ADD_EDIT_USER:
                    String username = request.getParameter("user");
                    
                    if(username == null){
                        //adding new
                    }else{
                       Users u = userService.findByUsername(username);
                       request.setAttribute("user", u);
                    }
                    request.setAttribute("roles", userService.findAllRoles());
                    destination = ADD_EDIT_USER_PAGE;
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

    private void refreshUserList(HttpServletRequest request, UserService service){
        List<Users> users = service.findAll();
        request.setAttribute("users", users);
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
