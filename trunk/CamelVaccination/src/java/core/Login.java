package core;

import com.mysql.jdbc.ResultSet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import dbManagement.*;
import logManagement.*;
import java.io.PrintWriter;
import java.util.Calendar;
import userManagement.User;

/**
 *
 * @author administrator
 */
public class Login extends HttpServlet {
    private final int secsBeforeRefresh = 3;
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
            String title = "Login";
            String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                    + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            
            htmlPage += htmlIntro;
            
            String usr = request.getParameter("user");
            String psw = request.getParameter("password");
            boolean isDoc = request.getParameter("type").equals("medico");
            
            dbManager dbM = new dbManager();
            ResultSet res = dbM.userMatches(usr, psw, isDoc);
            dbM.releaseConnection();
            
            if (res.first()){
                HttpSession session = request.getSession();
                User loggedUser = (User) session.getAttribute("loggedUser");
                if (loggedUser == null){
                    loggedUser = new User(res, isDoc);
                    session.setAttribute("loggedUser", loggedUser);
                    
                    htmlPage += "<div class=\"jump\">";
                    htmlPage += "<p class=\"jump\">Benvenuto " + ((loggedUser.getIsDoctor()) ? "Dr. " + loggedUser.getSurname() : loggedUser.getName()) + "<br/>";
                    htmlPage += "Verrai a breve reindirizzato alla tua pagina personale</p>";
                    htmlPage += "<p class=\"jump\"><a href=\"" + Macro.BASE + "logged/Welcome\">Oppure clicca qui per continuare...</a></p>";
                    htmlPage += "</div>";
                    
                    /*INIZIO SETTAGGIO COOKIE*/
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
                             Calendar cal = Calendar.getInstance();
                             String cookieValue = cal.getTime().toString();
                             int cookieExpire = 3600*24*7;//una settimana
                             loggedUser.setLastLogin(cookieValue);
                             cookie = new Cookie(cookieName, cookieValue);
                             cookie.setMaxAge(cookieExpire);

                             response.addCookie(cookie);
                        }

                        else{
                            String message = cookie.getValue();
                            loggedUser.setLastLogin(message);
                            Calendar cal = Calendar.getInstance();
                            String cookieValue = cal.getTime().toString();
                            cookie.setValue(cookieValue);
                            
                            response.addCookie(cookie);
                        }
                    } else {
                        Log4k.warn(cookieName, cookieName);
                    }
                        /*FINE SETTAGGIO COOKIE*/

                        htmlPage += htmlOutro;
                        out.print(htmlPage);
                        response.setHeader("Refresh", secsBeforeRefresh + "; url=" + Macro.BASE + "logged/Welcome");

                    } else {
                        Log4k.warn(Login.class.getName(), "un utente gia' loggato non dovrebbe essere qui\n");
                    }
            }
            else {
                htmlPage += "<div class=\"jump\">";
                htmlPage += "<p class=\"jump\">Hai inserito un nome utente o una password sbagliati<br>";
                htmlPage += "Verrai a breve reindirizzato alla tua pagina personale</p>";
                htmlPage += "<p class=\"jump\"><a href=\"" + Macro.BASE + "logged/Welcome\">Oppure clicca qui per continuare...</a></p>";
                htmlPage += "</div>";                 
                htmlPage += htmlOutro;
                
                out.print(htmlPage);
                response.setHeader("Refresh", secsBeforeRefresh + "; url=" + Macro.BASE + "logged/Welcome");
            }
        } catch (Exception ex) {
            out.print("Error");
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
