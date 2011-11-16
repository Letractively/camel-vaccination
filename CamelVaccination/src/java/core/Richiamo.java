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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        
        try {
            HttpSession session = request.getSession();
            User loggedUser = (User) session.getAttribute("loggedUser");
            if (loggedUser == null){
                Log4k.warn(Richiamo.class.getName(), "un utente non loggato non dovrebbe essere qui");
            } else if (!loggedUser.getIsDoctor()){
                Log4k.warn(Welcome.class.getName(), "un paziente non dovrebbe essere qui");
            } else {
                
                out.println("<HTML><HEAD><title>Richiamo</title></HEAD><BODY>");
                if(request.getParameter("date")==null){
                    out.println("<form action=\"Richiamo\" method=\"GET\">");
                    out.println("<label for=\"Vaccinazioni effettuate prima di (giorni)\"></label><input type=\"text\" name=\"date\" value=\"\" />");
                    out.println("<input type=\"submit\" name=\"Submit\" value=\"Cerca\" />");
                    out.println("</form>");
                }
                else{
                    int doctorID = loggedUser.getId();
                    dbManager dbMan = new dbManager();
                    String s = request.getParameter("date");
                    ResultSet r = dbMan.getPreviousVaccinationsPatients(doctorID, s);
                    
                    //dobbiamo gestire il caso in cui r sia vuoto!!!!!
                    
                    out.println("<TABLE>");
                    out.println("<TR>");
                    out.println("<TD>Paziente</TD>");
                    out.println("<TD>Foto</TD>");
                    out.println("<TD>Data di vaccinazione</TD>");
                    out.println("</TR>");
                    
                    while(r.next()){
                        String nome = r.getString("name");
                        String cognome = r.getString("surname");
                        String foto = r.getString("pictures");
                        String vacc = r.getString("vaccination_date");
                        
                        out.println("<TR>");
                        out.println("<TD>"+nome+cognome+"</TD>");
                        out.println("<TD>"+"<img src=\""+foto+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>");
                        out.println("<TD>"+vacc+"</TD>");
                        out.println("</TR>");
                        
                    }
                    
                    out.println("</TABLE>");
                    //PULSANTI DI STAMPA PDF
                    
                    //PULSANTE ANNULLA (CHIEDERE ABI PER JAVASCRIPT)
                    out.println("</BODY></HTML>");
                }
            }
        } catch (SQLException ex) {
            Log4k.error(Richiamo.class.getName(), ex.getMessage());
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
