
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class GameScreen extends JFrame{

    
    public GameScreen(String title) throws HeadlessException {
        super(title);
    }
      
   
    public static void main(String[] args) {
        GameScreen screen=new GameScreen("Uzay Oyunu");
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
