import models.Coordinate;
import models.ImageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageCreatorTest {

    private ImageCreator imageCreator;

    @BeforeEach
    public void setUp(){
        imageCreator = new ImageCreator();
    }

    @Test
    public void testCalculatePixelCoordinate(){

        assertEquals(950-20*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(20,0)).getX());
        assertEquals(475-10*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(0,10)).getY());
        assertEquals(950-40*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(40,0)).getX());
        assertEquals(475-20*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(0,20)).getY());
        assertEquals(950-60*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(60,0)).getX());
        assertEquals(475-30*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(0,30)).getY());
        assertEquals(950-80*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(80,0)).getX());
        assertEquals(475-40*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(0,40)).getY());
        assertEquals(950-100*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(100,0)).getX());
        assertEquals(475-50*9,
                this.imageCreator.calculatePixelCoordinate(new Coordinate(0,50)).getY());


    }
}
