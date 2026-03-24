package v2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FloodFill {

    private BufferedImage image;
    private FloodFillCanvas canvas;
    private int frameCount = 0;
    private int FRAME_SKIP = 1; // salva imagem a cada X pixels (aumente para imagens grandes)
    private int delay;
    private Color cor;
    private int framesToSnapshot;
    private int snapshotCount = 0;
    File snapshotOutputDir = new File("output/Snapshots");
    File finalOutputDir = new File("output/Final");


    public FloodFill(BufferedImage image, FloodFillCanvas canvas,int frameSkip,int delay,Color cor, int FramesToSnapshot) {
        this.image = image;
        this.canvas = canvas;
        FRAME_SKIP = frameSkip;
        this.delay = delay;
        this.cor = cor;
        this.framesToSnapshot = FramesToSnapshot;
        prepareOutputFolder();

    }

    public FloodFill(BufferedImage image, FloodFillCanvas canvas,int frameSkip,int delay,Color cor) {
        this.image = image;
        this.canvas = canvas;
        FRAME_SKIP = frameSkip;
        this.delay = delay;
        this.cor = cor;
        this.framesToSnapshot = 10;
        prepareOutputFolder();

    }


    //  FLOOD FILL COM PILHA (Stack - LIFO)

    public void fillWithStack(int startX, int startY, Color newColor) {
        int targetColor = image.getRGB(startX, startY); // cor de fundo a ser substituída
        int paintColor  = newColor.getRGB();



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

        Queue<Point> queue = new Queue<>();
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

        forceDisplayUpdate();
        System.out.println("Flood Fill com FILA concluído!");
        saveResult("output_queue.png");
    }

    // 
    //  VERIFICA SE O PIXEL É VÁLIDO
    // 
    private boolean isValid(int x, int y, int targetColor) {
        if (x < 0 || x >= image.getWidth()) return false;
        if (y < 0 || y >= image.getHeight()) return false;

        return isSimilarColor(image.getRGB(x, y), targetColor, 10);
    }

    // VERIFICA SE O PIXEL É VÁLIDO COM TOLERANCIA DE COR
    private boolean isSimilarColor(int rgb1, int rgb2, int tolerance) {
        Color c1 = new Color(rgb1, true);
        Color c2 = new Color(rgb2, true);

        int dr = Math.abs(c1.getRed() - c2.getRed());
        int dg = Math.abs(c1.getGreen() - c2.getGreen());
        int db = Math.abs(c1.getBlue() - c2.getBlue());
        int da = Math.abs(c1.getAlpha() - c2.getAlpha());

        return dr <= tolerance && dg <= tolerance && db <= tolerance && da <= tolerance;
    }

    // 
    //  ATUALIZA A ANIMAÇÃO
    // 

    public void updateDisplay() {
        frameCount++;

        if (frameCount % FRAME_SKIP == 0) {
            canvas.setImage(image);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (frameCount % framesToSnapshot == 0) {
            saveSnapshot();
        }
    }

    public void forceDisplayUpdate() {
        canvas.setImage(image);
        canvas.repaint();
    }

    // 
    //  SALVA A IMAGEM RESULTANTE
    // 
    private void saveResult(String filename) {
        try {
            updateDisplay();
            if (!finalOutputDir.exists()) {
                finalOutputDir.mkdirs();
            }
            File output = new File(finalOutputDir,filename);
            ImageIO.write(image, "PNG", output);
            System.out.println("Imagem salva em: " + output.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void saveSnapshot() {
        try {
            if (!snapshotOutputDir.exists()) {
                snapshotOutputDir.mkdirs();
            }
            String filename = String.format("snapshot_%04d.png", snapshotCount++);
            File output = new File(snapshotOutputDir,filename);
            ImageIO.write(image, "PNG", output);
            System.out.println("Snapshot salvo em: " + output.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar snapshot: " + e.getMessage());
        }
    }

    private void deleteDirectory(File dir) {
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }

        dir.delete();
    }

    private void prepareOutputFolder() {
        File outputDir = new File("output/");
        deleteDirectory(outputDir);
        outputDir.mkdirs();
    }


}