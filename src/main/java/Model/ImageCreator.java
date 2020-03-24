package Model;

import Enum.GraphType;
import Enum.ExerciseType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class ImageCreator {
    private DataManger dataManger;
    private CalculateStats calculateStats;
    private int width = 1000;
    private int height = 500;
    private int maxNormedWidth = 100;
    private int maxNormedHeight = 50;
    private int quantityOfSeparationsX;
    private int quantityOfSeparationsY;

    public ImageCreator() {
        this.quantityOfSeparationsX = 10;
        this.quantityOfSeparationsY = 5;
        this.dataManger = new DataManger();
        this.calculateStats = new CalculateStats();
    }

    public BufferedImage createCoordinateSystem(Date fromDate, Date toDate, String exercise, GraphType graphType) {
        Coordinate from = new Coordinate();
        Coordinate to = new Coordinate();
        int distance;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, this.width, this.height);
        image = this.drawCoordinateSystem(image, fromDate, toDate, exercise, graphType);
        image = this.drawGraph(image, exercise, fromDate, toDate, graphType);

        graphics2D.dispose();

        return image;
    }

    private BufferedImage drawCoordinateSystem(BufferedImage image, Date fromDate, Date toDate, String exercise
            , GraphType graphType) {
        Coordinate from = new Coordinate();
        Coordinate to = new Coordinate();
        int distance;

        final Graphics2D graphics2D = image.createGraphics();
        int proportionArrow = (int) ((Double.valueOf(maxNormedWidth)) / 100);
        if (proportionArrow == 0) {
            proportionArrow = 1;
        }
        //coordinate system
        graphics2D.setPaint(Color.BLACK);
        //x-axis
        int endOfXAxis = (int) (this.maxNormedWidth * 1.05);
        this.drawText(image, "Date", endOfXAxis, 0, "endX");
        image = this.drawLine(image, 0, 0, endOfXAxis, 0); //+10%
        //arrow
        image = this.drawLine(image, endOfXAxis, 0, endOfXAxis - proportionArrow,
                -proportionArrow);
        image = this.drawLine(image, endOfXAxis, 0, endOfXAxis - proportionArrow,
                proportionArrow);

        int daysBetweenSeparations = this.calculateStats.calculateDaysBetweenDates(toDate, fromDate) /
                this.quantityOfSeparationsX;
        //separations
        distance = this.maxNormedWidth / this.quantityOfSeparationsX;
        Calendar calendar = Calendar.getInstance();
        Date d = fromDate;
        calendar.setTime(d);
        for (int i = 0; i <= this.quantityOfSeparationsX; i++) {
            String newDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                    (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
            image = this.drawText(image, newDate, i * distance, -proportionArrow, "x");
            image = this.drawLine(image, i * distance, -proportionArrow, i * distance, proportionArrow);
            calendar.add(Calendar.DAY_OF_MONTH, daysBetweenSeparations);

        }
        //y-axis
        int endOfYAxis = (int) (this.maxNormedHeight * 1.07);
        String graphTypeTitle = "";
        if (graphType == GraphType.EFFECTIVE_GRAPH) {
            graphTypeTitle = "Effective weight: reps * weight";
        } else if (graphType == GraphType.WEIGHT_GRAPH) {
            graphTypeTitle = "Weight in kg";
        } else if (graphType == GraphType.REPS_GRAPH) {
            graphTypeTitle = "Reps";
        }
        this.drawText(image, graphTypeTitle, 0, endOfYAxis, "endY");
        image = this.drawLine(image, 0, 0, 0, endOfYAxis);
        //arrow
        image = this.drawLine(image, 0, endOfYAxis, proportionArrow,
                endOfYAxis - proportionArrow);
        image = this.drawLine(image, 0, endOfYAxis, -proportionArrow,
                endOfYAxis - proportionArrow);
        //separations
        double[] minMax = this.getMinMaxEffective(fromDate, toDate, exercise, graphType);
        double valueDifferenceBetweenSeperations = (double) ((minMax[1] - minMax[0]) /
                (double) this.quantityOfSeparationsY);
        double value = (int) minMax[0];
        for (int i = 0; i <= this.quantityOfSeparationsY; i++) {
            String text = String.valueOf((int) value);
            image = this.drawText(image, text, -proportionArrow, i * distance, "y");
            image = this.drawLine(image, -proportionArrow, i * distance, proportionArrow, i * distance);
            value += valueDifferenceBetweenSeperations;
        }
        graphics2D.dispose();

        return image;
    }

    private BufferedImage drawGraph(BufferedImage image, String exercise, Date from, Date to,
                                    GraphType graphType) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals(Constants.bodyWeight)){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        int highestSet = this.dataManger.getHighestSet(from, to, exercise, exerciseType);

        for (int i = 1; i <= highestSet; i++) {
            Coordinate tmp = null;
            Color color = this.getColorOfSet(i);
            if (graphType == GraphType.EFFECTIVE_GRAPH) { //History just for Effective Graph
                this.drawColorHistory(image, i, color);
            }
            //at the moment just first set
            for (Coordinate c : this.getListOfSet(i, from, to, exercise, graphType)) {
                this.drawCircle(image, (int) c.getX(), (int) c.getY(), 0.5, color);
                if (tmp == null) {
                    tmp = c;
                } else {
                    this.drawLine(image, (int) tmp.getX(), (int) tmp.getY(), (int) c.getX(),
                            (int) c.getY(), color);
                    tmp = c;
                }
            }
        }

        return image;
    }

    private BufferedImage drawText(BufferedImage image, String text, int x, int y) {
        this.drawText(image, text, x, y, null);
        return image;
    }

    private BufferedImage drawText(BufferedImage image, String text, int x, int y, String xy) {
        int deltaX = 0, deltaY = 0;
        Coordinate textCoordinates = new Coordinate();
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(Color.BLACK);
        int textWidth = graphics2D.getFontMetrics().stringWidth(text);
        int textHeight = graphics2D.getFontMetrics().getHeight();
        if (xy.equals("x")) {
            deltaX = -textWidth / 2;
            deltaY = -(int) ((double) textHeight * 1.3);
        } else if (xy.equals("y")) {
            deltaX = (int) (-textWidth * 1.1);
            deltaY = (int) (-textHeight * 0.3);
        } else if (xy.equals("endX")) {
            deltaX = (int) (textWidth * 0.3);
            deltaY = (int) (-textHeight * 0.3);
        } else if (xy.equals("endY")) {
            deltaX = -(int) (textWidth * 0.5);
            deltaY = (int) (textHeight * 0.2);
        }

        textCoordinates.setXY(x, y);
        textCoordinates = this.calculatePixelCoordinate(textCoordinates);
        textCoordinates.setXY(textCoordinates.getX() + deltaX, textCoordinates.getY() - deltaY);
        graphics2D.drawString(text, (int) textCoordinates.getX(), (int) textCoordinates.getY());
        graphics2D.dispose();

        return image;
    }

    private BufferedImage drawCircle(BufferedImage image, int x, int y, double radius, Color color) {
        Coordinate circle = new Coordinate();
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setPaint(color);
        circle.setXY(x, y);
        circle = this.calculatePixelCoordinate(circle);
        double factor = (double) this.width / (double) this.maxNormedWidth;
        radius = factor * radius;
        circle.setX(circle.getX() - radius / 2);
        circle.setY(circle.getY() - radius / 2);
        graphics2D.fillOval((int) circle.getX(), (int) circle.getY(), (int) radius, (int) radius);
        graphics2D.dispose();


        return image;
    }


    private BufferedImage drawLine(BufferedImage image, int x1, int y1, int x2, int y2) {
        return this.drawLine(image, x1, y1, x2, y2, Color.BLACK);
    }

    /**
     * Use normed coordinates
     *
     * @param image
     * @param x1    is a normed coordinate
     * @param y1    is a normed coordinate
     * @param x2    is a normed coordinate
     * @param y2    is a normed coordinate
     * @return BufferedImage with drawn line
     */
    private BufferedImage drawLine(BufferedImage image, int x1, int y1, int x2, int y2, Color color) {
        Coordinate from = new Coordinate();
        Coordinate to = new Coordinate();
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setPaint(color);
        from.setXY(x1, y1);
        to.setXY(x2, y2);
        from = this.calculatePixelCoordinate(from);
        to = this.calculatePixelCoordinate(to);
        graphics2D.drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());
        graphics2D.dispose();


        return image;
    }

    public Coordinate calculatePixelCoordinate(Coordinate normedCoordinate) { //from normed to pixel
        double tenPercentWidth = this.width * 0.1;
        double twentyPercentWidth = this.width * 0.2;
        double eighteenPercentHeight = this.height * 0.8;
        double tenPercentHeight = this.height * 0.1;
        double twentyPercentHeight = this.height * 0.2;

        double factorWidth = (this.width - twentyPercentWidth) / this.maxNormedWidth;
        double factorHeight = (this.height - twentyPercentHeight) / this.maxNormedHeight;


        double pixelX = (normedCoordinate.getX() * factorWidth) + tenPercentWidth;
        double pixelY = eighteenPercentHeight - (normedCoordinate.getY() * factorHeight) + tenPercentHeight;

        Coordinate pixelCoordinates = new Coordinate(pixelX, pixelY);

        return pixelCoordinates;
    }

    private BufferedImage drawColorHistory(BufferedImage image, int set, Color color) {
        Coordinate start = new Coordinate();
        start.setX(this.maxNormedWidth * 1.02);
        start.setY(this.maxNormedHeight * 1.02);
        start = this.calculatePixelCoordinate(start);
        start.setY(start.getY() + 15 * set);
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(color);
        graphics2D.fillRect((int) start.getX(), (int) start.getY(), 10, 10);
        graphics2D.setPaint(Color.black);
        graphics2D.drawString(set + ". set", (int) start.getX() + 12, (int) start.getY() + 10);
        graphics2D.dispose();


        return image;
    }

    private double[] getMinMaxEffective(Date from, Date to, String exercise, GraphType graphType) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals(Constants.bodyWeight)){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        double min = 0, max = 0;
        boolean firstTime = true;
        Map<Date, List<TrainingSet>> map = this.dataManger.getStatsBetweenDates(exercise, from, to, exerciseType);
        for (Map.Entry<Date, List<TrainingSet>> m :
                dataManger.getStatsBetweenDates(exercise, from, to, exerciseType).entrySet()) {
            for (TrainingSet t : m.getValue()) {
                double value = 0;
                if (graphType == GraphType.EFFECTIVE_GRAPH) {
                    value = t.getReps() * t.getWeight();
                } else if (graphType == GraphType.WEIGHT_GRAPH) {
                    value = t.getWeight();
                } else if (graphType == GraphType.REPS_GRAPH) {
                    value = t.getReps();
                }
                if (firstTime) {
                    min = value;
                    max = value;
                    firstTime = false;
                } else {
                    if (min > value) {
                        min = value;
                    }
                    if (max < value) {
                        max = value;
                    }

                }
            }
        }
        double[] minmax = {min, max};

        return minmax;
    }


    private List<Coordinate> getListOfSet(int set, Date from, Date to, String exercise, GraphType graphType) {
        ExerciseType exerciseType = ExerciseType.EXERCISE;
        if(exercise.equals(Constants.bodyWeight)){
            exerciseType = ExerciseType.BODYWEIGHT;
        }
        int daysBetweenDates = this.calculateStats.calculateDaysBetweenDates(to, from);
        double[] maxMin = this.getMinMaxEffective(from, to, exercise, graphType);
        int differentMaxMinTraining = (int) (maxMin[1] - maxMin[0]);
        List<Coordinate> coordinates = new LinkedList<>();
        for (Map.Entry<Date, List<TrainingSet>> m :
                dataManger.getStatsBetweenDates(exercise, from, to, exerciseType).entrySet()) {
            if (m.getValue().size() >= set) {
                int tmpDays = calculateStats.calculateDaysBetweenDates(m.getKey(), from); //days between from date and date of training
                double graphTypeValue = 0;
                if (graphType == GraphType.EFFECTIVE_GRAPH) {
                    graphTypeValue =
                            (double) (m.getValue().get(set - 1).getReps() * m.getValue().get(set - 1).getWeight());
                } else if (graphType == GraphType.WEIGHT_GRAPH) {
                    graphTypeValue = (double) m.getValue().get(set - 1).getWeight();
                } else if (graphType == GraphType.REPS_GRAPH) {
                    graphTypeValue = (double) (m.getValue().get(set - 1).getReps());
                }
                double factorX = (double) tmpDays / (double) daysBetweenDates;
                double differentMaxValue = maxMin[1] - graphTypeValue;
                double factorY = (differentMaxValue / (double) differentMaxMinTraining); //to fix something 0
                double X = (factorX * Double.valueOf(maxNormedWidth));
                double Y = maxNormedHeight - (factorY * Double.valueOf(maxNormedHeight));


                coordinates.add(new Coordinate((int) X, (int) Y));
            }
        }

        return coordinates;
    }


    private Color getColorOfSet(int set) {
        switch (set) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return new Color(0x34792F);
            case 4:
                return Color.MAGENTA;
            case 5:
                return Color.ORANGE;
            case 6:
                return Color.YELLOW;
            case 7:
                return new Color(0x2efe12);
            case 8:
                return new Color(0x56c3bb);
            case 9:
                return new Color(0xa0429f);
            case 10:
                return new Color(0x9e1778);
            case 11:
                return new Color(0xec7511);
            case 12:
                return new Color(0xc019c6);
            case 13:
                return new Color(0x9a87f8);
            case 14:
                return new Color(0x5f1073);
            case 15:
                return new Color(0x253fb2);
            default: {
                return Color.black;
            }
        }
    }
}
