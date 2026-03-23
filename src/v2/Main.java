package v2;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //  1. Mostra o dialog de configuração 
                ConfigDialog dialog = new ConfigDialog(null);
                dialog.setVisible(true);

                if (!dialog.isConfirmed()) {
                    System.out.println("Cancelado pelo usuário.");
                    return;
                }

                //  2. Lê os parâmetros escolhidos 
                BufferedImage image = ImageIO.read(new File(dialog.getImagePath()));
                int startX    = dialog.getStartX();
                int startY    = dialog.getStartY();
                int scale     = dialog.getScale();
                int delay     = dialog.getDelay();
                int frameSkip = dialog.getFrameSkip();
                Color color   = dialog.getColor();
                boolean stack = dialog.useStack();

                //  3. Monta a janela principal 
                FloodFillCanvas canvas = new FloodFillCanvas(image,scale);

                JFrame frame = new JFrame("Flood Fill - Animação");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new JScrollPane(canvas));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                //  4. Inicia o Flood Fill 
                FloodFill floodFill = new FloodFill(image, canvas, frameSkip, delay);

                new Thread(() -> {
                    try {
                        Thread.sleep(400);
                        if (stack) floodFill.fillWithStack(startX, startY, color);
                        else       floodFill.fillWithQueue(startX, startY, color);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}