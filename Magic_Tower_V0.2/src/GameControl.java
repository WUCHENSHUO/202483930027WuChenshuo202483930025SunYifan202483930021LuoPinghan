import java.util.Random;
import java.util.Scanner;

public class GameControl {
	GameData gameData;
	Menu menu;
    Random random = new Random();

	GameControl(GameData gameData, Menu menu) {
		this.gameData = gameData;
		this.menu = menu;
	}

	void gameStart() {
		Scanner keyboardInput = new Scanner(System.in);
		while (true) {
			String input = keyboardInput.next();
			if (input.length() != 1 || (input.charAt(0) != 'a' && input.charAt(0) != 's' && input.charAt(0) != 'd'
					&& input.charAt(0) != 'w' && input.charAt(0) != '0')) {
				System.out.println("Wrong Input.");
				continue;
			}
			if (input.charAt(0) == '0')
				menu.enterMenu();
			else
				handleInput(input.charAt(0));
			gameData.printMap();
		}
	}

	void handleInput(char inC) {
		int tX = 0, tY = 0;
		if (inC == 'a') {
			tX = gameData.pX;
			tY = gameData.pY - 1;
		}
		if (inC == 's') {
			tX = gameData.pX + 1;
			tY = gameData.pY;
		}
		if (inC == 'd') {
			tX = gameData.pX;
			tY = gameData.pY + 1;
		}
		if (inC == 'w') {
			tX = gameData.pX - 1;
			tY = gameData.pY;
		}
		if (gameData.map[gameData.currentLevel][tX][tY] == 2) {
			gameData.keyNum++;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 3 && gameData.keyNum > 0) {
			gameData.keyNum--;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 4) {
			gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
			gameData.currentLevel++;
			for (int i = 0; i < gameData.H; i++)
				for (int j = 0; j < gameData.W; j++)
					if (gameData.map[gameData.currentLevel][i][j] == 6) {
						gameData.pX = i;
						gameData.pY = j;
					}
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 5) {
            System.out.print("You Win!!");
            System.exit(0);
        } else if (gameData.map[gameData.currentLevel][tX][tY] == 7) {
            gameData.atk += 5;
            moveHero(tX, tY);
        } else if (gameData.map[gameData.currentLevel][tX][tY] == 8) {
            gameData.def += 5;
            moveHero(tX, tY);
        } else if (gameData.map[gameData.currentLevel][tX][tY] == 9 && gameData.keyNum > 0) {
            gameData.keyNum--;
            int R = random.nextInt(5);
            if (R == 0) {
                gameData.heroHealth += 50;
                System.out.println("You got a small heal potion.");
            } else if (R == 1) {
                gameData.heroHealth += 100;
                System.out.println("You got a big heal potion.");
            } else if (R == 2) {
                gameData.atk += 10;
                System.out.println("You got a rare sword.");
            } else if (R == 3) {
                gameData.def += 10;
                System.out.println("You got a rare shield.");
            } else if (R == 4) {
                gameData.money += 100;
                System.out.println("You got some money.");
            }
            moveHero(tX, tY);
        } else if (gameData.map[gameData.currentLevel][tX][tY] == 10) {
            System.out.println("Welcome to shop!");
            System.out.println("--------------------------------");
            System.out.print("1. Fire sword(atk+15)(100 money)");
            if (!gameData.fs){
                System.out.print("(Sold out)");
            }
            System.out.println();
            System.out.print("2. Iron shield(def+10)(200 money)");
            if (!gameData.is){
                System.out.print("(Sold out)");
            }
            System.out.println();
            System.out.println("3. Back");
            System.out.println("--------------------------------");
            Scanner keyboardInput = new Scanner(System.in);
            int input = keyboardInput.nextInt();
            switch (input) {
                case 1:
                    if(gameData.fs){if (gameData.money >= 100) {
                        gameData.money -= 100;
                        gameData.atk += 15;
                        System.out.println("You got a fire sword.");
                        gameData.fs = false;
                    } else {
                        System.out.println("You don't have enough money.");
                    }
                    }else {
                        System.out.println("Sold out.");
                    }
                    break;
                case 2:
                    if(gameData.is){
                        if (gameData.money >= 200) {
                            gameData.money -= 200;
                            gameData.def += 10;
                            System.out.println("You got a iron shield.");
                            gameData.is = false;
                        } else {
                            System.out.println("You don't have enough money.");
                        }
                    }else {
                        System.out.println("Sold out.");
                    }
                    break;
            }
        } else if (gameData.map[gameData.currentLevel][tX][tY] == 11) {
            gameData.money += 100;
            moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] > 11) {
			gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY];
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 1) {
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] < 0) {
            double damage = gameData.map[gameData.currentLevel][tX][tY]*(1-0.01*gameData.def)+gameData.atk;
			if (gameData.heroHealth+damage <= 0) {
				System.out.print("That monster has " + Integer.toString(-gameData.map[gameData.currentLevel][tX][tY])
						+ " power, You Lose!!");
				System.exit(0);
			} else {
				gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY]*(1-0.01*gameData.def)+gameData.atk;
				moveHero(tX, tY);
			}
		}
	}

	void moveHero(int tX, int tY) {
		gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
		gameData.map[gameData.currentLevel][tX][tY] = 6;
		gameData.pX = tX;
		gameData.pY = tY;
	}
}
