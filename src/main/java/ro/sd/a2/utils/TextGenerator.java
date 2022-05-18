package ro.sd.a2.utils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextGenerator implements TextFormatter{
    @Override
    public void formatText(String format) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("appointmentConfirmation.txt");
            String[] split = format.split("#");
            if (split.length > 4) {
                String user = split[0];
                String date = split[1];
                String salon = split[2];
                String service = split[3];
                String price = split[4];

                myWriter.write(getContent(user, date, salon, service, price));
                myWriter.close();

        }} catch(IOException e){
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
