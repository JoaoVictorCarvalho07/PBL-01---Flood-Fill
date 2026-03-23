package V1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // 1. Carrega a imagem
        BufferedImage image = ImageIO.read(new File("img_1.png"));

        // 2. Define os setores (x, y, largura, altura, cor)
        List<Setor> setors = List.of(
                new Setor(50,  50,  200, 100, new Color(255, 0, 0, 150)),   // Vermelho semitransparente
                new Setor(300, 100, 150, 200, new Color(0, 255, 0, 150)),   // Verde semitransparente
                new Setor(100, 300, 250, 80,  new Color(0, 0, 255, 150))    // Azul semitransparente
        );

        // 3. Cria o canvas e a janela
        ImageCanvas canvas = new ImageCanvas(image, setors);

        JFrame frame = new JFrame("Image Painter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(canvas)); // ScrollPane caso imagem seja grande
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 4. Inicia a pintura animada (300ms por setor)
        canvas.startPainting(300);
    }
}