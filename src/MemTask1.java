import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MemTask1 {
    static int AddPoint;
    static String AddText;
    static BufferedImage myPicture;
    static int positionX = 0;
    static int positionY = 100;
    static Map<String, String> Help = new HashMap<>();


    static Graphics g;


    public static Object EnterAsk() throws IOException {
        System.out.println("Введите команду: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if (s.equals("AddFile")){
            AddFile();
            System.out.println("Файл успешно добавлен. ");
            return EnterAsk();
        }
        else if (s.equals("PrintFile")){
            PrintFile();
            System.out.println("Режим просмотра изображения");
            return EnterAsk();}
        else if (s.equals("Mem")){
            Mem();
            System.out.println("Текст успешно добавлен. ");
            return EnterAsk();}
        else if (s.equals("ImageSave")){
            ImageSave();
            System.out.println("Файл успешно добавлен. ");
            return EnterAsk();}
        else if (s.equals("Exit")){
            System.out.println("Работа завершена.");
        }
        else if (s.equals("Help")){
            Help();
            return EnterAsk();
        }

        else {
            return EnterAsk();}
        return null;
    }



    static public BufferedImage AddFile () throws IOException {
        System.out.println("Укажите путь к файлу");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        myPicture = ImageIO.read(new File(s));

        System.out.println("Добавлен новый файл");
        return myPicture;

    }

    static public void Help ()  {
        System.out.println("Справочное меню:");

        System.out.println("(Название команды - значение)");
        System.out.println("");

        Help.put("AddFile","Найти изображение для создания мема");
        Help.put("Mem","Создание/Добавление текста в указанную область");
        Help.put("ImageSave","Сохранение изображения");
        Help.put("PrintFile","Вывод изображения на экран");
        Help.put("Exit","Завершить работу редактора");
        for(Map.Entry<String,String> entry: Help.entrySet()){
            System.out.println(entry.getKey()+ " - "+ entry.getValue());
        }

    }




    static public JFrame PrintFile () {
        JLabel myLabel = new JLabel(new ImageIcon(myPicture));
        JPanel myPanel = new JPanel();
        myPanel.add(myLabel);
        JFrame myFrame = new JFrame();
        myFrame.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        myFrame.add(myPanel);
        myFrame.setVisible(true);

        return myFrame;
    }


    static public void Mem() {
        System.out.println("Введите текст: ");
        Scanner scanner = new Scanner(System.in);
        AddText = scanner.nextLine();
        System.out.println("Выберите шрифт (пр. Arial, Bart)");
        Scanner scanner1 = new Scanner(System.in);
        String txtStyle = scanner.nextLine();
        System.out.println("Выберите размер текста :");
        Scanner scanner2 = new Scanner(System.in);
        int txtSize = scanner.nextInt();
        Font font = new Font(txtStyle, Font.BOLD, txtSize);
        g = myPicture.getGraphics();


        FontMetrics ruler = g.getFontMetrics(font);
        GlyphVector vector = font.createGlyphVector(ruler.getFontRenderContext(), AddText);
        Shape outline = vector.getOutline(0, 0);
        double expectedWidth = outline.getBounds().getWidth();
        double expectedHeight = outline.getBounds().getHeight();
        boolean textFits = myPicture.getWidth() >= expectedWidth && myPicture.getHeight() >= expectedHeight;
        double widthBasedFontSize = (font.getSize2D() * myPicture.getWidth()) / expectedWidth;
        double heightBasedFontSize = (font.getSize2D() * myPicture.getHeight()) / expectedHeight;
        double newFontSize = widthBasedFontSize < heightBasedFontSize ? widthBasedFontSize : heightBasedFontSize;
        Font font2 = font.deriveFont(font.getStyle(), (float) newFontSize);

        FontMetrics metrics = g.getFontMetrics(font2);

        System.out.println("Выберите место для вставки текста (1-Top, 2-Center, 3- Bottom)");
        Scanner scanner3 = new Scanner(System.in);
        AddPoint = scanner.nextInt();
        AttributedString attributedText = new AttributedString(AddText);
        attributedText.addAttribute(TextAttribute.FONT, font2);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);

        if (AddPoint==1) {
            positionX = 0;
            positionY = metrics.getAscent();



            g.drawString(attributedText.getIterator(), positionX - 10, positionY);
            g.dispose();
        } else if (AddPoint==2) {

            positionX = (myPicture.getWidth() - metrics.stringWidth(AddText)) / 2;
            positionY = (myPicture.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();




            g.drawString(attributedText.getIterator(), positionX , positionY);
            g.dispose();
        }


        else if (AddPoint==3) {
            positionX = (myPicture.getWidth() - metrics.stringWidth(AddText));
            positionY = (myPicture.getHeight() - metrics.getHeight()) + metrics.getAscent();



            g.drawString(attributedText.getIterator(), positionX , positionY);
            g.dispose();
        } else {



            g.drawString(attributedText.getIterator(), positionX , positionY);
            g.dispose();
        }
    }
    static public void ImageSave () throws IOException {
        System.out.println("Укажите путь для сохранения: ");
        Scanner scanner = new Scanner(System.in);
        String savePath = scanner.nextLine();
        System.out.println("Имя файла: ");
        Scanner scanner1 = new Scanner(System.in);
        String saveName = scanner.nextLine();



        ImageIO.write(myPicture, "png", new File(savePath+ "/"+saveName+".png"));

    }
    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в MemConstructor!");
        System.out.println("");

        System.out.println("(введите Help для вызова справочного меню)");


        MemTask1.EnterAsk();






















    }


}
