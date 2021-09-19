import java.util.Random;

public class Character {
    boolean isPlayer;
    int health;
    Random rand;
    int numPotions;
    int maxHealth;
    int baseDamage;
    int enemiesKilled;

    public Character(boolean isPlayer, int enemiesKilled){
        rand = new Random();
        this.isPlayer = isPlayer;
        this.enemiesKilled = enemiesKilled;
        if(isPlayer) maxHealth = 10;
        else maxHealth = rand.nextInt(2) + 5 + enemiesKilled;
        health = maxHealth;
        numPotions = 0;
        baseDamage = 2;
    }

    public void setHealth(int change){
        health += change;
        if(health < 0) health = 0;
    }

    public int getHealth(){
        return health;
    }

    public int dealDamage(){
        if(isPlayer) return rand.nextInt(4) + baseDamage;
        else return rand.nextInt(6) + (enemiesKilled/2);
    }

    public boolean isDead(){
        return health <= 0;
    }

    public void gainPotion(){
        numPotions += 1;
    }

    public int getNumPotions(){
        return numPotions;
    }

    public int usePotion(){
        int healthGained = rand.nextInt(4) + 3;
        health = health + healthGained;
        if(health > maxHealth) health = maxHealth;
        numPotions -= 1;
        return healthGained;
    }

    public String getHealthOverMaxHealth(){
        return (String.valueOf(health) + "/" + String.valueOf(maxHealth));
    }

    public int getBaseDamage(){
        return baseDamage;
    }

    public void upgrade(int up){
        //1 increase health by 3, 2 increase damage by 3
        if(up == 1){
             maxHealth += 3;
             health += 3;
        }
        if(up == 2){
            baseDamage += 3;
        }
    }

}

