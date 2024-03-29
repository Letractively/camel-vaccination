package core;

import dbManagement.dbManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pdfManagement.*;
import userManagement.Paziente;
import userManagement.User;

/**
 *
 * @author alessandro
 */
public class EseguiVaccinazioni extends HttpServlet {

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
            String htmlCode="";
            String title = "Esegui Vaccinazioni";
            String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"" + Macro.BASE + "style.css\" />"
                    + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            htmlCode += htmlIntro;
            
            HttpSession session = request.getSession();
            String pdfName = session.getId()+".pdf";//il nome del pdf sarà <IDsessione>.pdf
            String realPath =getServletContext().getRealPath(File.separator+"logged"+File.separator+"doctorFiles"+File.separator+"PDFs"+File.separator+pdfName);
            String virtualPath = "PDFs/" + pdfName;
            User doctor = (User) request.getSession().getAttribute("loggedUser");//recupero il profilo del medico
                               
            LinkedList <Paziente> chosenPatients = (LinkedList<Paziente>)session.getAttribute("chosenPatients");
               
            for(Paziente p : chosenPatients){
                dbManager db = new dbManager();
                db.doVaccinate(doctor.getId(),p.getId());
                db.releaseConnection();
            }
            
            String date = (String)request.getSession().getAttribute("date");
            String signature = doctor.getName()+" "+doctor.getSurname();

            pdfCreator.createLetters(realPath, chosenPatients, signature, date);
            htmlCode += "<div class=\"container\">";
            htmlCode += "<div class=\"header\">";
            htmlCode += "<p class=\"headerInfo\"><a href=\"" + Macro.BASE + "\" target=\"_self\">Torna alla Home</a><BR>";
            htmlCode += "</div>";  //div header end
            htmlCode += "<div class=\"content\">";
            htmlCode += "<p id=\"pdfLink\">"; 
            htmlCode += "<img src=\"photo/pdf_ico.gif\" height=\"16px\" width=\"16px\"/>"
                    + "<a href="+virtualPath+" id=\"pdfLink\" target=\"_blank\"> Stampa Lettere per i Pazienti</a>";
            htmlCode += "</p>"; 
            htmlCode += "<p class=\"headerInfo\">I pazienti sono stati vaccinati.</p>";
            htmlCode += "</div>"; //div content end
            htmlCode += "</div>"; //div container end 
            htmlCode+=htmlOutro;
            out.println(htmlCode);
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
