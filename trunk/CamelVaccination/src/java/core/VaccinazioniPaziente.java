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
import javax.servlet.http.HttpSession;
import logManagement.Log4k;
import userManagement.User;

/**
 *
 * @author Lorenzo
 */
public class VaccinazioniPaziente extends HttpServlet {
    
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
            String title = "Welcome";
            String htmlPage = "";
            String htmlIntro="<HTML><HEAD><title>"+title+"</title></HEAD><BODY>\n";
            String htmlOutro="</BODY></HTML>";
            htmlPage+=htmlIntro;
            
            HttpSession session = request.getSession();
            
            User loggedUser = (User) session.getAttribute("loggedUser");
            if (loggedUser == null){
                Log4k.warn(VaccinazioniPaziente.class.getName(), "un utente non loggato non dovrebbe essere qui");
                response.sendRedirect("");
            }
            else {
                if(loggedUser.getIsDoctor()){
                    Log4k.warn(VaccinazioniPaziente.class.getName(), "un dottore non dovrebbe essere qui");
                    response.sendRedirect("");
                } else {
                    htmlPage+="Benvenuto "/*+username*/+" queste sono le vaccinazioni che hai effettuato.<BR>";
                    htmlPage+="<TABLE>";
                    htmlPage+="<TR>";
                    htmlPage+="<TD>Medico</TD>";
                    htmlPage+="<TD>Data di vaccinazione</TD>";
                    htmlPage+="</TR>";
                    
                    
                    User paziente = loggedUser;
                    dbManager db = new dbManager();
                    ResultSet res = db.getPatientVaccinations(paziente.getId());
                    db.releaseConnection();
                    try{
                        if(res.first()){
                            while(!res.isAfterLast()){
                                int medId = res.getInt("doctor_id");
                                String date = res.getString("vaccination_date");
                                
                                dbManager dbMed = new dbManager();
                                ResultSet doctor = dbMed.getDoctor(medId);
                                dbMed.releaseConnection();
                                
                                String doctorName="";
                                if(doctor.first()){
                                    while(!doctor.isAfterLast()){
                                        doctorName = doctor.getString("name")+" "+doctor.getString("surname");
                                        doctor.next();
                                    }
                                }
                                
                                htmlPage+="<TR>\n";
                                htmlPage+="<TD>"+doctorName+"</TD>\n";
                                htmlPage+="<TD>"+date+"</TD>\n";
                                htmlPage+="</TR>\n";
                                res.next();
                            }
                        }
                    }
                    catch(SQLException e){
                        Log4k.error(Welcome.class.getName(), e.getMessage());
                    }
                    
                    htmlPage+="</TABLE>\n";
                    
                }
                htmlPage+="<a href=\"../Welcome\" title=\"Home\">Torna alla Home</a>\n";
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
