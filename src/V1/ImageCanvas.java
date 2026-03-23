package V1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageCanvas extends JPanel {

    private BufferedImage image;
    private final List<Setor> setors;

    public ImageCanvas(BufferedImage image, List<Setor> setors) {
        this.image = deepCopy(image); // cópia para não alterar o original
        this.setors = setors;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    // Inicia a pintura setor por setor com delay visível
    public void startPainting(int delayPerSectorMs) {
        new Thread(() -> {
            for (Setor setor : setors) {
                paintSectorAnimated(setor, delayPerSectorMs);
            }
        }).start();
    }

    // Pinta um setor pixel a pixel (linha por linha)
    private void paintSectorAnimated(Setor sector, int totalDelayMs) {
        int rgb = sector.color.getRGB();
        int delayPerRow = Math.max(1, totalDelayMs / sector.h);

        for (int row = sector.y; row < sector.y + sector.h; row++) {
            for (int col = sector.x; col < sector.x + sector.w; col++) {
                // Garante que não ultrapasse os limites da imagem
                if (col < image.getWidth() && row < image.getHeight()) {
                    image.setRGB(col, row, rgb);
                }
            }

            // Redesenha após cada linha para animação visível
            repaint();

            try {
                Thread.sleep(delayPerRow);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    private BufferedImage deepCopy(BufferedImage source) {
        BufferedImage copy = new BufferedImage(
                source.getWidth(), source.getHeight(), source.getType()
        );
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();
        return copy;
    }
}