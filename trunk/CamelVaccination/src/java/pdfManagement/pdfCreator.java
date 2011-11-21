/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfManagement;

/**
 *
 * @author administrator
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import logManagement.Log4k;
import userManagement.Paziente;

public class pdfCreator {
    
    private static final String font = FontFactory.HELVETICA;
    private static final float size = 14;
    private static final int align = Element.ALIGN_LEFT;
    
    private static String letter(String name, String surname, String doctorSign){
        return ""
                + "Gentile signor/a "+name+" "+surname+", \n"
                + "con la presente la informiamo che in data odierna dovrà presentarsi presso il nostro"
                + "centro medico per il richiamo della vaccinazione.\n\n"
                + "Distinti saluti\n"
                + "dr. "+doctorSign+"\n";
    }
    
    private static void createPage(Document doc, String text){
        
        Paragraph p = new Paragraph(text,FontFactory.getFont(font, size));
        p.setAlignment(align);
        try {
            if (!(doc.add(p) && doc.newPage()))
                Log4k.warn(pdfCreator.class.getName(), "Attenzione! il paragrafo non è stato aggiunto o non è stata creata la nuova pagina");
            
        } catch (DocumentException ex) {
            Log4k.error(pdfCreator.class.getName(), ex.getMessage());
        }
        
    }
    
    public static void createLetters(String documentName, LinkedList <Paziente> patients, String docSign){
        Document document = new Document();
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(documentName+".pdf"));
            document.open();
            
            while(patients.isEmpty()){
                Paziente p = patients.remove();
                String name = p.getName();
                String surname = p.getSurname();
                
                String l = letter(name, surname, docSign);
                createPage(document,l);
            }
        } catch (DocumentException ex) {
            Log4k.error(pdfCreator.class.getName(), ex.getMessage());
        }
        catch  (FileNotFoundException ex){
            Log4k.error(pdfCreator.class.getName(), ex.getMessage());
        }
        
        document.close();
    }
}