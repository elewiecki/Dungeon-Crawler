import java.util.Random;


public class Entity {

    int health;
    Random rand;
    int maxHealth;
    int baseDamage;

    public Entity(int maxHealth, int baseDamage){
        rand = new Random();
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.baseDamage = baseDamage;
    }
    
    public void setHealth(int change){
        health = Math.max(health + change, 0);
    }

    public int getHealth(){
        return health;
    };

    public boolean isDead(){
        return health <= 0;
    }

    //needs to be overriden for differnt entities
    public int dealDamage(){
        return 0;
    }


    public String getHealthOverMaxHealth(){
        return (String.valueOf(health) + "/" + String.valueOf(maxHealth));
    }

    public int getBaseDamage(){
        return baseDamage;
    }

}
