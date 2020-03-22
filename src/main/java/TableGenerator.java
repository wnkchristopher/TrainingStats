import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class TableGenerator {
    DataManger dataManger;

    public TableGenerator() {
        dataManger = new DataManger();
    }

    public PdfPTable generateWeightTable(Date from, Date to, String exercise) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals("weight")){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        Font fontH1 = new Font(Font.getFamily("Currier"), 7, Font.NORMAL);
        int highestSet = dataManger.getHighestSet(from, to, exercise,exerciseType);
        List<Date> dates = getDates(from, to, exercise);
        PdfPTable table = new PdfPTable(2);
        table.setLockedWidth(true);
        table.setTotalWidth(445f);

        List<List<TrainingSet>> statsOfSets = new ArrayList<>();
        statsOfSets.add(this.dataManger.getListOfSet(1, from, to, exercise, exerciseType));

        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < dates.size(); i++) {
            String s = formatter.format(dates.get(i));
            table.addCell(new PdfPCell(new Phrase(s, fontH1)));
            try {
                TrainingSet trainingSet = statsOfSets.get(0).get(i);
                table.addCell(new Phrase(String.valueOf(trainingSet.getWeight()) + " kg", fontH1));
            } catch (NullPointerException e) {
                table.addCell("");
            } catch (IndexOutOfBoundsException e) {
                table.addCell("");
            }

        }

        return table;
    }

    public PdfPTable generateTable(Date from, Date to, String exercise) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals("weight")){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        Font fontH1 = new Font(Font.getFamily("Currier"), 7, Font.NORMAL);
        int highestSet = dataManger.getHighestSet(from, to, exercise,exerciseType);
        List<Date> dates = getDates(from, to, exercise);
        PdfPTable table = new PdfPTable(1 + highestSet * 2);
        table.setLockedWidth(true);
        table.setTotalWidth(445f);
        PdfPCell cell = new PdfPCell();
        cell.setRowspan(2);
        table.addCell(cell);
        for (int i = 0; i < highestSet; i++) {
            PdfPCell c = new PdfPCell(new Phrase("Set " + (i + 1), fontH1));
            c.setColspan(2);
            table.addCell(c);
        }

        List<List<TrainingSet>> statsOfSets = new ArrayList<>();

        for (int i = 0; i < highestSet; i++) {
            statsOfSets.add(this.dataManger.getListOfSet(i + 1, from, to, exercise, exerciseType));
            table.addCell(new Phrase("Weight", fontH1));
            table.addCell(new Phrase("Reps", fontH1));
        }


        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < dates.size(); i++) {
            String s = formatter.format(dates.get(i));
            table.addCell(new PdfPCell(new Phrase(s, fontH1)));
            for (int u = 0; u < highestSet; u++) {
                try {
                    TrainingSet trainingSet = statsOfSets.get(u).get(i);
                    table.addCell(new Phrase(String.valueOf(trainingSet.getWeight()), fontH1));
                    table.addCell(new Phrase(String.valueOf(trainingSet.getReps()), fontH1));
                } catch (NullPointerException e) {
                    table.addCell("");
                    table.addCell("");
                } catch (IndexOutOfBoundsException e) {
                    table.addCell("");
                    table.addCell("");
                }
            }
        }

        return table;
    }


    private List<Date> getDates(Date from, Date to, String exercise) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals("weight")){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        List<Date> dates = new LinkedList<>();
        for (Map.Entry<Date, List<TrainingSet>> m :
                dataManger.getStatsBetweenDates(exercise, from, to, exerciseType).entrySet()) {
            dates.add(m.getKey());
        }
        return dates;
    }
}
