/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package twitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chris
 */
public class Discover extends HttpServlet {

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
        
        
        String followed = request.getParameter("followed_by_user_id");
        String following = request.getParameter("following_user_id");
        
        if (followed != null){
            int followed_by_user_id = Integer.parseInt(followed);
            int following_user_id = Integer.parseInt(following);
            
            if(UserModel.ensureFollowed(followed_by_user_id, following_user_id)){
                UserModel.unfollow(followed_by_user_id, following_user_id);
                response.sendRedirect("Discover");
            }else{
                UserModel.followUser(followed_by_user_id, following_user_id);
                response.sendRedirect("Discover");
            }
        }else{
            ArrayList<User> users = UserModel.getUsers();
            request.setAttribute("users", users);

            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            User user = UserModel.getUser(username);
        
        request.setAttribute("user_id", user.getId());
            String url = "/discover.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response); 
        }
        
        
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
