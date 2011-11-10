/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.mysql.jdbc.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 *
 * @author administrator
 */
public class Welcome extends HttpServlet {

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
            String kind = request.getParameter("kind");
            if(kind.equals("medico")){
                
                out.println("<a href=\"richiamo/\" >Procedura richiamo paziente</a><BR>");
                out.println("<a href=\"visualizza/\" >Visualizza pazienti richiamati</a><BR>");
                
            }
            
            else if(kind.equals("paziente")){
                //RECUPERARE I DATI DALLA SESSIONE E NON VIA POST
                
                out.println("Benvenuto "/*+username*/+" queste sono le vaccinazioni che hai effettuato.<BR>");
                out.print("<TABLE>");
                out.println("<TR>");
                out.println("<TD>Medico</TD>");
                out.println("<TD>Data di vaccinazione</TD>");
                out.println("</TR>");
                
                ResultSet res = null; //DA IMPLEMENTARE
                try{
                     while(res.next()){
                        String med = ""+res.getString("name")+" "+res.getString("surname");
                        String date = res.getString("vaccination_date");
                        
                        
                        out.println("<TR>");
                        out.println("<TD>"+med+"</TD>");
                        out.println("<TD>"+date+"</TD>");
                        out.println("</TR>");
                        
                    }
                }
                catch(Exception e){
                    //SCRIVI IN LOG4J
                }
                
                out.print("</TABLE>");
            }
            else /*SCRIVI IN LOG4J*/;
            
            out.println("<a href=\"logout/\" >Logout</a><BR>");
            
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
