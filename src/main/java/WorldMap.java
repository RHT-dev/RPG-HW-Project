import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorldMap {
    private final List<Location> locations = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public WorldMap() {
        // Вступление
        System.out.println("\nДобро пожаловать в мир под названием Эльдориа! Ваше приключение начинается...\n");
        // Квесты для каждой локации
        Quest ruinsQuest = new Quest(
            "Тайна руин",
            "Победите призрака в руинах и принесите покой древним духам.",
            100, 50
        );
        Quest fortQuest = new Quest(
            "Честь форта",
            "Победите бандита в развалинах форта и восстановите справедливость.",
            120, 60
        );
        // Квестовый NPC в деревне
        Quest firstQuest = new Quest(
            "Помощь деревне",
            "Победите орка в лесу и вернитесь за наградой.",
            80, 30
        );
        QuestGiver villager = new QuestGiver("Староста деревни", firstQuest);
        locations.add(new Location("🏡 Деревня", "Уютная деревня с торговцем и костром. Здесь живут простые люди, пережившие нападения монстров.", player -> {
            System.out.println("\n[ЛОР] Деревня — сердце Эльдории. Здесь вы выросли, здесь ваши друзья и наставники.\n");
            System.out.println("Вокруг слышен смех детей, пахнет свежим хлебом, а над крышами вьётся дымок. Но в глазах жителей — тревога: монстры всё чаще нападают на окрестности.");
            System.out.println("Вы отдыхаете и полностью восстанавливаетесь. Ваши раны затягиваются, а душа наполняется надеждой.");
            player.health = player.getCurrentMaxHealth();
            player.mana = player.getCurrentMaxMana();
            player.stamina = player.getCurrentMaxStamina();
            // Квестовый выбор
            System.out.println("\n📜 Доступные квесты деревни:");
            Quest[] allQuests = {firstQuest, ruinsQuest, fortQuest};
            String[] questNames = {"Помощь деревне (лес)", "Тайна руин (руины)", "Честь форта (форт)"};
            for (int i = 0; i < allQuests.length; i++) {
                if (!player.hasQuest(allQuests[i].getTitle())) {
                    System.out.println((i+1) + ". " + questNames[i] + ": " + allQuests[i].getDescription());
                }
            }
            System.out.println("0. Не брать квесты");
            System.out.print("Выберите квест для принятия (или 0 для пропуска): ");
            int choice = new Scanner(System.in).nextInt();
            if (choice >= 1 && choice <= allQuests.length) {
                if (!player.hasQuest(allQuests[choice-1].getTitle())) {
                    player.acceptQuest(allQuests[choice-1]);
                } else {
                    System.out.println("Вы уже приняли этот квест.");
                }
            } else {
                System.out.println("Вы решили пока не брать новые квесты.");
            }
        }));
        locations.add(new Location("🌲 Лес", "Дикий лес. Здесь часто нападают орки. Говорят, в чаще скрыты древние руины.", player -> {
            System.out.println("\n[ЛОР] Лес — опасное место, но именно здесь вы впервые проявили храбрость, защищая деревню.\n");
            System.out.println("Высокие деревья заслоняют небо, в тени шуршат звери, а где-то вдали слышится рёв орка. В воздухе пахнет хвоей и опасностью.");
            Enemy orc = new Enemy("ORC");
            new BattleSystem().runBattle(player, orc);
            if (!player.hasQuest("Помощь деревне")) {
                player.acceptQuest(firstQuest);
            }
            if (!player.isQuestCompleted("Помощь деревне") && !orc.isAlive()) {
                player.markQuestCompleted("Помощь деревне");
                player.addBlessing("Помощь деревне");
            }
        }));
        locations.add(new Location("🏚️ Руины", "Старые руины. Слухи гласят, что там обитает призрак.", player -> {
            if (player.getLevel() < 2) {
                System.out.println("Слишком опасно! Вам нужно достичь 2 уровня, чтобы войти в руины.");
                return;
            }
            System.out.println("\n[ЛОР] Руины — остатки древней цивилизации, павшей в войне с драконами.\n");
            System.out.println("Каменные арки поросли мхом, в трещинах стен прячутся светлячки. Здесь витает древняя магия и эхо трагедии.");
            Enemy ghost = new Enemy("GHOST");
            new BattleSystem().runBattle(player, ghost);
            if (!player.hasQuest("Тайна руин")) {
                player.acceptQuest(ruinsQuest);
            }
            if (!player.isQuestCompleted("Тайна руин") && !ghost.isAlive()) {
                player.markQuestCompleted("Тайна руин");
                player.addBlessing("Тайна руин");
            }
        }));
        locations.add(new Location("🧌 Развалины форта", "Заброшенный форт, где затаился бандит.", player -> {
            if (player.getLevel() < 3) {
                System.out.println("Слишком опасно! Вам нужно достичь 3 уровня, чтобы войти в форт.");
                return;
            }
            System.out.println("\n[ЛОР] Форт когда-то защищал границы Эльдории, но пал под натиском чудовищ.\n");
            System.out.println("Поваленные стены, ржавые ворота и следы былых сражений. Здесь пахнет пеплом и опасностью, а в тени притаился бандит.");
            Enemy bandit = new Enemy("BANDIT");
            new BattleSystem().runBattle(player, bandit);
            if (!player.hasQuest("Честь форта")) {
                player.acceptQuest(fortQuest);
            }
            if (!player.isQuestCompleted("Честь форта") && !bandit.isAlive()) {
                player.markQuestCompleted("Честь форта");
                player.addBlessing("Честь форта");
            }
        }));
        locations.add(new Location("🪙 Торговец", "Бродячий торговец с редкими товарами. Он знает много историй о мире.", player -> {
            System.out.println("\n[ЛОР] Торговец рассказывает вам о древних артефактах и легендах Эльдории.\n");
            System.out.println("Его повозка украшена яркими лентами, а на прилавке сверкают диковинные товары. Торговец улыбается загадочно и шепчет: 'В каждом предмете — история, в каждом зелье — шанс на спасение.'");
            new Shop().visit(player);
        }));
        locations.add(new Location("🐲 Логово дракона", "Здесь обитает Древний Дракон. Только герой, достигший 5 уровня, может войти!", player -> {
            if (player.getLevel() < 5) {
                System.out.println("Слишком опасно! Вам нужно достичь 5 уровня, чтобы войти в логово дракона.");
                return;
            }
            System.out.println("\n[ЛОР] Легенды гласят, что Древний Дракон пробудился после падения древней цивилизации. Только истинный герой может бросить ему вызов.\n");
            System.out.println("Пещера полна золота, стены покрыты древними рунами, а в центре — исполинский силуэт дракона, чьи глаза горят огнём. Здесь решится судьба Эльдории.");
            System.out.println("Вы входите в логово Древнего Дракона... 🐲");
            Enemy boss = new BossEnemy();
            new BattleSystem().runBattle(player, boss);
            if (player.isAlive()) {
                System.out.println("\n🎉🏆 Поздравляем! Вы победили Древнего Дракона и спасли Эльдорию! 🏆🎉\n");
                System.out.println("\n=== КОНЕЦ ИГРЫ ===");
                System.out.println("\nГерой возвращается в деревню, где его встречают как спасителя. Жители празднуют победу, а легенда о вашем подвиге разносится по всему миру.\n");
                System.out.println("\n--- ТИТРЫ ---");
                System.out.println("\nСпасибо за игру! Вы прошли путь от простого жителя до великого героя, победив древнее зло и восстановив надежду в сердцах людей.\n");
                System.out.println("\n--- Разработчик: Г.Т.К ---");
                System.exit(0);
            }
        }));
    }

    public void travel(Player player) {
        while (player.isAlive()) {
            System.out.println("\n🌍 Куда вы хотите отправиться?");
            System.out.println("📈 Ваш уровень: " + player.getLevel());
            for (int i = 0; i < locations.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, locations.get(i).getName());
            }
            System.out.println("0. 🚪 Завершить игру");
            System.out.println("9. 🎒 Открыть инвентарь");

            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("👋 Вы покинули этот мир... До новых встреч!");
                return;
            }
            if (choice == 9) {
                player.inventory.displayInventory(player);
                continue;
            }
            if (choice >= 1 && choice <= locations.size()) {
                System.out.println("\n📍 Путешествие в: " + locations.get(choice - 1).getName() + "...");
                locations.get(choice - 1).enter(player);
            } else {
                System.out.println("❗ Некорректный выбор.");
            }
        }

        System.out.println("\n💀 Вы погибли. Игра окончена. Спасибо за игру! 💀");
    }
}
