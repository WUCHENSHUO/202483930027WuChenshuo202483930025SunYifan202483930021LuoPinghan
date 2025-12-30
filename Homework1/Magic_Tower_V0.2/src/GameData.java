import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.lang.reflect.Field;

public class GameData implements Serializable {
	int L, H, W, currentLevel,atk,def,money;
	int pX, pY, keyNum;
    double heroHealth;
    boolean fs,is;
	int[][][] map;
	void readMapFromFile(String filePath) {
		currentLevel = 0;
		heroHealth = 105;
        atk=0;
        def=0;
        money=0;
		keyNum = 0;
		pX = 3;
		pY = 3;
        fs=true;
        is=true;
		try {
			Scanner in = new Scanner(new File(filePath));
			L = in.nextInt();
			H = in.nextInt();
			W = in.nextInt();
			map = new int[L][H][W];
			for (int i = 0; i < L; i++)
				for (int j = 0; j < H; j++)
					for (int k = 0; k < W; k++)
						map[i][j][k] = in.nextInt();
		} catch (IOException e) {
			System.out.println("Error with files:" + e.toString());
		}
        System.out.println("Map loaded.");
	}

	void printMap() {
		String C[] = { "Wall   ", "___    ", "Key    ", "Door   ", "Stair  ", "Exit   ", "Hero   ","Sword  ","Shield ","Chest   ","Shop    ","Money   " };
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (map[currentLevel][j][k] < 0)
					System.out.print("Monster ");
				else if (map[currentLevel][j][k] > 11)
					System.out.print("healPot ");
				else
					System.out.print(C[map[currentLevel][j][k]] + " ");
			}
			System.out.print("\n\n");
		}
		System.out.print(
				"Hero: Health:" + String.format("%.2f", heroHealth) +" ATK:"+atk+" DEF:"+def+" Money:"+money+ "  KeyNum:" + Integer.toString(keyNum) + "  [Menu]-press 0-\n");
	}

	void copyFields(Object source) {
		try {
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(source);
				field.set(this, value);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
