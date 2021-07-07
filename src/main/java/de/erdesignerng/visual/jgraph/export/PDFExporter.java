package de.erdesignerng.visual.jgraph.export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import de.erdesignerng.visual.jgraph.ERDesignerGraph;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Herlana
 */
public class PDFExporter implements Exporter {

    @Override
    public String getFileExtension() {
        return ".pdf";
    }

    @Override
    public void fullExportToStream(ERDesignerGraph aGraph, OutputStream aStream) throws IOException, MalformedURLException {
        try {
            Document document = new Document(new Rectangle(400, 300));
            PdfWriter writer;
            
            writer = PdfWriter.getInstance(document, aStream);
            
            
            document.open();
            Color theBackgroundColor = aGraph.getBackground();
            BufferedImage img = aGraph.getImage(theBackgroundColor, 10);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            
            Image iTextImage = Image.getInstance(baos.toByteArray());
            

             iTextImage.setBorder(30);
            iTextImage.scaleToFit(330, 240);
//            iTextImage.scaleAbsolute(400, 300);
            iTextImage.setAbsolutePosition(0, 100);
            PdfImage stream = new PdfImage(iTextImage, "", null);
            stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
            PdfIndirectObject ref = writer.addToBody(stream);
            iTextImage.setDirectReference(ref.getIndirectReference());
            document.add(iTextImage);
            document.close();
        } catch (BadElementException ex) {
            Logger.getLogger(PDFExporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPdfFormatException ex) {
            Logger.getLogger(PDFExporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void exportToStream(Component aComponent, OutputStream aStream) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
