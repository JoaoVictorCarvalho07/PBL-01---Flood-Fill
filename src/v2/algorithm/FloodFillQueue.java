package v2.algorithm;

import v2.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloodFillQueue extends AbstractFloodFill {

    public FloodFillQueue(BufferedImage image, FloodFillCanvas canvas,
                          int frameSkip, int delay, Color cor,
                          int framesToSnapshot) {
        super(image, canvas, frameSkip, delay, cor, framesToSnapshot);
    }

    @Override
    public void fill(int startX, int startY, Color newColor) {
        int targetColor = image.getRGB(startX, startY);
        int paintColor  = newColor.getRGB();

        if (targetColor == paintColor) return;

        v2.Queue<Point> queue = new v2.Queue<>();
        queue.add(new Point(startX, startY));

        System.out.println("Iniciando Flood Fill com FILA...");

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            if (!isValid(p.x, p.y, targetColor)) continue;

            image.setRGB(p.x, p.y, paintColor);
            updateDisplay();

            queue.add(new Point(p.x + 1, p.y));
            queue.add(new Point(p.x - 1, p.y));
            queue.add(new Point(p.x, p.y + 1));
            queue.add(new Point(p.x, p.y - 1));
        }

        forceDisplayUpdate();
        System.out.println("Flood Fill com FILA concluído!");
        saveResult("output_queue.png");
    }
}