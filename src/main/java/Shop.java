import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    private List<Item> stock = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public Shop() {
        // Ассортимент формируется в visit(Player player)
    }

    public void visit(Player player) {
        stock.clear();
        int maxHealth = player.health;
        int maxMana = player.mana;
        int maxStamina = player.stamina;
        // Оружие и экипировка по классу
        switch (player.getJobClass()) {
            case JobClass.MAGE -> {
                stock.add(new Weapon("Посох ученика", 70, 6, "Увеличивает урон магических атак на 6."));
                stock.add(new Weapon("Волшебная шляпа", 55, 0, "Увеличивает максимальную ману (пассивно, не реализовано)."));
                stock.add(new Potion("Зелье маны", 36, (int)(maxMana * 0.4), Potion.PotionType.MANA, maxMana, "Восстанавливает 40% маны."));
            }
            case JobClass.WARRIOR -> {
                stock.add(new Weapon("Стальной меч", 80, 8, "Увеличивает урон ближнего боя на 8."));
                stock.add(new Weapon("Щит", 60, 2, "Уменьшает получаемый урон (пассивно, не реализовано)."));
                stock.add(new Potion("Зелье выносливости", 30, (int)(maxStamina * 0.4), Potion.PotionType.STAMINA, maxStamina, "Восстанавливает 40% выносливости."));
            }
            case JobClass.ROGUE -> {
                stock.add(new Weapon("Кинжал", 56, 5, "Увеличивает урон от критических атак на 5."));
                stock.add(new Weapon("Кожаная броня", 44, 0, "Повышает ловкость (пассивно, не реализовано)."));
                stock.add(new Potion("Зелье выносливости", 30, (int)(maxStamina * 0.4), Potion.PotionType.STAMINA, maxStamina, "Восстанавливает 40% выносливости."));
            }
        }
        // Зелья здоровья доступны всем
        stock.add(new Potion("Зелье здоровья", 40, (int)(maxHealth * 0.4), Potion.PotionType.HEALTH, maxHealth, "Восстанавливает 40% здоровья."));
        stock.add(new Potion("Большое зелье здоровья", 70, (int)(maxHealth * 0.7), Potion.PotionType.HEALTH, maxHealth, "Восстанавливает 70% здоровья."));
        System.out.println("\n🧙‍♂️ Торговец приветствует вас!");
        while (true) {
            System.out.println("Ваше золото: " + player.gold);
            System.out.println("Что хотите купить? (0 - выйти)");

            for (int i = 0; i < stock.size(); i++) {
                Item item = stock.get(i);
                System.out.printf("%d. %s (%d золото) — %s%n", i + 1, item.getName(), item.getPrice(), item.getDescription());
            }

            int choice = scanner.nextInt();
            if (choice == 0) break;

            if (choice >= 1 && choice <= stock.size()) {
                Item selected = stock.get(choice - 1);
                if (player.gold >= selected.getPrice()) {
                    player.gold -= selected.getPrice();
                    player.inventory.addItem(selected);
                    System.out.println("Покупка успешна! " + selected.getDescription());
                } else {
                    System.out.println("Недостаточно золота.");
                }
            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }
}
