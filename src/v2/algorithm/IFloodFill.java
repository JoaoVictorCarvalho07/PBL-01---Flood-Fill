package v2.algorithm;
import java.awt.Color;

public interface IFloodFill {

    void fill(int startX, int startY, Color newColor);
    void forceDisplayUpdate();

}
