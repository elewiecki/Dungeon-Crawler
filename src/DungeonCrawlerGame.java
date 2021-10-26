import java.util.Scanner;
import java.util.Random;

public class DungeonCrawlerGame{
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        System.out.print("Difficulty: How big do you want the dungeon to be (3x4 for Easy, 4x5 for Medium, 5X6 for hard)\nEnter length and width: ");
        int length = scan.nextInt();
        int width = scan.nextInt();
        
        System.out.print("Character creator: Type a symbol to represent you: ");
        String playerX = scan.next();

        System.out.println("\nTiles will either have an enemy to fight, give you a health potion, or be empty.");
        System.out.println("Cleared tiles are marked by a *");
        System.out.println("You cannot use a health potion in combat.");
        System.out.println("Every two kills will give you an upgrade.");
        System.out.println("Type the commands instructed to perform an action. Type \"end\" while not in combat to end the game");
        System.out.println("The goal of the game is to clear every tile. Clear a tile by walking over and killing any enemy on it\n");

        Dungeon dungeon = new Dungeon(length, width, playerX);
        Random rand = new Random();
        dungeon.createDungeon();
        dungeon.printDungeon();

        boolean keepGoing = true;
        String direction;
        String action;
        boolean hasMoved = false;
        boolean ran = false;

        String upgrade;

        int killsToUpgrade = 2;

        int playerState = 2; //1 is fighting, 2 is neutral, 3 is dead

        //begin game loop
        while(keepGoing){
            dungeon.printStats();
            //get action input from player
            System.out.print("Type \"up\", \"down\", \"left\", or \"right\", or \"potion\": ");
            direction = scan.next();
            System.out.print("\n");
            if(direction.toLowerCase().compareTo("end") == 0){
                keepGoing = false;
            }
            else if(direction.toLowerCase().compareTo("potion") == 0){
                dungeon.useHealthPotion();
                dungeon.printDungeon();    
            }
            else if(dungeon.move(direction, false)){
                dungeon.printDungeon();
                playerState = dungeon.encounter();
            }
            //in fight
            while(playerState == 1){
                ran = false;
                System.out.print("Type to \"attack\", or \"run\" to run away: ");
                action = scan.next();
                System.out.print("\n");
                if(action.toLowerCase().compareTo("attack") == 0){
                    playerState = dungeon.fight(action);
                }
                else if(action.toLowerCase().compareTo("run") == 0){
                    System.out.println("You ran away, moving to a random adjacent tile\n");
                    int randNum;
                    hasMoved = false;
                    while(!hasMoved){
                        //pick a random direction to move in
                        randNum = rand.nextInt(4);
                        switch(randNum){
                            case 0: if(dungeon.move("up", true)) hasMoved = true;
                            break;                       
                            case 1: if(dungeon.move("down", true)) hasMoved = true;
                            break;
                            case 2: if(dungeon.move("left", true)) hasMoved = true;
                            break;
                            case 3: if(dungeon.move("right", true)) hasMoved = true;
                            break;  
                            default: hasMoved = false;
                            break;
                        }
                    }
                    dungeon.printDungeon();
                    ran = true;
                    playerState = dungeon.encounter();
                //if you win a fight
                }
                if(playerState == 2 && !ran){
                    dungeon.printDungeon();
                    killsToUpgrade -= 1;
                    if(killsToUpgrade == 0){
                        do { System.out.print("Would you like more health, damage, or a potion? (type \"health\", \"damage\", or \"potion\"): ");
                             upgrade = scan.next();
                        } while(upgrade.toLowerCase().compareTo("health") != 0 && upgrade.toLowerCase().compareTo("damage") !=0 && upgrade.toLowerCase().compareTo("potion") !=0);
                        System.out.print("\n");
                        dungeon.upgradePlayer(upgrade);
                        killsToUpgrade = 2;
                    }
                    else System.out.println("Kill one more enemy to get an upgrade\n");
                }
            }
            //check if game should end
            if(playerState == 3){
                System.out.print("You're dead! You lose the game!");
                keepGoing = false;
            }
            if(dungeon.winCheck()){
                System.out.print("You cleared the map! You win!");
                keepGoing = false;
            }
        }
    }
}
