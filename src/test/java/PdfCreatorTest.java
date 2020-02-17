import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PdfCreatorTest {
    GeneratePdf generatePdf;
    @BeforeEach
    public void setUp(){
        generatePdf = new GeneratePdf();
    }

    @Test
    public void createPdfsTest(){
        String strFrom = "15.3.19";
        String strTo = "21.6.19";

        Date from = new Date();
        Date to = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try{
            from = sdf.parse(strFrom);
            to = sdf.parse(strTo);
        }catch(ParseException e){
            e.printStackTrace();
        }
        generatePdf.generatePdf(from, to, "pull ups");
    }

    @Test
    public void createPdfTest(){
        String strFrom = "15.3.2019";
        String strTo = "21.6.2019";

        Date from = new Date();
        Date to = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try{
            from = sdf.parse(strFrom);
            to = sdf.parse(strTo);
        }catch(ParseException e){
            e.printStackTrace();
        }
        List<String> exercises = new ArrayList<>();
        exercises.add("pull ups");
        exercises.add("dips");
        exercises.add("cable rowing");
        generatePdf.generatePdf(from, to, exercises);
    }
}
