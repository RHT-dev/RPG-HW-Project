import java.util.*;

public class SkillFactory {
    public static List<Skill> getSkillsForClass(JobClass job) {
        List<Skill> skills = new ArrayList<>();

        switch (job) {
            case WARRIOR:
                skills.add(new Skill(
                    "Сокрушающий удар",
                    "Мощный удар с шансом оглушить врага. Тратит 50 выносливости.",
                    50, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        int damage = (int)(u.strength * 2.2 + u.getLevel() * 2);
                        t.takeDamage(damage);
                        if (new Random().nextInt(100) < 30) {
                            t.addEffect(new StunEffect());
                        }
                        ((Player)u).stamina -= 50;
                    }
                ));
                skills.add(new Skill(
                    "Боевой клич",
                    "Повышает силу на 2 хода. Тратит 40 выносливости.",
                    40, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        System.out.println(u.getName() + " издает боевой клич! Сила увеличена на 5 на 2 хода.");
                        ((Player)u).strength += 5;
                        ((Player)u).stamina -= 40;
                    }
                ));
                skills.add(new Skill(
                    "Защитная стойка",
                    "Снижает получаемый урон на 2 хода. Тратит 30 выносливости.",
                    30, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        u.defend();
                        ((Player)u).stamina -= 30;
                    }
                ));
                break;

            case MAGE:
                skills.add(new Skill(
                    "Огненный шар",
                    "Наносит урон и поджигает врага. Тратит 55 маны.",
                    55, Skill.ResourceType.MANA,
                    (u, t) -> {
                        int dmg = (int)(u.intelligence * 2.5 + u.getLevel() * 2);
                        t.takeDamage(dmg);
                        t.addEffect(new BurnEffect());
                        ((Player)u).mana -= 55;
                    }
                ));
                skills.add(new Skill(
                    "Ледяная стрела",
                    "Замедляет и может заморозить врага. Тратит 45 маны.",
                    45, Skill.ResourceType.MANA,
                    (u, t) -> {
                        int dmg = (int)(u.intelligence * 2.1 + u.getLevel() * 1.5);
                        t.takeDamage(dmg);
                        t.addEffect(new FreezeEffect());
                        ((Player)u).mana -= 45;
                    }
                ));
                skills.add(new Skill(
                    "Молния",
                    "Мощный урон и шанс оглушить. Тратит 60 маны.",
                    60, Skill.ResourceType.MANA,
                    (u, t) -> {
                        int dmg = (int)(u.intelligence * 2.8 + u.getLevel() * 2.5);
                        t.takeDamage(dmg);
                        if (new Random().nextInt(100) < 40) {
                            t.addEffect(new StunEffect());
                        }
                        ((Player)u).mana -= 60;
                    }
                ));
                skills.add(new Skill(
                    "Исцеление",
                    "Восстанавливает здоровье. Тратит 40 маны.",
                    40, Skill.ResourceType.MANA,
                    (u, t) -> {
                        int heal = (int)(u.intelligence * 2.2 + u.getLevel() * 2);
                        u.health = Math.min(u.health + heal, 100 + u.getLevel() * 10);
                        System.out.println(u.getName() + " исцеляется на " + heal);
                        ((Player)u).mana -= 40;
                    }
                ));
                break;

            case ROGUE:
                skills.add(new Skill(
                    "Удар из тени",
                    "Критический урон. Тратит 40 выносливости.",
                    40, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        int dmg = (int)(u.agility * 3.2 + u.getLevel() * 2);
                        t.takeDamage(dmg);
                        ((Player)u).stamina -= 40;
                    }
                ));
                skills.add(new Skill(
                    "Жестокие удары",
                    "На 1 ход увеличивает урон всех ваших атак на 40%. Тратит 35 выносливости.",
                    35, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        System.out.println(u.getName() + " впадает в ярость! Все атаки на этом ходу наносят на 40% больше урона.");
                        ((Player)u).strength = (int)(((Player)u).strength * 1.4);
                        ((Player)u).stamina -= 35;
                        // Можно реализовать временный эффект через отдельный статус
                    }
                ));
                skills.add(new Skill(
                    "Ядовитый клинок",
                    "Наносит урон и накладывает кровотечение. Тратит 32 выносливости.",
                    32, Skill.ResourceType.STAMINA,
                    (u, t) -> {
                        int dmg = (int)(u.agility * 2.3 + u.getLevel() * 1.5);
                        t.takeDamage(dmg);
                        t.addEffect(new BleedEffect());
                        ((Player)u).stamina -= 32;
                    }
                ));
                break;
        }

        return skills;
    }
}
