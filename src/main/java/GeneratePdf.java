import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratePdf {
    private DataManger dataManger;
    ImageCreator imageCreator;
    TableGenerator tableGenerator;


    public GeneratePdf(){
        dataManger = new DataManger();
        this.imageCreator = new ImageCreator();
        this.tableGenerator = new TableGenerator();
    }
    public void generatePdf(Date from, Date to, String exercise){

        Document document = new Document();

        try{
            PdfWriter.getInstance(document, new FileOutputStream(exercise + ".pdf"));
        }catch(IOException e){
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk headline = new Chunk(exercise, font);
        Paragraph paragraph = new Paragraph();
        paragraph.add(headline);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Paragraph paragraphDate = new Paragraph();
        String textDates = format.format(from)+ " - "+  format.format(to);
        paragraphDate.add(new Chunk(textDates, font));
        paragraphDate.setAlignment(Element.ALIGN_CENTER);


        try{
            document.add(paragraph);
            document.add(paragraphDate);
        }catch(DocumentException e){
            e.printStackTrace();
        }
        document.setMargins(10,10,10,10);


        try{
            BufferedImage bufferedImage =
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.EFFECTIVE_GRAPH);
            Image imageEffective = Image.getInstance(bufferedImage, null);
            imageEffective.scalePercent(50);
            document.add(imageEffective);

            bufferedImage=
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.WEIGHT_GRAPH);
            Image imageWeight = Image.getInstance(bufferedImage, null);
            imageWeight.scalePercent(25);
            imageWeight.setAbsolutePosition(40f, document.getPageSize().getHeight()*0.46f);
            document.add(imageWeight);

            bufferedImage =
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.REPS_GRAPH);
            Image imageReps = Image.getInstance(bufferedImage, null);
            imageReps.scalePercent(25);
            imageReps.setAbsolutePosition(document.getPageSize().getWidth()/2,
                    document.getPageSize().getHeight()*0.46f);
            document.add(imageReps);

        }catch(IOException e){
            e.printStackTrace();
        }catch(BadElementException e){
            e.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }

        //spacer
        for(int i = 0; i<8; i++){
            try{
                document.add( Chunk.NEWLINE );
            }catch(DocumentException ex){
                ex.printStackTrace();
            }
        }
        font = FontFactory.getFont(FontFactory.COURIER, 10,Font.BOLD ,BaseColor.BLACK);
        Paragraph paragraphFrequency = new Paragraph();
        DecimalFormat f = new DecimalFormat("#0.00");
        String frequency = "Frequency: " + String.valueOf(f.format
                (this.dataManger.getFrequencyPerWeek(from, to, exercise))) + " workouts per week";
        paragraphFrequency.add(new Chunk(frequency, font));
        paragraphFrequency.setAlignment(Element.ALIGN_CENTER);

        try{
            document.add(paragraphFrequency);
        }catch(DocumentException e){
            e.printStackTrace();
        }

        //spacer
        for(int i = 0; i<1; i++){
            try{
                document.add( Chunk.NEWLINE );
            }catch(DocumentException ex){
                ex.printStackTrace();
            }
        }

        PdfPTable table = tableGenerator.generateTable(from, to, exercise);


        try{
            document.add(table);
        }catch(DocumentException e){
            e.printStackTrace();
        }

        document.close();
    }
}