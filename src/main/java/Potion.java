public class Potion extends Item {
    private int healAmount;

    public enum PotionType { HEALTH, MANA, STAMINA }
    private PotionType type;
    private int maxValue;

    public Potion(String name, int price, int healAmount, PotionType type, int maxValue, String description) {
        super(name, price, description);
        this.healAmount = healAmount;
        this.type = type;
        this.maxValue = maxValue;
    }

    @Override
    public void use(Player player) {
        int restore = Math.min(healAmount, maxValue);
        switch (type) {
            case HEALTH -> {
                player.health = Math.min(player.health + restore, maxValue);
                System.out.println("Вы выпили зелье: " + name + " (+ " + restore + " HP)");
            }
            case MANA -> {
                player.mana = Math.min(player.mana + restore, maxValue);
                System.out.println("Вы выпили зелье: " + name + " (+ " + restore + " маны)");
            }
            case STAMINA -> {
                player.stamina = Math.min(player.stamina + restore, maxValue);
                System.out.println("Вы выпили зелье: " + name + " (+ " + restore + " выносливости)");
            }
        }
    }
}
