import java.awt.*;
import java.util.Random;
import javax.swing.*;
public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;
	GUI(GameData gameData) {
		this.gameData = gameData;
		f = new JFrame("Magic Tower");
		b = new JLabel[gameData.H][gameData.W];
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j]=new JLabel();
				b[i][j].setBounds(j*100, i*100, 100, 100);
				f.add(b[i][j]);
			}
		}
		f.setSize(gameData.H*100+10, gameData.W*100+40);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		refreshGUI();
	}
	public void refreshGUI()
	{
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j],i,j);
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}
	}

	private static Image chooseImage(int index,int x,int y){
		ImageIcon[] icons = new ImageIcon[13];
		Image scaledImage;
		icons[0]= new ImageIcon("Wall.jpg");
		icons[1]= new ImageIcon("Floor.jpg");
		icons[2]= new ImageIcon("Key.jpg");
		icons[3]= new ImageIcon("Door.jpg");
		icons[4]= new ImageIcon("Stair.jpg");
		icons[5]= new ImageIcon("Exit.jpg");
		icons[6]= new ImageIcon("Hero.jpg");
		icons[7]= new ImageIcon("Potion.jpg");
		icons[8]= new ImageIcon("Monster.jpg");
        icons[9]= new ImageIcon("monster2.png");
        icons[10]= new ImageIcon("monster3.png");
        icons[11]= new ImageIcon("sword.png");
        icons[12]= new ImageIcon("shield.png");
		if(index>10)
			scaledImage = TextOverIconUtil.generateTextOverImage(icons[7].getImage(),Integer.toString(index)).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else if(index<0)
			scaledImage = TextOverIconUtil.generateTextOverImage(icons[8].getImage(),Integer.toString(index)).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        else if(index==7)
            scaledImage = icons[11].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        else if(index==8)
            scaledImage = icons[12].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        else if(index==6)
            scaledImage = TextOverIconUtil.generateTextOverImage(icons[6].getImage(),Double.toString(GameData.heroHealth)).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else
			scaledImage = icons[index].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return scaledImage;
	}
}
