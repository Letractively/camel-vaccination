/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.mysql.jdbc.ResultSet;
import dbManagement.dbManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logManagement.Log4k;
import userManagement.User;

/**
 *
 * @author administrator
 */
@WebServlet(name = "Richiamo", urlPatterns = {"/Richiamo"})
public class Richiamo extends HttpServlet {
    
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
        
        out.println("<HTML><HEAD><title>Richiamo</title></HEAD><BODY>");
        
               
        try {
            String seconds = "";
            String date = "date";
                    
            if(request.getParameter("seconds")!=null)
                 seconds = request.getParameter("seconds");
            
            out.println("<form action=\"Richiamo\" method=\"GET\">");
            out.println("<label for=\"date\">Vaccinazioni effettuate prima di (secondi)</label>"
                    + "<input type=\"text\" id=\"date\" name=\""+date+"\" value=\""+seconds+"\" />");
            out.println("<input type=\"submit\" name=\"Submit\" value=\"Cerca\" />");
            out.println("</form>");
            
            if(request.getParameter(date)!=null){
                
                String s = request.getParameter("seconds");
                User loggedUser = (User) request.getSession().getAttribute("loggedUser");
                int doctorID = loggedUser.getId();
                dbManager db = new dbManager();
                ResultSet r = db.getPreviousVaccinationsPatients(doctorID, s);
                
                out.println("<form action=\"Conferma\" method=\"POST\">");
                
                out.println("<TABLE>");
                out.println("<TR>");
                out.println("<TD>Paziente</TD>");
                out.println("<TD>Foto</TD>");
                out.println("<TD>Data di vaccinazione</TD>");
                out.println("</TR>");
                try {
                    while(r.next()){
                        String checkboxname = "patients";
                        String id = r.getString("ID"); //CONTROLLARE NOME COL DATABASE
                        String nome = r.getString("name");
                        String cognome = r.getString("surname");
                        String foto = r.getString("pictures");
                        String vacc = r.getString("vaccination_date");
                        
                        out.println("<TR>");
                        out.println("<TD>"+id+"</TD>");
                        out.println("<TD>"+nome+cognome+"</TD>");
                        out.println("<TD>"+nome+cognome+"</TD>");
                        out.println("<TD>"+"<img src=\""+foto+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>");
                        out.println("<TD>"+vacc+"</TD>");
                        out.println("<TD><input type=\"checkbox\" name=\""+checkboxname+"\" value=\""+id+"\" /></TD>");
                        out.println("</TR>");
                        
                    }
                } catch (SQLException ex) {
                    out.println("</BODY></HTML>");
                    String className = "Richiamo";
                    Log4k.error(className, ex.getMessage());
                    
                }
                out.println("</TABLE>");
                
                out.println("<BR><input type=\"submit\" name=\"Conferma\" value=\"Conferma\" />");
                out.println("</form>");
                
                out.println("<a href=\"Welcome\" title=\"Home\">Torna alla Home</a>");
            }
        } finally {
            out.println("</BODY></HTML>");
            out.close();
        }
        
        out.println("</BODY></HTML>");
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
