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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logManagement.Log4k;
import userManagement.*;


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
        
        String checkboxname = "patients";//Assicurarsi che sia uguale anche in Conferma
        String arrayPatientsName = "retrivedPatiens"; //idem sopra
        
        out.println("<HTML><HEAD><title>Richiamo</title></HEAD><BODY>");
        
        
        try {
            LinkedList <Paziente> arrayPazienti = new LinkedList();
            
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
                
                out.println("<form action=\"Conferma\" method=\"POST\">");
                
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
                out.println("<TD>Seleziona</TD>");
                out.println("</TR>");
                
                try {
                    
                    if(r.first()){
                        while (!r.isAfterLast()) {
                            
                            Paziente p = new Paziente(r);
                            
                            if (!arrayPazienti.add(p))
                                Log4k.warn(Richiamo.class.getName(), "il paziente non e` stato inserito nel'array");
                            
                            out.println("<TR>");
                            out.println("<TD>"+p.getId()+"</TD>");
                            out.println("<TD>"+p.getUsername()+"</TD>");
                            out.println("<TD>"+p.getName()+" "+p.getSurname()+"</TD>");
                            out.println("<TD>"+p.getGender()+"</TD>");
                            out.println("<TD>"+p.getVaccination_date()+"</TD>");
                            out.println("<TD>"+"<img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>");
                            out.println("<TD>"+p.getDoctor_id()+"</TD>");
                            out.println("<TD><input type=\"checkbox\" name=\""+checkboxname+"\" value=\""+p.getId()+"\" /></TD>");
                            out.println("</TR>");
                            r.next();
                        }
                    }
                } catch (SQLException ex) {
                    out.println("</BODY></HTML>");
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }
                out.println("</TABLE>");
                
                
                //Salvo la lista di pazienti nella sessione
                HttpSession session = request.getSession();
                session.setAttribute(arrayPatientsName, arrayPazienti);//Assicurarsi che il nome sia uguale anceh in Conferma                
                
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
