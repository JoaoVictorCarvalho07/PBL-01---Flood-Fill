package v2.algorithm;

import v2.Point;
import v2.Stack;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Flood Fill usando PILHA (Stack - LIFO).
 * Comportamento: expande em profundidade, formando um padrão "recursivo".
 */
public class FloodFillStack extends AbstractFloodFill {

    public FloodFillStack(BufferedImage image, FloodFillCanvas canvas,
                          int frameSkip, int delay, Color cor,
                          int framesToSnapshot) {
        super(image, canvas, frameSkip, delay, cor, framesToSnapshot);
    }

    @Override
    public void fill(int startX, int startY, Color newColor) {
        int targetColor = image.getRGB(startX, startY);
        int paintColor  = newColor.getRGB();

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        System.out.println("Iniciando Flood Fill com PILHA...");

        while (!stack.isEmpty()) {
            Point p = stack.pop();

            if (!isValid(p.x, p.y, targetColor)) continue;

            image.setRGB(p.x, p.y, paintColor);
            updateDisplay();

            stack.push(new Point(p.x + 1, p.y)); // direita
            stack.push(new Point(p.x - 1, p.y)); // esquerda
            stack.push(new Point(p.x, p.y + 1)); // baixo
            stack.push(new Point(p.x, p.y - 1)); // cima
        }

        System.out.println("Flood Fill com PILHA concluído!");
        saveResult("output_stack.png");
    }
}