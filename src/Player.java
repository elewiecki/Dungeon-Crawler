public class Player extends Entity{

    int numPotions;

    public Player(int maxHealth, int baseDamage){
        super(maxHealth, baseDamage);
        numPotions = 0;
    }

    public void upgrade(int up){
        //1 increase health by 3, 2 increase damage by 3
        if(up == 1){
             maxHealth += 3;
             health += 3;
        }
        if(up == 2){
            baseDamage += 2;
        }
    }

    public void gainPotion(){
        numPotions += 1;
    }

    public int getNumPotions(){
        return numPotions;
    }

    public int usePotion(){
        int healthGained = rand.nextInt(4) + 3;
        health = Math.min(health + healthGained, maxHealth);
        numPotions -= 1;
        return healthGained;
    }

    @Override
    public int dealDamage(){
        return rand.nextInt(4) + baseDamage;
    }
    
}
