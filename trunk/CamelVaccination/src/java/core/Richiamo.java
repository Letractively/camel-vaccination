/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.mysql.jdbc.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            if(request.getParameter("seconds")==null){
                out.println("<form action=\"Richiamo\" method=\"POST\">");
                out.println("<label for=\"Vagginazioni effettuate prima di (giorni)\"></label><input type=\"text\" name=\"date\" value=\"\" />");
                out.println("<input type=\"submit\" name=\"Submit\" value=\"Cerca\" />");	
                out.println("</form>");
            }
            else{
                //RECUPERARE ID MEDICO DALLA SESSIONE
                String s = request.getParameter("seconds");
                ResultSet r = null; //FUZIONE RICERCA PAZIENTI PER DATA
                
                out.println("<TABLE>");
                out.println("<TR>");
                out.println("<TD>Paziente</TD>");
                out.println("<TD>Foto</TD>");
                out.println("<TD>Data di vaccinazione</TD>");
                out.println("</TR>");
                try {
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
                } catch (SQLException ex) {
                    //STAMPA CON LOG4J
                }
                out.println("</TABLE>");
                //PULSANTI DI STAMPA PDF
                
                //PULSANTE ANNULLA (CHIEDERE ABI PER JAVASCRIPT)
            }
        } finally {            
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
