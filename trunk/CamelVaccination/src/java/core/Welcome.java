package core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                    + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            htmlPage += htmlIntro;
            
            HttpSession session = request.getSession();
            User loggedUser = (User) session.getAttribute("loggedUser");
                            
                 /*INIZIO LINK AL PDF*/
                String pdfName = session.getId()+".pdf";
                String realPath = getServletContext().getRealPath(File.separator+"logged"+File.separator+"doctorFiles"+File.separator+"PDFs"+File.separator+pdfName);            
                String virtualPath ="doctorFiles/PDFs/"+pdfName;
                File pdf = new File(realPath);
                
                htmlPage += "<div class=\"container\">";
                htmlPage += "<div class=\"header\">";
                htmlPage += (loggedUser.getIsDoctor() ? "" : "<img id=\"welcomeImage\" src=\"" + Macro.BASE + "photo/"+loggedUser.getPicture()+"\" />");
                htmlPage += "<p class=\"headerInfo\">Sei loggato come: "+loggedUser.getName()+" "+loggedUser.getSurname()+"<br>";
                htmlPage += "Username: "+loggedUser.getUsername()+"</p>";
                htmlPage += "<p class=\"headerInfo\">Ultimo Login: "+loggedUser.getLastLogin()+"<br>";
                htmlPage += "<a href=\"" + Macro.BASE + "logged/Logout\"> Logout </a></p>";
                htmlPage += "</div>";  //div header end
                
                htmlPage += "<div class=\"content\">";

                if (pdf.exists()){
                    htmlPage += "<p id=\"pdfLink\">"; 
                    htmlPage += "<img src=\"photo/pdf_ico.gif\" height=\"16px\" width=\"16px\"/>"
                            + "<a href=" + Macro.BASE + "logged/"+virtualPath+" id=\"pdfLink\" target=\"_blank\"> Lettere Ultima Vaccinazione</a>";
                    htmlPage += "</p>"; 
                } 
                
                htmlPage += "<p class=\"headerInfo\">Menu</p>";
                htmlPage += "<p class=\"content\">";               
                
                if(loggedUser.getIsDoctor()){
                    htmlPage += "<a href=\"" + Macro.BASE + "logged/doctorFiles/Richiamo\"> Procedura richiamo paziente </a><BR>";
                    htmlPage += "<a href=\"" + Macro.BASE + "logged/doctorFiles/VisualizzaVaccinazioni\"> Visualizza pazienti richiamati </a><BR>";
                } else {
                    htmlPage += "<a href=\"" + Macro.BASE + "logged/patientFiles/VaccinazioniPaziente\"> Visualizza dettagli vaccinazioni </a><BR>";
                }
                
                htmlPage += "</p>";
                htmlPage += "</div>"; //div content end
                htmlPage += "</div>"; //div container end            
            
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
