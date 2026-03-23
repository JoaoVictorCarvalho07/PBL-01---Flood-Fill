package v2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class FloodFill {

    private BufferedImage image;
    private FloodFillCanvas canvas;
    private int frameCount = 0;
    private int FRAME_SKIP = 1; // salva imagem a cada X pixels (aumente para imagens grandes)
    private int delay;
    public FloodFill(BufferedImage image, FloodFillCanvas canvas,int frameSkip,int delay) {
        this.image = image;
        this.canvas = canvas;
        FRAME_SKIP = frameSkip;
        this.delay = delay;
    }


    //  FLOOD FILL COM PILHA (Stack - LIFO)

    public void fillWithStack(int startX, int startY, Color newColor) {
        int targetColor = image.getRGB(startX, startY); // cor de fundo a ser substituída
        int paintColor  = newColor.getRGB();

        if (targetColor == paintColor) return; // já tem a cor, nada a fazer

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        System.out.println("Iniciando Flood Fill com PILHA...");

        while (!stack.isEmpty()) {
            Point p = stack.pop(); // desempilha

            // Verifica bounds e se ainda tem a cor de fundo
            if (!isValid(p.x, p.y, targetColor)) continue;

            // Pinta o pixel
            image.setRGB(p.x, p.y, paintColor);
            updateDisplay(); // atualiza animação

            // Empilha os 4 vizinhos laterais
            stack.push(new Point(p.x + 1, p.y)); // direita
            stack.push(new Point(p.x - 1, p.y)); // esquerda
            stack.push(new Point(p.x, p.y + 1)); // baixo
            stack.push(new Point(p.x, p.y - 1)); // cima
        }

        System.out.println("Flood Fill com PILHA concluído!");
        saveResult("output_stack.png");
    }

    // 
    //  FLOOD FILL COM FILA (Queue - FIFO)
    // 
    public void fillWithQueue(int startX, int startY, Color newColor) {
        int targetColor = image.getRGB(startX, startY);
        int paintColor  = newColor.getRGB();

        if (targetColor == paintColor) return;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));

        System.out.println("Iniciando Flood Fill com FILA...");

        while (!queue.isEmpty()) {
            Point p = queue.poll(); // desenfileira

            // Verifica bounds e cor de fundo
            if (!isValid(p.x, p.y, targetColor)) continue;

            // Pinta o pixel
            image.setRGB(p.x, p.y, paintColor);
            updateDisplay(); // atualiza animação

            // Enfileira os 4 vizinhos laterais
            queue.add(new Point(p.x + 1, p.y)); // direita
            queue.add(new Point(p.x - 1, p.y)); // esquerda
            queue.add(new Point(p.x, p.y + 1)); // baixo
            queue.add(new Point(p.x, p.y - 1)); // cima
        }

        System.out.println("Flood Fill com FILA concluído!");
        saveResult("output_queue.png");
    }

    // 
    //  VERIFICA SE O PIXEL É VÁLIDO
    // 
    private boolean isValid(int x, int y, int targetColor) {
        // 1. Verifica bounds (evita Index Out Of Bounds)
        if (x < 0 || x >= image.getWidth())  return false;
        if (y < 0 || y >= image.getHeight()) return false;
        // 2. Verifica se a cor é igual à cor de fundo
        System.out.println(image.getRGB(x, y));
        return image.getRGB(x, y) == targetColor;
    }

    // 
    //  ATUALIZA A ANIMAÇÃO
    // 
    private void updateDisplay() {
        frameCount++;
        if (frameCount % FRAME_SKIP == 0) {
            canvas.setImage(image); // repassa imagem atualizada para o canvas
            try {
                Thread.sleep(delay); // delay para animação visível (ajuste conforme desejar)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // 
    //  SALVA A IMAGEM RESULTANTE
    // 
    private void saveResult(String filename) {
        try {
            File output = new File(filename);
            ImageIO.write(image, "PNG", output);
            System.out.println("Imagem salva em: " + output.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }
}