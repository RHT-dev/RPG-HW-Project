import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Item> items = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void addItem(Item item) {
        items.add(item);
        System.out.println("🎁 Вы получили предмет: " + item.getName());
    }

    public void displayInventory(Player player) {
        if (items.isEmpty()) {
            System.out.println("🎒 Инвентарь пуст.");
            return;
        }

        System.out.println("🎒 Ваш инвентарь:");
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            System.out.printf("%d. %s (цена: %d)%n", i + 1, it.getName(), it.getPrice());
        }

        System.out.println("Хотите использовать предмет? (1 - да, 0 - нет)");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.print("Выберите предмет для использования: ");
            int idx = scanner.nextInt() - 1;
            if (idx >= 0 && idx < items.size()) {
                System.out.println("🎁 Использован предмет: " + items.get(idx).getName());
                items.get(idx).use(player);
                items.remove(idx);
            } else {
                System.out.println("❗ Неверный выбор.");
            }
        }
    }
}
