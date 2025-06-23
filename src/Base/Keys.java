package Base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean qPressed;
    public boolean ePressed;
    public boolean lPressed;
    public boolean SPACEPressed;

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = true;
            //GlobalStates.moved = true;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = true;
            //GlobalStates.moved = true;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = true;
            //GlobalStates.moved = true;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = true;
            //GlobalStates.moved = true;
        }
        if(code == KeyEvent.VK_Q)
        {
            qPressed = true;
        }
        if(code == KeyEvent.VK_E)
        {
            ePressed = true;
        }
        if(code == KeyEvent.VK_L)
        {
            lPressed = true;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            SPACEPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_Q)
        {
            qPressed = false;
        }
        if(code == KeyEvent.VK_E)
        {
            ePressed = false;
        }
        if(code == KeyEvent.VK_L);
        {
            lPressed = false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            SPACEPressed = false;
        }
    }
}
