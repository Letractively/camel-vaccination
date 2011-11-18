/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.mysql.jdbc.ResultSet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
            out.println("<HTML><HEAD><title>Welcome</title></HEAD><BODY>");
            HttpSession session = request.getSession();  
            
            /*INIZIO RECUPERO COOKIE*/
            String cookieName = "vaccination";
            
            Cookie[] cookieArray = request.getCookies(); 
            
            int i = 0;
            while(!cookieArray[i].getName().equals(cookieName)) i++;
                
            if(i == cookieArray.length) 
                Log4k.warn(Welcome.class.getName(),"Nessun cookie trovato");
            
            else{
                Cookie c = cookieArray[i];
                String message = c.getValue();
                /*Stampa del valore del cookie da qualche parte*/
                
            }
            /*FINE RECUPERO COOKIE*/
            
            /*INIZIO LINK AL PDF*/
            String fileName = request.getSession().getId();
            String path = "";
            File pdf = new File(path+fileName+".pdf");
            if (pdf.exists())
                /*Stampa link al pdf*/;
            else /*Amen*/;
            /*FINE LINK AL PDF*/
            
            User loggedUser = (User) session.getAttribute("loggedUser");
            if (loggedUser == null){
                Log4k.warn(Welcome.class.getName(), "un utente non loggato non dovrebbe essere qui");
                response.sendRedirect("login.jsp");
            } else {
                if(loggedUser.getIsDoctor()){                    
                    out.println("<a href=\"doctorFiles/Richiamo\"> Procedura richiamo paziente </a><BR>");
                    out.println("<a href=\"doctorFiles/Visualizza\"> Visualizza pazienti richiamati </a><BR>");                    
                } else {
                    out.println("<a href=\"patientFiles/VaccinazioniPaziente\"> Visualizza dettagli vaccinazioni </a><BR>");
                }
                
                out.println("<a href=\"Logout\"> Logout </a><BR>");
            }
            out.println("</BODY></HTML>");
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
