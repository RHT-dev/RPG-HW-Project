import java.util.*;

public class Player extends PlayableCharacter {
    private Race race;
    private JobClass jobClass;
    private List<Skill> skills = new ArrayList<>();
    private int experience = 0;
    private int level = 1;
    private int expToNextLevel = 100;
    public Inventory inventory = new Inventory();
    public int gold = 50;
    private List<Quest> quests = new ArrayList<>();
    public int defenseBonus = 0;
    private List<String> blessings = new ArrayList<>();
    public int manaBonus = 0;
    public int healthBonus = 0;
    public int staminaBonus = 0;


    public Player(String name, Race race, JobClass jobClass) {
        super(name);
        this.race = race;
        this.jobClass = jobClass;
        this.skills = SkillFactory.getSkillsForClass(jobClass);
        this.maxActionPoints = 1;
        this.actionPoints = 1;
        initializeStats();
    }

    private void initializeStats() {
        switch (jobClass) {
            case WARRIOR:
                health = 140 ;
                stamina = 100;
                strength = 15;
                agility = 8;
                intelligence = 5;
                break;
            case MAGE:
                health = 80;
                mana = 120;
                strength = 5;
                agility = 7;
                intelligence = 18;
                break;
            case ROGUE:
                health = 100;
                stamina = 90;
                strength = 10;
                agility = 15;
                intelligence = 7;
                break;
        }

        switch (race) {
            case HUMAN:
                
                break;
            case ELF:
                mana *= 1.3;
                break;
            case DWARF:
                stamina += 30;
                break;
        }
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public JobClass getJobClass() {
        return jobClass;
    }

    @Override
    public void attack(PlayableCharacter target) {
        int baseDamage = (jobClass == JobClass.MAGE) ? intelligence : strength;
        boolean isCrit = new Random().nextInt(100) < 15;
        if (isCrit) {
            baseDamage *= 1.5;
            System.out.println(name + " наносит КРИТИЧЕСКИЙ удар!");
        }
        System.out.println(name + " атакует " + target.getName());
        target.takeDamage(baseDamage);
        spendActionPoint();
    }

    public void addExperience(int amount) {
        if (level >= 10) {
            System.out.println("📈 Вы достигли максимального уровня 10! Опыт больше не начисляется.");
            return;
        }
        int bonus = 0;
        if (race == Race.HUMAN) {
            bonus = (int)(amount * 0.2);
        }
        experience += amount + bonus;
        if (bonus > 0) {
            System.out.println("🏅 (Бонус расы: Человек) +" + bonus + " опыта!");
        }
        System.out.println("🏆 Вы получили опыт: " + (amount + bonus) + " (Всего: " + experience + ")");
        while (experience >= expToNextLevel && level < 10) {
            levelUp();
        }
        if (level == 10) {
            experience = expToNextLevel;
            System.out.println("📈 Вы достигли максимального уровня 10! Дальнейший опыт не начисляется.");
        }
    }

    public void addGold(int amount) {
        gold += amount;
        System.out.println("🪙 Вы получили золото: " + amount + " (Всего: " + gold + ")");
    }

    private void levelUp() {
        experience -= expToNextLevel;
        level++;
        expToNextLevel = (int)(expToNextLevel * 1.5);
        health = getCurrentMaxHealth();
        mana = getCurrentMaxMana();
        stamina = getCurrentMaxStamina();
        // Очки действий по уровню
        if (level < 3) {
            maxActionPoints = 1;
        } else if (level < 5) {
            maxActionPoints = 2;
        } else {
            maxActionPoints = 3;
        }
        if (level % 5 == 0) {
            switch (jobClass) {
                case WARRIOR:
                    strength += 5;
                    break;
                case MAGE:
                    intelligence += 5;
                    break;
                case ROGUE:
                    agility += 5;
                    break;
            }
            System.out.println("\u2728 Уникальный уровень! Основная характеристика увеличена.");
        } else {
            strength += 1;
            intelligence += 1;
            agility += 1;
        }
        System.out.println("\u2B50 Поздравляем! Вы достигли уровня " + level + "!");
        System.out.println("Ваши основные характеристики увеличились!");
        System.out.println("Теперь у вас " + maxActionPoints + " очк." + (maxActionPoints == 1 ? "о" : "а") + " действий за ход!");
    }

    public int getLevel() {
        return level;
    }
    public int getExperience() {
        return experience;
    }
    public int getExpToNextLevel() {
        return expToNextLevel;
    }

    public int getMaxHealth() {
        switch (jobClass) {
            case WARRIOR: return 140 + (level - 1) * 15;
            case MAGE: return 80 + (level - 1) * 10;
            case ROGUE: return 100 + (level - 1) * 12;
            default: return 100 + (level - 1) * 10;
        }
    }
    public int getMaxMana() {
        switch (jobClass) {
            case MAGE: return 120 + (level - 1) * 12;
            default: return 100 + (level - 1) * 5;
        }
    }
    public int getMaxStamina() {
        switch (jobClass) {
            case WARRIOR: return 100 + (level - 1) * 10;
            case ROGUE: return 90 + (level - 1) * 8;
            default: return 80 + (level - 1) * 5;
        }
    }

    public int getCurrentMaxHealth() {
        return getMaxHealth() + healthBonus;
    }
    public int getCurrentMaxMana() {
        return getMaxMana() + manaBonus;
    }
    public int getCurrentMaxStamina() {
        return getMaxStamina() + staminaBonus;
    }
    public void addManaBonus(int amount) {
        manaBonus += amount;
    }
    public void addHealthBonus(int amount) {
        healthBonus += amount;
    }
    public void addStaminaBonus(int amount) {
        staminaBonus += amount;
    }

    public void acceptQuest(Quest quest) {
        quests.add(quest);
        System.out.println("📜 Квест добавлен в журнал!");
    }
    public void markQuestCompleted(String questTitle) {
        for (Quest q : quests) {
            if (q.getTitle().equals(questTitle) && q.getStatus() == Quest.Status.IN_PROGRESS) {
                q.complete();
                System.out.println("🏅 Квест '" + questTitle + "' отмечен как выполненный!");
            }
        }
    }
    public boolean hasQuest(String questTitle) {
        for (Quest q : quests) {
            if (q.getTitle().equals(questTitle)) return true;
        }
        return false;
    }
    public boolean isQuestCompleted(String questTitle) {
        for (Quest q : quests) {
            if (q.getTitle().equals(questTitle) && q.getStatus() == Quest.Status.COMPLETED) return true;
        }
        return false;
    }

    public void addBlessing(String questName) {
        if (blessings.contains(questName)) {
            System.out.println("✨ Духи уже благословили вас за этот подвиг. Повторное благословение невозможно.");
            return;
        }
        blessings.add(questName);
        health += 10;
        System.out.println("\n🌿 Вас окутывает мягкое сияние. Духи деревни благодарят вас за помощь и даруют благословение!\n" +
                "✨ Ваше здоровье увеличено на 10 навсегда. ✨\n" +
                "Вы чувствуете прилив сил и внутреннего спокойствия, словно сама природа защищает вас.\n");
    }
}
