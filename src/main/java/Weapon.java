public class Weapon extends Item {
    private int bonusDamage;

    public Weapon(String name, int price, int bonusDamage, String description) {
        super(name, price, description);
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void use(Player player) {
        System.out.println("Вы экипировали: " + name + ". Урон +" + bonusDamage);
        player.strength += bonusDamage;
        // Пассивные эффекты для особых предметов
        if (name.equals("Волшебная шляпа")) {
            player.addManaBonus(30);
            player.mana = player.getCurrentMaxMana();
            System.out.println("✨ Ваша максимальная мана увеличена на 30!");
        }
        if (name.equals("Щит")) {
            player.defenseBonus = 5;
            System.out.println("🛡️ Теперь вы получаете на 5 меньше урона!");
        }
        if (name.equals("Кожаная броня")) {
            player.agility += 3;
            System.out.println("🦺 Ваша ловкость увеличена на 3!");
        }
    }

    public int getBonusDamage() {
        return bonusDamage;
    }
}
