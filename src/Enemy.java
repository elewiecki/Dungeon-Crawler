public class Enemy extends Entity{

    int enemiesKilled;

    public Enemy(int maxHealth, int baseDamage){
        super(maxHealth, baseDamage);    
    }

    @Override
    public int dealDamage(){
        return rand.nextInt(6) + baseDamage;
    }

}

