import java.util.*;

public abstract class PlayableCharacter {
    protected String name;
    protected int health;
    protected int mana;
    protected int stamina;
    protected int strength;
    protected int agility;
    protected int intelligence;
    protected int actionPoints = 2;
    protected int maxActionPoints = 2;
    protected boolean defending = false;

    protected List<StatusEffect> effects = new ArrayList<>();

    public PlayableCharacter(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        if (defending) {
            amount /= 2;
            System.out.println(name + " защищается! Урон снижен до " + amount);
            defending = false;
        }
        // Пассивная защита от щита
        if (this instanceof Player) {
            Player p = (Player)this;
            if (p.defenseBonus > 0) {
                amount = Math.max(0, amount - p.defenseBonus);
                System.out.println(name + " блокирует часть урона щитом! (−" + p.defenseBonus + ")");
            }
        }
        health -= Math.max(amount, 0);
        System.out.println(name + " получает " + amount + " урона. (Осталось HP: " + health + ")");
    }

    public void addEffect(StatusEffect effect) {
        effects.add(effect);
        System.out.println(name + " под эффектом: " + effect.getName());
    }

    public void processEffects() {
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect effect = it.next();
            effect.apply(this);
            effect.reduceDuration();
            if (effect.isExpired()) {
                System.out.println(name + " больше не под эффектом: " + effect.getName());
                it.remove();
            }
        }
    }

    public void resetActionPoints() {
        actionPoints = maxActionPoints;
    }

    public boolean canAct() {
        return actionPoints > 0;
    }

    public void spendActionPoint() {
        if (actionPoints > 0) actionPoints--;
    }

    public void defend() {
        defending = true;
    }

    public boolean isDefending() {
        return defending;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return 1;
    }

    public abstract void attack(PlayableCharacter target);
}
