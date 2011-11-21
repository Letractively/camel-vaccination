package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logManagement.Log4k;
import userManagement.Paziente;
import userManagement.User;

/**
 *
 * @author administrator
 */
@WebServlet(name = "Conferma", urlPatterns = {"/Conferma"})
public class Conferma extends HttpServlet {
    
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
            String title = "Conferma";
            String htmlIntro = "<HTML><HEAD>"
                    + "<title>" + title + "</title>"
                    + "<link rel=\"stylesheet\" type =\"text/css\" href=\"../style.css\" />"
                    + "</HEAD><BODY>";
            String htmlOutro = "</BODY></HTML>";
            htmlPage+=htmlIntro;
     
            String checkboxname = "patients";
            String arrayName = "retrivedPatiens"; //DA SETTARE IN BASE ALLA FUNZIONE PRECEDENTE
            int i = 0;
            
            LinkedList <Paziente> chosenPatients = new LinkedList();
            LinkedList <Paziente> allPatients =
                    (LinkedList <Paziente>) request.getSession().getAttribute(arrayName);//recupero i pazienti dalla sessione
            
            HttpSession session = request.getSession();
            
            User doctor = (User) request.getSession().getAttribute("loggedUser");//recupero il profilo del medico
            
            String[] patientsList = request.getParameterValues(checkboxname);//recupero gli id passati per POST
            
            htmlPage += "<div class=\"container\">";
            htmlPage += "<div class=\"header\">";
            htmlPage+="<p class=\"headerInfo\"><a href=\"/\" title=\"Home\">Torna alla Home</a></p>\n";
            htmlPage += "</div>";  //div header end
            
            /*Salvo i pazienti selezionati, dato che dovrei fare delle assunzioni su
             * come vengono estratti e/o trasmetti i nomi dei pazienti faccio una doppia scansione
             * utilizzando l'id paziente per identificarli
             */
            
            htmlPage += "<div class=\"content\">";
            htmlPage+="<form action=\"EseguiVaccinazioni\" method=\"POST\">\n";
            htmlPage+="<p class=\"submit\"><input type=\"submit\" name=\"Conferma\" value=\"Conferma\" /></p>\n";
            htmlPage+="<TABLE>\n";
            htmlPage+="<TR>\n";
            htmlPage+="<th>ID</th>\n";
            htmlPage+="<th>Username</th>\n";
            htmlPage+="<th>Paziente</th>\n";
            htmlPage+="<th>M/F</th>\n";
            htmlPage+="<th>Data di vaccinazione</th>\n";
            htmlPage+="<th>Foto</th>\n";
            htmlPage+="<th>Medico</th>\n";
            htmlPage+="</TR>\n";
            
            while(i < patientsList.length){
                int k = 0;
                while(k<allPatients.size()){
                    Paziente p = allPatients.get(k);
                    String id = p.getId().toString();
                    if(id.equals(patientsList[i]))
                        if (chosenPatients.add(p)){//se l'id nella lista è uguale a quello recuperato dal post lo aggiungo e controllo il buon esito
                            htmlPage+="<TR>\n";
                            htmlPage+="<TD>"+p.getId()+"</TD>\n";
                            htmlPage+="<TD>"+p.getUsername()+"</TD>\n";
                            htmlPage+="<TD>"+p.getName()+" "+p.getSurname()+"</TD>\n";
                            htmlPage+="<TD>"+p.getGender()+"</TD>\n";
                            htmlPage+="<TD>"+p.getVaccination_date()+"</TD>\n";
                            htmlPage+="<TD>"+"<img src=\"photo/"+p.getPicture()+"\" height=\"50\" width=\"50\" alt=\"Foto Paziente\" /></TD>\n";
                            htmlPage+="<TD>"+p.getDoctor_id()+"</TD>\n";
                            htmlPage+="</TR>\n";
                        } else
                            Log4k.warn(Conferma.class.getName(),
                                    "Il paziente selezionato non è stato aggiunto alla lista");
                    k++;
                }
                i++;
            }
            
            session.setAttribute("chosenPatients", chosenPatients);
            
            htmlPage+="</TABLE>\n";
            htmlPage+="<p class=\"submit\"><input type=\"submit\" name=\"Conferma\" value=\"Conferma\" /></p>\n";
            htmlPage+="</form>\n";
            htmlPage += "</div>"; //div content end
            htmlPage += "</div>"; //div container end
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
