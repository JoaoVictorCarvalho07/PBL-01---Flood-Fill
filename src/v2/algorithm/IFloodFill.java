package v2.algorithm;
import java.awt.Color;

//Interface para implementar métodos para Stack e Queue
public interface IFloodFill {

    void fill(int startX, int startY, Color newColor);
    void forceDisplayUpdate();

}
