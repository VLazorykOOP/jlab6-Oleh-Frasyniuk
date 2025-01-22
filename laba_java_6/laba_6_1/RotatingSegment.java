import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class RotatingSegment extends JPanel {
    private double angle = 0;
    private double t = 0; // Параметр для руху точки по відрізку

    // Оновлення стану при кожному кроці анімації
    public void update() {
        t += 0.01;
        if (t > 1) {
            t = 0; // Повернення точки на початок відрізка
        }
        angle += Math.PI / 50; // Кут обертання
        repaint();
    }

    // відрізок, що обертається
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Розміри вікна
        int width = getWidth();
        int height = getHeight();

        //Центр координат
        int centerX = width / 2;
        int centerY = height / 2;

        //Початкові координати відрізка
        double startX = -100;
        double startY = 0;
        double endX = 100;
        double endY = 0;

        //Кут обертання
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        //Обертання відрізка
        double rotatedEndX = cosAngle * endX - sinAngle * endY;
        double rotatedEndY = sinAngle * endX + cosAngle * endY;

        //Рухаюча точка
        double movingPointX = startX + t * (endX - startX);
        double movingPointY = startY + t * (endY - startY);

        //Малюємо відрізок
        g2d.setColor(Color.BLUE);
        g2d.draw(new Line2D.Double(centerX + movingPointX, centerY + movingPointY, centerX + rotatedEndX, centerY + rotatedEndY));

        //Малюємо точку, що рухається
        g2d.setColor(Color.RED);
        g2d.fillOval(centerX + (int) movingPointX - 3, centerY + (int) movingPointY - 3, 6, 6);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Segment");
        RotatingSegment panel = new RotatingSegment();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(panel);
        frame.setVisible(true);

        //Анімація
        new Timer(20, e -> panel.update()).start();
    }
}
