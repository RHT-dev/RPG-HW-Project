import java.util.*;

public class Enemy extends PlayableCharacter {
    protected int expReward;
    protected int goldReward;
    protected int level = 1;

    public Enemy(String type) {
        super(type);
        switch (type.toUpperCase()) {
            case "ORC":
                this.name = "Орк";
                this.health = 100;
                this.stamina = 80;
                this.strength = 12;
                this.agility = 6;
                this.intelligence = 3;
                this.expReward = 60;
                this.goldReward = 20;
                this.level = 2;
                break;
            case "GHOST":
                this.name = "Призрак";
                this.health = 150;
                this.mana = 100;
                this.strength = 4;
                this.agility = 10;
                this.intelligence = 12;
                this.expReward = 80;
                this.goldReward = 30;
                this.level = 3;
                break;
            case "BANDIT":
                this.name = "Бандит";
                this.health = 150;
                this.stamina = 90;
                this.strength = 10;
                this.agility = 12;
                this.intelligence = 5;
                this.expReward = 70;
                this.goldReward = 25;
                this.level = 4;
                break;
            default:
                this.name = "Неизвестное существо";
                this.health = 80;
                this.strength = 8;
                this.intelligence = 5;
                this.agility = 5;
                this.expReward = 40;
                this.goldReward = 10;
                this.level = 1;
        }
    }

    @Override
    public void attack(PlayableCharacter target) {
        System.out.println(this.name + " атакует " + target.getName());
        int damage = (int)(this.strength + this.level * 2.5);
        boolean isCrit = new Random().nextInt(100) < 10;
        if (isCrit) {
            damage *= 1.5;
            System.out.println(this.name + " наносит КРИТИЧЕСКИЙ удар!");
        }
        target.takeDamage(damage);
        this.spendActionPoint();
    }

    public int getExpReward() {
        return this.expReward;
    }
    public int getGoldReward() {
        return this.goldReward;
    }
    @Override
    public int getLevel() {
        return this.level;
    }
}
