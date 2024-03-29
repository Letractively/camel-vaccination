package core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo
 */
public class Logout extends HttpServlet {
    
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
            String title = "Logout";
            String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                    + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            
            htmlPage += htmlIntro;
            /* Cancellare anche i cookie */
            
            request.getSession().invalidate();
            htmlPage += "<div class=\"jump\">";
            htmlPage += "<p class=\"jump\">Sei stato sloggato con successo. <br />";
            htmlPage += "Verrai a breve reindirizzato alla Home Page</p>";
            htmlPage += "<p class=\"jump\"><a href=\"" + Macro.BASE + "\">Oppure clicca qui per continuare...</a></p>";
            htmlPage += "</div>";
            htmlPage += htmlOutro;
            out.print(htmlPage);
            response.setHeader("Refresh", "4; url=" + Macro.BASE);
            
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
