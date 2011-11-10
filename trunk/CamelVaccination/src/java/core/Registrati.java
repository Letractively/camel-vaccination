package core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

/**
 *
 * @author administrator
 */
public class Registrati extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String htmlPage = "";
            String title = "Registrazione";
            String htmlIntro = "<HTML><HEAD><title>" + title + "</title></HEAD><BODY>";
            
            htmlPage += htmlIntro;
            
            String userCaptchaResponse = request.getParameter("captcha");
            boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
            
            htmlPage += userCaptchaResponse + "  TANTECOCCOLE";
            if (captchaPassed) {
                htmlPage += "OK it matches";
                // proceed to submit action
            } else {
                htmlPage += "not OK you typed wrong letters";                
                // return error to user
            }            
            
            String htmlOutro = "</BODY></HTML>";            
            htmlPage += htmlOutro;
            out.print(htmlPage);
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
