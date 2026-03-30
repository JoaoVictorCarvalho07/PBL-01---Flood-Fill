package v2.algorithm;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class AbstractFloodFill implements IFloodFill {

    protected BufferedImage image;
    protected FloodFillCanvas canvas;
    protected int frameCount = 0;
    protected int frameSkip;
    protected int delay;
    protected Color cor;
    protected int framesToSnapshot;
    protected static int  snapshotCount = 0;

    protected File snapshotOutputDir = new File("output/snapshots");
    protected File finalOutputDir    = new File("output/final");

    public AbstractFloodFill(BufferedImage image, FloodFillCanvas canvas,
                             int frameSkip, int delay, Color cor,
                             int framesToSnapshot) {
        this.image           = image;
        this.canvas          = canvas;
        this.frameSkip       = frameSkip;
        this.delay           = delay;
        this.cor             = cor;
        this.framesToSnapshot = framesToSnapshot;
    }

    public AbstractFloodFill(BufferedImage image, FloodFillCanvas canvas,
                             int frameSkip, int delay, Color cor) {
        this(image, canvas, frameSkip, delay, cor, 10);
    }

    protected boolean isValid(int x, int y, int targetColor) {
        if (x < 0 || x >= image.getWidth())  return false;
        if (y < 0 || y >= image.getHeight()) return false;
        return isSimilarColor(image.getRGB(x, y), targetColor, 10);
    }

    protected boolean isSimilarColor(int rgb1, int rgb2, int tolerance) {
        Color c1 = new Color(rgb1, true);
        Color c2 = new Color(rgb2, true);
        return Math.abs(c1.getRed()   - c2.getRed())   <= tolerance
                && Math.abs(c1.getGreen() - c2.getGreen()) <= tolerance
                && Math.abs(c1.getBlue()  - c2.getBlue())  <= tolerance
                && Math.abs(c1.getAlpha() - c2.getAlpha()) <= tolerance;
    }

    protected void updateDisplay() {
        frameCount++;
        if (frameCount % frameSkip == 0) {
            canvas.setImage(image);
            try { Thread.sleep(delay); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        if (frameCount % framesToSnapshot == 0) {
            saveSnapshot();
        }
    }

    @Override
    public void forceDisplayUpdate() {
        canvas.setImage(image);
        canvas.repaint();
    }

    protected void saveResult(String filename) {
        try {
            updateDisplay();
            if (!finalOutputDir.exists()) finalOutputDir.mkdirs();
            File output = new File(finalOutputDir, filename);
            ImageIO.write(image, "PNG", output);
            System.out.println("Imagem salva em: " + output.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void saveSnapshot() {
        try {
            if (!snapshotOutputDir.exists()) snapshotOutputDir.mkdirs();
            String filename = String.format("snapshot_%04d.png", snapshotCount++);
            File output = new File(snapshotOutputDir, filename);
            ImageIO.write(image, "PNG", output);
            System.out.println("Snapshot salvo em: " + output.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar snapshot: " + e.getMessage());
        }
    }


}