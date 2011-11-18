/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logManagement.Log4k;
import pdfManagement.pdfCreator;
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
            String checkboxname = "patients";
            String arrayName = "retrivedPatiens"; //DA SETTARE IN BASE ALLA FUNZIONE PRECEDENTE
            int i = 0;
            
            LinkedList <Paziente> choosedPatients = new LinkedList();
            LinkedList <Paziente> allPatients = 
                    (LinkedList <Paziente>) request.getSession().getAttribute(arrayName);//recupero i pazienti dalla sessione

            String nomeFile = request.getSession().getId();//il nome del pd sarà <IDsessione>.pdf
            User doctor = (User) request.getSession().getAttribute("loggedUser");//recupero il profilo del medico
            
            String[] patientsList = request.getParameterValues(checkboxname);//recupero gli id passati per POST
            
            /*Salvo i pazienti selezionati, dato che dovrei fare delle assunzioni su 
             * come vengono estratti e/o trasmetti i nomi dei pazienti faccio una doppia scansione
             * utilizzando l'id paziente per identificarli
             */
            while(i < patientsList.length){
                int k = 0;
                while(k<allPatients.size()){
                    Paziente p = allPatients.get(k);
                    String id = p.getId().toString();
                   if(id.equals(patientsList[i]))
                       if (choosedPatients.add(p))//se l'id nella lista è uguale a quello recuperato dal post lo aggiungo e controllo il buon esito
                           Log4k.warn(Conferma.class.getName(), 
                                   "Il paziente selezionato non è stato aggiunto alla lista");
               }
            }
            
            //Passo l'array di pazienti selezionati alla stampante PDF
            String signature = doctor.getName()+" "+doctor.getSurname();
            pdfCreator.createLetters(nomeFile, choosedPatients, signature);
          
            
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
