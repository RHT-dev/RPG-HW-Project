import java.util.Scanner;

public class QuestGiver {
    private final String name;
    private final Quest quest;
    private final Scanner scanner = new Scanner(System.in);

    public QuestGiver(String name, Quest quest) {
        this.name = name;
        this.quest = quest;
    }

    public void interact(Player player) {
        System.out.println("\nВы встретили NPC: " + name);
        if (quest.getStatus() == Quest.Status.NOT_TAKEN) {
            System.out.println("\"" + quest.getTitle() + "\": " + quest.getDescription());
            System.out.println("Взять квест? (1 - да, 0 - нет)");
            int choice = scanner.nextInt();
            if (choice == 1) {
                quest.setStatus(Quest.Status.IN_PROGRESS);
                player.acceptQuest(quest);
                System.out.println("Квест взят!");
            } else {
                System.out.println("Может, в другой раз...");
            }
        } else if (quest.getStatus() == Quest.Status.IN_PROGRESS && quest.isCompleted()) {
            System.out.println("Вы выполнили квест! Получите награду.");
            player.addExperience(quest.getExpReward());
            player.addGold(quest.getGoldReward());
            quest.setStatus(Quest.Status.COMPLETED);
            System.out.println("Квест завершён!");
            // Для повторяемых квестов — сбрасываем статус
            System.out.println("Хотите взять этот квест снова? (1 - да, 0 - нет)");
            int again = scanner.nextInt();
            if (again == 1) {
                quest.setStatus(Quest.Status.IN_PROGRESS);
                quest.complete(); // сразу отмечаем как невыполненный
                player.acceptQuest(quest);
                System.out.println("Квест взят снова!");
            }
        } else if (quest.getStatus() == Quest.Status.COMPLETED) {
            System.out.println("Спасибо за помощь! Квест уже выполнен.");
            // Для повторяемых квестов — можно взять снова
            System.out.println("Хотите взять этот квест снова? (1 - да, 0 - нет)");
            int again = scanner.nextInt();
            if (again == 1) {
                quest.setStatus(Quest.Status.IN_PROGRESS);
                quest.complete();
                player.acceptQuest(quest);
                System.out.println("Квест взят снова!");
            }
        } else {
            System.out.println("Выполните задание: " + quest.getDescription());
        }
    }
} 