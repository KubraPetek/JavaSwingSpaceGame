
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Atesler{
    private int x;
    private int y;

    public Atesler(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}

public class Game extends JPanel implements KeyListener,ActionListener{
    
    public static int value;
    Timer timer=new Timer(1, this);
    private int gecen_sure=0;
    private int toplam_atis=0;
    private BufferedImage backgroundImage;
    private BufferedImage image;
    private int atesDirY=1;//Atesler yanlızca yukarıya doğru gidecek.Bu yüzden yalnızca Y için değişken tanımladık.
    private int topX=0;//Sağa sola gitmeyi ayarlayacak.
    private int topDirX=2;//topX e eklenecek değer.Belli bir limite çarpınca geri dönmeyi sağlayacak.
    private int uzayGemisiX=0;//Uzay gemisinin nerden başlayacağını gösterecek.
    private int dirUzyX=20;//Uzay gemisinin kaydırma işlemleri için gerekli.
    private ArrayList<Atesler> atesler=new ArrayList<Atesler>();
    
  
    public boolean kotrolEt(){
        for (Atesler ates : atesler) {
            if (new Rectangle(ates.getX(),ates.getY(),10,15).intersects(new Rectangle(topX,0,20,20))) {
                
                 return true;
            }
        }   
         return false;
    }

    
    public Game() {
        try {
            JOptionPane.showMessageDialog(this, "Gemiyi hareket ettirmek için yön tuşlarını,ateş etmek içinde CTRL tuşunu kullanabilirsiniz.");
            image=ImageIO.read(new FileImageInputStream(new File("ship1.png")));
            backgroundImage=ImageIO.read(new FileImageInputStream(new File("bg5.jpg")));
            
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Resim okunamadı.");
        }
        setBackground(Color.BLACK);
        timer.start();
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
       
        gecen_sure+=5;
        g.drawImage(backgroundImage, 0, 0, this);
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);//top oluşturduk.
        g.drawImage(image, uzayGemisiX, 490, image.getWidth()/5,image.getHeight()/5,this);
        
        
        for (Atesler ates : atesler) {
            if (ates.getY()<0) {//Atış JFrameden çıktıysa.
                
                atesler.remove(ates);
            }
        }
        g.setColor(Color.BLUE);
        for (Atesler ates : atesler) {//ateşlerin çizdiğimiz kısım
            g.fillRect(ates.getX(), ates.getY(), 10, 15);
        }
        
        if (kotrolEt()) {
            timer.stop();
            String message="Kazandınız...\n"+"Toplam Atış : "+toplam_atis+"\n"+"Geçen süre : "+gecen_sure/1000.0;
            //JOptionPane.showMessageDialog(this, message);
           
            value= JOptionPane.showConfirmDialog(this, message,"Tekrar oynamak ister misiniz?",JOptionPane.YES_NO_OPTION);
            System.out.println(value);
            if (value==1) {
                System.exit(0);
            }
            else if (value==0) {
                GameScreen screen=new GameScreen("Yeni Oyun");
                screen.setResizable(false);//Oyun ekranının boyutunun değiştirilmesini engelleyecek
                screen.setFocusable(false);//JFrame'e odaklanmayı kapatmaya yarar(Yanlızca JPanel'e odaklansın istiyoruz.)
                screen.setSize(800,600);//ekran boyutunu belirler.
                screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Oyunun kapanma formatı


                Game game=new Game();//JFrame içinde bir JPanel oluşturduk.
                game.requestFocus();//Odaklanmayı JPanel üzerine yaptık.//Bu üç komuutun yerleri önemli sıralamaya dikkat et.

                game.addKeyListener(game);

                game.setFocusable(true);
                game.setFocusTraversalKeysEnabled(false);//Klavye işlemlerini JPanel tarafından anlanmasını sağlayacak metod.

                screen.add(game);//JPanel'i JFrame'e ekledik.
                screen.setVisible(true);
            }
        
        }
    }

    @Override
    public void repaint() {//Paintin tekrar çağrılıp şekilllerin tekrar oluşturulmasını sağlar.
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
 
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c =e.getKeyCode();
        if (c==KeyEvent.VK_LEFT) {//sola basılmış ise
            
            if (uzayGemisiX<=0) {
                uzayGemisiX=0;
            }else{
                uzayGemisiX-=dirUzyX;
            }
        }
        else if (c==KeyEvent.VK_RIGHT) {//sağa basılmış işe
             if (uzayGemisiX>=750) {
                uzayGemisiX=750;
            }else{
                uzayGemisiX+=dirUzyX;
            }
        }
        else if (c==KeyEvent.VK_CONTROL) {
            atesler.add(new Atesler(uzayGemisiX+22, 490));
            toplam_atis++;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for (Atesler ates : atesler) {
            ates.setY(ates.getY()-atesDirY);
        }
        topX+=topDirX;
        if (topX>=720) {
            topDirX=-topDirX;
        }
        if (topX<=0) {
          topDirX=-topDirX;  
        }
        repaint();
    }

}
