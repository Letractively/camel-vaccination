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
import userManagement.User;

/**
 *
 * @author Lorenzo
 */
public class Profilo extends HttpServlet {
    
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
                    String title = "Profilo";
                    String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                    + "</HEAD><BODY>";
                String htmlOutro = "</BODY></HTML>";
            
            if(request.getParameter("id")!=null){
                dbManager db = new dbManager();
                ResultSet r1 = db.getPatient(new Integer(request.getParameter("id")));
                ResultSet r2 = db.getPatientVaccinations(new Integer(request.getParameter("id")));
                db.releaseConnection();
                
                try {
                   
                htmlPage+=htmlIntro;
                htmlPage += "<div class=\"container\">";
                htmlPage += "<div class=\"header\">";
                                 
                    if(r1!=null && r2!=null){
                        if(r1.first()){
                            User p = new User(r1, false);
                            htmlPage += "<img id=\"welcomeImage\" src=\"" + Macro.BASE + "photo/"+p.getPicture()+"\" />";
                            htmlPage += "<p class=\"headerInfo\">Paziente: "+p.getName()+" "+p.getSurname()+"</p>";
                            htmlPage += "<p class=\"headerInfo\">Data di nascita: "+p.getBirthdate()+"<br />";
                            htmlPage += "Sesso: "+p.getGender()+"</p>";
                            htmlPage += "<p class=\"headerInfo\"><a href=\"" + Macro.BASE + "\" title=\"Home\">Torna alla Home</a></p>\n";
                        }
                        
                    htmlPage += "</div>";  //div header end
                    htmlPage += "<div class=\"content\">";
                    htmlPage+="<TABLE>\n";
                                htmlPage+="<TR>\n";
                                htmlPage+="<th>Data di Vaccinazione</th>\n";
                                htmlPage+="<th>Dr.</th>\n";
                                htmlPage+="<th></th>\n";
                                htmlPage+="<th>ID</th>\n";
                                htmlPage+="</TR>\n";
                                
                        if(r2.first()){
                            while (!r2.isAfterLast()) {
                                htmlPage+="<TR>\n";
                                htmlPage+="<TD>"+r2.getString("vaccination_date")+"</TD>\n";
                                htmlPage+="<TD>"+r2.getString("surname")+"</TD>\n";
                                htmlPage+="<TD>"+r2.getString("name")+"</TD>\n";
                                htmlPage+="<TD>"+r2.getString("doctor_id")+"</TD>\n";
                                htmlPage+="</TR>\n";
                                r2.next();
                            }
                            } else {
                                htmlPage+="<TR>\n";
                                htmlPage+="<TD> Mai Vaccinato </TD>\n";
                                htmlPage+="<TD> - </TD>\n";
                                htmlPage+="<TD>  </TD>\n";
                                htmlPage+="<TD> - </TD>\n";
                                htmlPage+="</TR>\n";
                            }
                    }
                    else{
                        Log4k.warn(Profilo.class.getName(), "r1 = "+r1+"r2 = "+r2);
                    }
                    htmlPage += "</TABLE>\n";
                    htmlPage += "<p class=\"headerInfo\"><a href=\"" + Macro.BASE + "\" title=\"Home\">Torna alla Home</a></p>\n";
                    htmlPage += "</div>"; //div content end
                    htmlPage += "</div>"; //div container end
                    htmlPage+=htmlOutro;
                    out.println(htmlPage);
                } catch (SQLException ex) {
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }}
            else {
                htmlPage+=htmlIntro;
                htmlPage += "<div class=\"jump\">";
                htmlPage += "<p class=\"jump\">Errore nel processare la pagina<br>";
                htmlPage += "Verrai a breve reindirizzato alla tua pagina personale</p>";
                htmlPage += "<p class=\"jump\"><a href=\"" + Macro.BASE + "logged/Welcome\">Oppure clicca qui per continuare...</a></p>";
                htmlPage += "</div>";                 
                htmlPage += htmlOutro;
                
                out.print(htmlPage);
                response.setHeader("Refresh", "4; url=" + Macro.BASE + "logged/Welcome");

                Log4k.error(Richiamo.class.getName(),"errore su id");
            }
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
