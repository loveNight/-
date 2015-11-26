package king;

/**
 * 游戏窗口
 */
import java.awt.Toolkit;
import javax.swing.JFrame;
public class GameFrame extends JFrame{
    public GameFrame(){
        super("欢乐五子棋");//窗口标题
        //添加棋盘
        ChessBoard chessBoard = new ChessBoard();
        add(chessBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();         
        //将窗口设置为正中显示
        //Toolkit是抽象类，只能用getDefaultToolkit()方法来获取实例。
        //getScreenSize()方法返回的是一个Dimension对象，还须用getWidth()获取宽度
        int screenSizeX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenSizeY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int fSizeX = (int)getSize().getWidth();
        int fSizeY = (int)getSize().getHeight();
        setResizable(false);//禁止改变窗口大小
        setBounds((screenSizeX-fSizeX)/2, (screenSizeY-fSizeY)/2, fSizeX,fSizeY );
        setVisible(true);
    }    
}