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
            if(request.getParameter("id")!=null){
                dbManager db = new dbManager();
                ResultSet r1 = db.getPatient(new Integer(request.getParameter("id")));
                ResultSet r2 = db.getPatientVaccinations(new Integer(request.getParameter("id")));
                db.releaseConnection();
                
                try {
                    String htmlPage = "";
                    String title = "Profilo";
                    String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "</HEAD><BODY>";
                    String htmlOutro = "</BODY></HTML>";
                    
                    htmlPage+=htmlIntro;

                    if(r1!=null && r2!=null){
                        if(r1.first()){
                            User p = new User(r1, false);
                            htmlPage+="<TABLE>\n";
                            htmlPage+="<TR>\n";
                            htmlPage+="<TD>"+p.getId()+"</TD>\n";
                            htmlPage+="<TD>"+p.getUsername()+"</TD>\n";
                            htmlPage+="<TD>"+p.getGender()+"</TD>";
                            htmlPage+="<TD>"+"<img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>\n";
                            htmlPage+="</TR>\n";
                            htmlPage+="</TABLE>\n";
                        }
                        
                        if(r2.first()){
                            while (!r2.isAfterLast()) {
                                htmlPage+="<TABLE>\n";
                                htmlPage+="<TR>\n";
                                htmlPage+="<TD>"+r2.getString("vaccination_date")+"</TD>\n";
                                htmlPage+="<TD>"+r2.getString("doctor_id")+"</TD>\n";
                                htmlPage+="</TR>\n";
                                htmlPage+="</TABLE>\n";
                                r2.next();
                            }
                        }
                    }
                    else{
                        Log4k.warn(Profilo.class.getName(), "r1 = "+r1+"r2 = "+r2);
                    }
                    htmlPage+="<a href=\"" + Macro.BASE + "\" title=\"Home\">Torna alla Home</a>\n";
                    htmlPage+=htmlOutro;
                    out.println(htmlPage);
                } catch (SQLException ex) {
                    Log4k.error(Richiamo.class.getName(), ex.getMessage());
                }}
            else{
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
