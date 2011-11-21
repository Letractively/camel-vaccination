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
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logManagement.Log4k;
import userManagement.Paziente;

/**
 *
 * @author Lorenzo
 */
public class VisualizzaVaccinazioni extends HttpServlet {
    
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
        
        String checkboxname = "patients";//Assicurarsi che sia uguale anche in Conferma
        String arrayPatientsName = "retrivedPatiens"; //idem sopra
        
        out.println("<HTML><HEAD><title>Richiamo</title></HEAD><BODY>");
        
        
        try {
            
            String seconds = "";
            
            if(request.getParameter("date")!=null) //rileva se è già stata effettuata una ricerca
                seconds = request.getParameter("date");
            
            //Form di ricerca vaccinazioni
            out.println("<form action=\"?action=list&\" method=\"GET\">");
            out.println("<label for=\"date\">Vaccinazioni effettuate prima di (secondi)</label>"
                    + "<input type=\"text\" id=\"date\" name=\"date\" value=\""+seconds+"\" />");
            out.println("<input type=\"submit\" name=\"Submit\" value=\"Cerca\" />");
            out.println("</form>");
            
            //Stampa risultato ricerca
            if(request.getParameter("date")!=null){
                dbManager db = new dbManager();
                ResultSet r = db.getPreviousVaccinationsPatients(new Integer(seconds));
                db.releaseConnection();
                
                //Prima riga della tabella
                out.println("<TABLE>");
                out.println("<TR>");
                out.println("<TD>ID</TD>");
                out.println("<TD>Username</TD>");
                out.println("<TD>Paziente</TD>");
                out.println("<TD>M/F</TD>");
                out.println("<TD>Data di vaccinazione</TD>");
                out.println("<TD>Foto</TD>");
                out.println("<TD>Medico</TD>");
                out.println("</TR>");
                
                try {
                    
                    if(r.first()){
                        while (!r.isAfterLast()) {
                            
                            Paziente p = new Paziente(r);
                            out.println("<TR>");
                            out.println("<TD>"+p.getId()+"</TD>");
                            out.println("<TD>"+p.getUsername()+"</TD>");
                            out.println("<TD><a href=\"Profilo?id="+p.getId()+"\">"+p.getName()+" "+p.getSurname()+"</a></TD>");
                            out.println("<TD>"+p.getGender()+"</TD>");
                            out.println("<TD>"+p.getVaccination_date()+"</TD>");
                            out.println("<TD>"+"<img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>");
                            out.println("<TD>"+p.getDoctor_id()+"</TD>");
                            out.println("</TR>");
                            r.next();
                        }
                    }
                } catch (SQLException ex) {
                    out.println("</BODY></HTML>");
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }
                out.println("</TABLE>");
                out.println("<a href=\"Welcome\" title=\"Home\">Torna alla Home</a>");
            }
        } finally {
            out.println("</BODY></HTML>");
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
