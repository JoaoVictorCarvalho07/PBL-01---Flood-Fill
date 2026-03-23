package v2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FloodFillCanvas extends JPanel {

    private BufferedImage image;
    public int scale;

    public FloodFillCanvas(BufferedImage image,int scale) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        this.scale = scale;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint(); // redesenha a cada atualização
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Escala a imagem para caber melhor na tela
            g.drawImage(image, 0, 0,
                    image.getWidth() * scale,
                    image.getHeight() * scale,
                    null);
        }
    }
}