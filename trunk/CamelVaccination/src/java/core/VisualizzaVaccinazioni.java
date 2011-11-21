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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        

        
        
        try {
            String checkboxname = "patients";//Assicurarsi che sia uguale anche in Conferma
            String arrayPatientsName = "retrivedPatiens"; //idem sopra
            
            String htmlPage="";
            String htmlIntro="<HTML><HEAD><title>Richiamo</title></HEAD><BODY>";
            String htmlOutro="</BODY></HTML>";
            
            htmlPage+=htmlIntro;
            
            String seconds = "";
            
            if(request.getParameter("date")!=null) //rileva se è già stata effettuata una ricerca
                seconds = request.getParameter("date");
            
            //Form di ricerca vaccinazioni
            htmlPage+="<form action=\"?action=list&\" method=\"GET\">\n";
            htmlPage+="<label for=\"date\">Vaccinazioni effettuate prima di (secondi)</label>"
                    + "<input type=\"text\" id=\"date\" name=\"date\" value=\""+seconds+"\" />\n";
            htmlPage+="<input type=\"submit\" name=\"Submit\" value=\"Cerca\" />\n";
            htmlPage+="</form>\n";
            
            //Stampa risultato ricerca
            if(request.getParameter("date")!=null){
                dbManager db = new dbManager();
                ResultSet r = db.getPreviousVaccinationsPatients(new Integer(seconds));
                db.releaseConnection();
                
                //Prima riga della tabella
                htmlPage+="<TABLE>\n";
                htmlPage+="<TR>\n";
                htmlPage+="<TD>ID</TD>\n";
                htmlPage+="<TD>Username</TD>\n";
                htmlPage+="<TD>Paziente</TD>\n";
                htmlPage+="<TD>M/F</TD>\n";
                htmlPage+="<TD>Data di vaccinazione</TD>\n";
                htmlPage+="<TD>Foto</TD>\n";
                htmlPage+="<TD>Medico</TD>\n";
                htmlPage+="</TR>\n";
                
                try {
                    
                    if(r.first()){
                        while (!r.isAfterLast()) {
                            
                            Paziente p = new Paziente(r);
                            htmlPage+="<TR>\n";
                            htmlPage+="<TD>"+p.getId()+"</TD>\n";
                            htmlPage+="<TD>"+p.getUsername()+"</TD>\n";
                            htmlPage+="<TD><a href=\"Profilo?id="+p.getId()+"\">"+p.getName()+" "+p.getSurname()+"</a></TD>\n";
                            htmlPage+="<TD>"+p.getGender()+"</TD>\n";
                            htmlPage+="<TD>"+p.getVaccination_date()+"</TD>\n";
                            htmlPage+="<TD>"+"<img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>\n";
                            htmlPage+="<TD>"+p.getDoctor_id()+"</TD>\n";
                            htmlPage+="</TR>\n";
                            r.next();
                        }
                    }
                } catch (SQLException ex) {
                    htmlPage+="</BODY></HTML>\n";
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }
                htmlPage+="</TABLE>\n";
                htmlPage+="<a href=\"Welcome\" title=\"Home\">Torna alla Home</a>\n";
            }
            
            htmlPage+=htmlOutro;
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
