import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Element;
import com.itextpdf.text.BadElementException;


import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeneratePdf {
    private DataManger dataManger;
    ImageCreator imageCreator;
    TableGenerator tableGenerator;


    public GeneratePdf() {
        dataManger = new DataManger();
        this.imageCreator = new ImageCreator();
        this.tableGenerator = new TableGenerator();
    }

    public void generatePdf(Date from, Date to, ArrayList<String> exercises, PdfType type) {
        if (type == PdfType.ONE_PDF_FOR_ALL_EXERCISES) {
            this.generatePdf(from, to, exercises);
        } else if (type == PdfType.ONE_PDF_FOR_EACH_EXERCISE) {
            for (String exercise : exercises) {
                this.generatePdf(from, to, exercise);
            }
        }
    }

    /**
     * Generates a pdf for multiple exercises
     *
     * @param from
     * @param to
     * @param exercises
     */
    public void generatePdf(Date from, Date to, List<String> exercises) {

        Document document = this.generateDocument("TrainingsData");
        document.open();

        this.addHeadline("trainings data", document);

        this.addDate(from, to, document);

        this.spacer(document, 1);

        document.setMargins(10, 10, 10, 10);

        //Headline End

        for (String exercise : exercises) {
            this.addExerciseHeadline(document, exercise);

            this.addImage(from, to, exercise, document);

            this.addFrequencyPerWeek(from, to, exercise, document);

            this.spacer(document, 1);

            this.addTable(from, to, exercise, document);

            this.spacer(document, 2);
        }
        document.close();
    }


    /**
     * Generates a pdf for a specific exercise
     *
     * @param from
     * @param to
     * @param exercise
     */
    public void generatePdf(Date from, Date to, String exercise) {

        Document document = generateDocument(exercise);

        document.open();

        document.setMargins(10, 10, 10, 10);

        this.addExerciseHeadline(document, exercise);

        this.addImage(from, to, exercise, document);

        this.addFrequencyPerWeek(from, to, exercise, document);

        this.spacer(document, 1);

        this.addTable(from, to, exercise, document);

        document.close();
    }


    private Document generateDocument(String name) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private void addHeadline(String title, Document document) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        Chunk headline = new Chunk(title, font);
        Paragraph paragraph = new Paragraph();
        paragraph.add(headline);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addExerciseHeadline(Document document, String exercise) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        Chunk headline = new Chunk(exercise, font);
        headline.setUnderline(0.1f, -2f);
        Paragraph paragraph = new Paragraph();
        paragraph.add(headline);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private void addDate(Date from, Date to, Document document) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Paragraph paragraphDate = new Paragraph();
        String textDates = format.format(from) + " - " + format.format(to);
        paragraphDate.add(new Chunk(textDates, font));
        paragraphDate.setAlignment(Element.ALIGN_CENTER);

        try {
            document.add(paragraphDate);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addFrequencyPerWeek(Date from, Date to, String exercise, Document document) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, BaseColor.BLACK);
        Paragraph paragraphFrequency = new Paragraph();
        DecimalFormat f = new DecimalFormat("#0.00");
        String frequency = "Frequency: " + String.valueOf(f.format
                (this.dataManger.getFrequencyPerWeek(from, to, exercise))) + " workouts per week";
        paragraphFrequency.add(new Chunk(frequency, font));
        paragraphFrequency.setAlignment(Element.ALIGN_CENTER);

        try {
            document.add(paragraphFrequency);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private void addImage(Date from, Date to, String exercise, Document document) {
        try {
            BufferedImage bufferedImage =
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.EFFECTIVE_GRAPH);
            Image imageEffective = Image.getInstance(bufferedImage, null);
            imageEffective.scalePercent(50);
            document.add(imageEffective);

            bufferedImage =
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.WEIGHT_GRAPH);
            Image imageWeight = Image.getInstance(bufferedImage, null);
            imageWeight.scalePercent(25);

            bufferedImage =
                    this.imageCreator.createCoordinateSystem(from, to, exercise, GraphType.REPS_GRAPH);
            Image imageReps = Image.getInstance(bufferedImage, null);
            imageReps.scalePercent(25);
            // document.add(imageReps);

            PdfPTable table = new PdfPTable(2);
            PdfPCell pic1 = new PdfPCell(imageWeight);
            PdfPCell pic2 = new PdfPCell(imageReps);
            pic1.setBorder(Rectangle.NO_BORDER);
            pic2.setBorder(Rectangle.NO_BORDER);
            table.addCell(pic1);
            table.addCell(pic2);

            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            document.add(table);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addTable(Date from, Date to, String exercise, Document document) {
        PdfPTable table = tableGenerator.generateTable(from, to, exercise);

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void spacer(Document document, int spaces) {
        for (int i = 0; i <= spaces; i++) {
            try {
                document.add(Chunk.NEWLINE);
            } catch (DocumentException ex) {
                ex.printStackTrace();
            }
        }
    }

}