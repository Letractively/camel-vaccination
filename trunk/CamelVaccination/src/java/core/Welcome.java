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
            String htmlPage = "";
            String title = "Welcome";
            String htmlIntro = "<HTML><HEAD><title>" + title + "</title></HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            
            htmlPage += htmlIntro;
            
            HttpSession session = request.getSession();
            User loggedUser = (User) session.getAttribute("loggedUser");
            
            if (loggedUser == null){
                Log4k.warn(Welcome.class.getName(), "un utente non loggato non dovrebbe essere qui");
                response.sendRedirect("login.jsp");
            } else {
                /*INIZIO RECUPERO COOKIE*/                
                String cookieName = loggedUser.getUsername();
                Cookie cookie = null;
                Cookie[] cookieArray = request.getCookies();
                
                if (cookieArray!=null){
                    
                    for(int i=0; i<cookieArray.length; i++) {
                        
                        if (cookieArray[i].getName().equals(cookieName)) {
                            cookie = cookieArray[i];
                            break;
                        }
                    }
                    if(cookie==null){
                        Log4k.debug(Welcome.class.getName(),"Nessun cookie trovato");
                    }
                    
                    else{
                        String message = cookie.getValue();
                        htmlPage += "Bentornato! Il tuo ultimo login risale al: " + message + "<br>";
                    }
                }
                else {
                    Log4k.warn(cookieName, cookieName);
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
                
                
                if(loggedUser.getIsDoctor()){
                    htmlPage += "<a href=\"doctorFiles/Richiamo\"> Procedura richiamo paziente </a><BR>";
                    htmlPage += "<a href=\"doctorFiles/Visualizza\"> Visualizza pazienti richiamati </a><BR>";
                } else {
                    htmlPage += "<a href=\"patientFiles/VaccinazioniPaziente\"> Visualizza dettagli vaccinazioni </a><BR>";
                }
                
                htmlPage += "<a href=\"Logout\"> Logout </a><BR>";
            }
            
            htmlPage += htmlOutro;
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
