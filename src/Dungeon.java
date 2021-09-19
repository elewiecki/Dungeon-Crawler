import java.util.Random;

public class Dungeon {

char[][] dungeon;
char playerX;
int curX, curY;
Random rand;
Character player;
Character enemy;
int length, width;
int enemiesKilled;
int[][] dungeonMemory; //0 is undiscovered, 1 is cleared, 2 is enemy you ran away from

    public Dungeon(int length, int width, String playerX){
        dungeon = new char[length][width];
        dungeonMemory = new int[length][width];
        this.playerX = playerX.charAt(0);
        rand = new Random();
        curX = rand.nextInt(length);
        curY = 0;
        player = new Character(true, enemiesKilled);
        enemiesKilled = 0;
    }

    public void createDungeon(){
        for(int i = 0; i < dungeon.length; ++i){
            for(int j = 0; j < dungeon[i].length; ++j){
                    dungeon[i][j] = '_';
                    dungeonMemory[i][j] = 0;
            }       
        }
        dungeon[curX][curY] = playerX;
        dungeonMemory[curX][curY] = 1;
    }

    public void printDungeon(){
        for(int i = 0; i < dungeon.length; ++i){
            for(int j = 0; j < dungeon[i].length; ++j){
                if(j != dungeon[i].length - 1){
                    System.out.print(dungeon[i][j]);
                    System.out.print('|');
                }
                else{
                    System.out.println(dungeon[i][j]);
                }
            }
        }
        System.out.print("\n");
    }

    public boolean move(String direction, boolean isRunning){
        if(!isRunning) dungeon[curX][curY] = '*';
        else dungeon[curX][curY] = '_';
        boolean moved = true;
        if(direction.toLowerCase().compareTo("up") == 0 && curX != 0){
            curX = curX - 1;
        }
        else if(direction.toLowerCase().compareTo("down") == 0 && curX != dungeon.length - 1){
            curX = curX + 1;          
        }
        else if(direction.toLowerCase().compareTo("left") == 0 && curY != 0){
            curY = curY - 1;         
        }
        else if(direction.toLowerCase().compareTo("right") == 0 && curY != dungeon[1].length - 1){
            curY = curY + 1;
        }
        else{
            if(!isRunning) System.out.println("Can't go there! Try again\n");
            moved = false;
        }
        dungeon[curX][curY] = playerX;
        return moved;
    }

    public int encounter(){
        int randNum = rand.nextInt(100);
        if(dungeonMemory[curX][curY] == 1){
            System.out.println("You've already been here, nothing happens\n");
            return 2;
        }
        if(dungeonMemory[curX][curY] == 0 && randNum < 30){
            System.out.println("Nothing happens\n");
            dungeonMemory[curX][curY] = 1;
            return 2;
        }
        else if(dungeonMemory[curX][curY] == 0 && randNum >= 30 && randNum < 50){
            System.out.println("You get a health potion! Now you have " + (player.getNumPotions() + 1) + " potions\n");
            player.gainPotion();
            dungeonMemory[curX][curY] = 1;
            return 2;
        }
        else if(dungeonMemory[curX][curY] == 2 || randNum >= 50 && randNum < 100){
            System.out.println("There's an enemy!\n");
            printStats();
            enemy = new Character(false, enemiesKilled);
            dungeonMemory[curX][curY] = 2;
            return 1;
        }
        return 2;
    }

    public int fight(String action){
        int playerDamage = player.dealDamage();
        enemy.setHealth(-playerDamage);
        System.out.println("You dealt " + playerDamage + " damage!");
        if(enemy.isDead()){
            System.out.println("It's dead, you win the battle! \n");
            dungeonMemory[curX][curY] = 1;
            enemiesKilled += 1;
            return 2;
        }
        int enemyDamage = enemy.dealDamage();
        player.setHealth(-enemyDamage);
        System.out.println("You took " + enemyDamage + " damage! Now you have " + player.getHealthOverMaxHealth() + " health\n");
        if(player.isDead()){
            return 3;
        }
        return 1;
    }

    public void upgradePlayer(String upgradeChoice) {
        String upgrade = "";
        switch(upgradeChoice){
            case "potion": upgrade = "You get a health potion! Now you have " + (player.getNumPotions() + 1) + " potions";
            player.gainPotion();
            break;
            case "health": upgrade = "Your max and current health increased by 3!";
            player.upgrade(1);
            break;
            case "damage": upgrade = "Youre base damage increased by 3!";
            player.upgrade(2);
            break;
            default: break;
        }
        System.out.println(upgrade + "\n");
    }

    public boolean useHealthPotion(){
        int numPotions = player.getNumPotions();
        System.out.print("You had " + numPotions + " potions");
        if(numPotions == 0){
            System.out.println(", so you had none to use!\n");
            return false;
        }
        else{
            System.out.println(". You gained " + player.usePotion() + " health");
            System.out.println("now you have " + (numPotions - 1) + " potions and " + player.getHealthOverMaxHealth() + " health\n");
            return true;
        }
    }

    public boolean winCheck(){
        for(int i = 0; i < dungeon.length; ++i){
            for(int j = 0; j < dungeon[i].length; ++j){
                if(dungeonMemory[i][j] != 1) return false; 
            }
        }
        return true;
    }

    public void printStats(){
        System.out.println("Your health: " + player.getHealthOverMaxHealth());
        System.out.println("Your damage output: " + player.getBaseDamage() + " + (0 to 3) ");
        System.out.println("Potions in inventory: " + player.getNumPotions());
    }

}
