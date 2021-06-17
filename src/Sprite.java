import java.awt.*;
import javax.swing.ImageIcon;

public class Sprite {
	private ImageIcon sprite;

    public Sprite(String imageName, int x, int y) {
    	try {	
    		//Check if image exists 
    		sprite = new ImageIcon(this.getClass().getResource("/sprites/" + imageName + ".gif"));
    		Image image = sprite.getImage().getScaledInstance(x, y, Image.SCALE_FAST);
    		sprite.setImage(image);	//Set sprite field to resized image
    	}
    	catch (Exception e) {}	//If not, blank image sprite is created
    }
    
    /**
     * Gets the sprite image icon.
     * @return Stored image of resized sprite.
     */
    public ImageIcon getSprite()
    {
    	return this.sprite;
    }
}
