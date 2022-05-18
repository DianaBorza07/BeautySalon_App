package ro.sd.a2.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFGenerator implements TextFormatter{
    @Override
    public void formatText(String format) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("AppointmentConfirmation.pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            String[] split = format.split("#");
            if(split.length>4) {
                String user = split[0];
                String date = split[1];
                String salon = split[2];
                String service = split[3];
                String price = split[4];
                System.out.println(user+" "+date+" "+salon+" "+service+" "+price+"----------------------------------");
                Chunk chunk = new Chunk(getContent(user,date,salon,service,price), font);
                Paragraph paragraph = new Paragraph(getContent(user,date,salon,service,price),font);
                document.add(paragraph);
                Path path = Paths.get("src/main/resources/static/appointment.png");
                Image img = Image.getInstance(path.toAbsolutePath().toString());
                document.add(img);
                document.close();
            }
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContent(String name,String date,String salon, String service, String price){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thank you for choosing Randevular! ");
        stringBuilder.append("\n");
        stringBuilder.append("This is your appointment confirmation:\n");
        stringBuilder.append("Name: "+ name+"\n");
        stringBuilder.append("Appointment date: "+ date +"\n");
        stringBuilder.append("Salon: "+salon+"\n");
        stringBuilder.append("Service: "+service+"\n");
        stringBuilder.append("Price: "+price+"\n");
        return stringBuilder.toString();
    }
}
