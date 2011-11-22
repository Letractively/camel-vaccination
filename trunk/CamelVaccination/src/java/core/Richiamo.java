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
import userManagement.*;


/**
 *
 * @author administrator
 */
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
            String checkboxname = "patients";//Assicurarsi che sia uguale anche in Conferma
            String arrayPatientsName = "retrivedPatiens"; //idem sopra
        
            String htmlPage = "";
            String title = "Richiamo";
            String htmlIntro = "<HTML><HEAD>"
                        + "<title>" + title + "</title>"
                        + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                        + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            htmlPage+=htmlIntro;
        
            LinkedList <Paziente> arrayPazienti = new LinkedList();
            
            String seconds = "";
            
            if(request.getParameter("date")!=null) //rileva se è già stata effettuata una ricerca
                seconds = request.getParameter("date");
            
            //Form di ricerca vaccinazioni
            htmlPage += "<div class=\"container\">";
            htmlPage += "<div class=\"header\">";
            htmlPage+="<form action=\"?action=list&\" method=\"GET\">\n";
            htmlPage+="<label for=\"date\" class=\"searchBar\">Vaccinazioni effettuate prima di (secondi)</label>";
            htmlPage+="<p class=\"searchBar\"><input type=\"text\" id=\"date\" name=\"date\" value=\""+seconds+"\" />\n";
            htmlPage+="<input class=\"submit\" type=\"submit\" name=\"Submit\" value=\"Cerca\" /></p>\n";
            htmlPage+="</form>\n";
            htmlPage+="<p class=\"headerInfo\"><a href=\"" + Macro.BASE + "\" title=\"Home\">Torna alla Home</a></p>\n";
            htmlPage += "</div>";  //div header end
            
            //Stampa risultato ricerca
            if(request.getParameter("date")!=null){
                dbManager db = new dbManager();
                ResultSet r = db.getPreviousVaccinationsPatients(new Integer(seconds));
                db.releaseConnection();
                htmlPage += "<div class=\"content\">";
                htmlPage+="<form action=\"Conferma\" method=\"POST\">\n";
                htmlPage+="<p class=\"submit\"><input type=\"submit\" name=\"Conferma\" value=\"Conferma\" /></p>\n";
                
                //Prima riga della tabella
                htmlPage+="<TABLE>\n";
                htmlPage+="<tr>\n";
                htmlPage+="<th></th>\n";
                htmlPage+="<th>Nome</th>\n";
                htmlPage+="<th>Cognome</th>\n";
                htmlPage+="<th>M/F</th>\n";
                htmlPage+="<th>Data di vaccinazione</th>\n";
                htmlPage+="<th>Medico</th>\n";
                htmlPage+="<th>Seleziona</th>\n";
                htmlPage+="</tr>\n";
                
                try {
                    
                    if(r.first()){
                        while (!r.isAfterLast()) {
                            
                            Paziente p = new Paziente(r);
                            
                            if (!arrayPazienti.add(p))
                                Log4k.warn(Richiamo.class.getName(), "il paziente non e` stato inserito nel'array");
                            
                            htmlPage+="<TR>\n";
                            htmlPage+="<TD><img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>\n";
                            htmlPage+="<TD>"+p.getName()+"</TD>\n";
                            htmlPage+="<TD>"+p.getSurname()+"</TD\n>";
                            htmlPage+="<TD>"+p.getGender()+"</TD>\n";
                            htmlPage+="<TD>"+p.getVaccination_date()+"</TD>\n";
                            htmlPage+="<TD>"+p.getDoctor_id()+"</TD>\n";
                            htmlPage+="<TD><input type=\"checkbox\" name=\""+checkboxname+"\" value=\""+p.getId()+"\" /></TD>\n";
                            htmlPage+="</TR>\n";
                            r.next();
                        }
                    }
                } catch (SQLException ex) {
                    htmlPage+="</div></div></BODY></HTML>\n";
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }
                htmlPage+="</TABLE>\n";
                htmlPage+="<p class=\"submit\"><input type=\"submit\" name=\"Conferma\" value=\"Conferma\" /></p>\n";
                htmlPage+="</form>\n";
                htmlPage+="<p class=\"headerInfo\"><a href=\"" + Macro.BASE + "\" title=\"Home\">Torna alla Home</a></p>\n";
                htmlPage += "</div>"; //div content end
                
                //Salvo la lista di pazienti nella sessione
                HttpSession session = request.getSession();
                session.setAttribute(arrayPatientsName, arrayPazienti);//Assicurarsi che il nome sia uguale anceh in Conferma                
            }
            htmlPage += "</div>"; //div container end
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
