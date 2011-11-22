package core;

import dbManagement.NotInDBException;
import dbManagement.YetRegisteredException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import dbManagement.dbManager;
import logManagement.Log4k;

/**
 *
 * @author administrator
 */
public class Registrati extends HttpServlet {
    
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
            String title = "Registrazione";
            String htmlIntro = "<HTML><HEAD><title>" + title + "</title></HEAD><BODY>";
            
            htmlPage += htmlIntro;
            
            String usr = request.getParameter("user");
            String psw1 = request.getParameter("password");
            String psw2 = request.getParameter("confirm_password");
            String userCaptchaResponse = request.getParameter("jcaptcha");
            boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
            out.println(""
                    + "captcha = "+ userCaptchaResponse+"<BR>"
                    +"username = "+ usr+"<BR>"
                    +"password: "+psw1+" "+psw2+"<BR>");
            if (captchaPassed) {
                if(psw1.equals(psw2)){
                    
                    try {
                        dbManager db = new dbManager();
                        db.doRegister(usr, psw1);
                        db.releaseConnection();   
                        htmlPage += "Hai completato la registrazione<BR>";
                        htmlPage += "<a href=\"" + Macro.BASE + "login.jsp\" title=\"login\">Effettua il login</a>";                     
                    } catch (NotInDBException ex) {
                        htmlPage+="Username errato!<BR>\n";
                        htmlPage+="<a href=\"registrati.jsp\" title=\"Registrati\">Torna alla registrazione</a>";
                        Log4k.debug(Registrati.class.getName(), ex.getMessage());
                    } catch (YetRegisteredException ex) {
                        htmlPage+="Il tuo nome utente risulta già registrato!<BR>\n";
                        htmlPage+="<a href=\"registrati.jsp\" title=\"Registrati\">Torna alla registrazione</a>";
                        Log4k.debug(Registrati.class.getName(), ex.getMessage());
                    }
                }
                else{
                    htmlPage+="Le password che hai inserito non corrispondono!<BR>\n";
                    htmlPage+="<a href=\"registrati.jsp\" title=\"Registrati\">Torna alla registrazione</a>";
                }    
                    
                
            } else {
                htmlPage += "Captcha errato<BR>\n";
                htmlPage+="<a href=\"registrati.jsp\" title=\"Registrati\">Torna alla registrazione</a>";
                // return error to user
            }
            
            String htmlOutro = "</BODY></HTML>";
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
