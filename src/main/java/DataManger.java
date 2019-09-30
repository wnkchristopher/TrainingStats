
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataManger {
    CalculateStats calculateStats;
    public DataManger(){
        this.calculateStats = new CalculateStats();
    }

    public void addNewExercise(String name){

        if(proveExerciseExists(name)){
            return;
        }
        try {     //add exercise to list of exercises
            FileWriter writer = new FileWriter("Data/exercises.txt", true);
            writer.write(name);
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.writeExerciseStats(name, "", null);
    }
    public boolean proveExerciseExists(String exercise_name){
        Iterator iterator = getExerciseList().iterator();
        while (iterator.hasNext()){
            if(iterator.next().equals(exercise_name)){
                return true;
            }
        }
        return false;
    }

    public List<String> getExerciseList(){
        List<String> exerciseList = new ArrayList<>();
        try {
            String line;
            BufferedReader bufferreader = new BufferedReader(new FileReader("Data/exercises.txt"));
            while ((line = bufferreader.readLine()) != null) {
                exerciseList.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return exerciseList;
    }
    public void changeExerciseOrder(List<String> exerciseOrder){
        try {
            PrintWriter writer = new PrintWriter("Data/exercises.txt");
            writer.print("");
            writer.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        for(String s : exerciseOrder){
            this.addNewExercise(s);
        }
    }

    public void addWorkout(Map<ExerciseSet, String[]> exerciseSetMap, Date dateOfTraining){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfTraining);
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                (calendar.get(Calendar.MONTH)+1)+ "." + calendar.get(Calendar.YEAR);
        String exerciseChecker = "";
        String exerciseContent = "";
        Map<ExerciseSet, String[]> test = new TreeMap<>(exerciseSetMap);
        for(ExerciseSet exerciseSet : test.keySet()){
            if(exerciseChecker.isEmpty()){
                exerciseChecker = exerciseSet.getExercise();
                exerciseContent = date + "|" +
                        exerciseSetMap.get(exerciseSet)[0] + "|" + exerciseSetMap.get(exerciseSet)[1];
            }else if(exerciseChecker.equals(exerciseSet.getExercise())){
                //einfach nächste Übung rein
                exerciseContent+= "|" + exerciseSetMap.get(exerciseSet)[0] + "|"
                        + exerciseSetMap.get(exerciseSet)[1];
            }else{
                this.writeExerciseStats(exerciseChecker,exerciseContent,dateOfTraining);
                exerciseChecker = exerciseSet.getExercise();
                exerciseContent = date + "|" +
                        exerciseSetMap.get(exerciseSet)[0] + "|" + exerciseSetMap.get(exerciseSet)[1];
            }
        }
        if(!exerciseContent.isEmpty()){
            this.writeExerciseStats(exerciseChecker,exerciseContent,dateOfTraining);
        }
    }

    public Date convertToDate(String sDate){
        Date date;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            date = dateFormatter.parse(sDate);
        }catch (ParseException e){
            return null;
        }
        return date;
    }

    private boolean isFileEmpty(String exercise){
        File logFile = new File("Data/Exercises/" + exercise + ".txt");
        if(logFile.length() == 0){
            return true;
        }
        return false;
    }




    private void writeExerciseStats(String exercise, String content, Date dateOfTraining){
        int line = this.getLineToInsert(exercise, dateOfTraining);
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("Data/Exercises/" + exercise + ".txt", true));
            //writer.write(content);
            writer.close();
            //new stats getting insert sorted
            if(dateOfTraining != null){
                Path path = FileSystems.getDefault()
                        .getPath("Data/Exercises/" + exercise + ".txt");
                List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
                lines.add(line, content);
                Files.write(path, lines);
            }

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private Date getDateOfLine(String line){
        int i = line.indexOf("|");
        if(i<0){
            return null;  //may be Exception later
        }
        String stringDate = line.substring(0,i);
        return this.convertToDate(stringDate);
    }

    private int getLineToInsert(String exercise, Date date){
        if(date == null){
            return 0;
        }

        int lineCounter = 0;
        try {
            String stringDate;
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Data/Exercises/" + exercise + ".txt"));
            while ((line = bufferedReader.readLine()) != null) {

                Date d = this.getDateOfLine(line);
                if(d == null){
                    break;
                }
                /*int i = line.indexOf("|");
                if(i<0){
                    break;
                }
                stringDate = line.substring(0,i);
                Date d = this.convertToDate(stringDate);*/
                if(d.before(date)){
                    lineCounter ++;
                }else{
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(lineCounter<0){
            lineCounter=0;
        }

        return lineCounter;
    }

    private int getLineOfDate(String exercise, Date date){
        try {
            int lineNumber = 0;
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Data/Exercises/" + exercise + ".txt"));
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber ++;
                Date d = this.getDateOfLine(line);
                if(d == null){
                    break;
                }
                if(d.equals(date)){
                    return lineNumber;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private List<TrainingSet> getTrainingSets(String exercise ,Date date){
        List<TrainingSet> list = new LinkedList<>();
        String line = "";
        int lineNumber = this.getLineOfDate(exercise,date);
        try {
            line = Files.readAllLines(Paths.get("Data/Exercises/"+exercise+".txt")).get(lineNumber-1);
        }catch(IOException e){
            e.printStackTrace();
        }
        String[] split = line.split("\\|");

        for(int i = 1; i <split.length; i+=2){
            list.add(new TrainingSet(Double.valueOf(split[i]), Double.valueOf(split[i+1])));
        }

        return list;

    }

    public Map<Date, List<TrainingSet>> getStatsBetweenDates(String exercise, Date from, Date to){

        Map<Date, List<TrainingSet>> tmpMap = new HashMap<>();
        try {
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader("Data/Exercises/"+ exercise+".txt"));
            while ((line = bufferedReader.readLine()) != null) {
                Date d = this.getDateOfLine(line);
                if(d == null){
                    break;
                }
                if((d.after(from)&&d.before(to)) || d.equals(from) || d.equals(to)){
                    tmpMap.put(d, this.getTrainingSets(exercise, d));
                }

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return (new TreeMap<>(tmpMap));
    }

    public int getHighestSet(Date from, Date to, String exercise){
        int highestSet = 0;
        Map<Date, List<TrainingSet>> map = this.getStatsBetweenDates(exercise,from, to);
        for(Map.Entry<Date, List<TrainingSet>> m: this.getStatsBetweenDates(exercise, from, to).entrySet()){
            int i = 0;
            for(TrainingSet t : m.getValue()){
                i++;
            }
            if(i>highestSet){
                highestSet = i;
            }
        }

        return highestSet;
    }

    public List<TrainingSet> getListOfSet(int set, Date from, Date to, String exercise) {
        List<TrainingSet> listStats = new LinkedList<>();
        for (Map.Entry<Date, List<TrainingSet>> m : this.getStatsBetweenDates(exercise, from, to).entrySet()) {
            try{
                listStats.add(new TrainingSet(m.getValue().get(set-1).getReps(),m.getValue().get(set-1).getWeight()));
            }catch(IndexOutOfBoundsException e){
                listStats.add(null);
            }
        }
        return listStats;
    }

    public float getFrequencyPerWeek(Date from, Date to, String exercise){
        float days = (float)this.calculateStats.calculateDaysBetweenDates(to, from);
        float weeks = days/7;
        float quantityWorkouts = (float)this.getListOfSet(1,from, to, exercise).size();
        return (quantityWorkouts/weeks);

    }
}
