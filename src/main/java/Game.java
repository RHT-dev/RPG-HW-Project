import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    public Player createPlayer() {
        System.out.print("Введите имя персонажа: ");
        String name = scanner.nextLine();

        String[] raceNames = {"Человек", "Эльф", "Дварф"};
        System.out.println("\nВыберите расу:");
        for (int i = 0; i < Race.values().length; i++) {
            String desc = switch (Race.values()[i]) {
                case HUMAN -> "Люди — универсальны, быстрее набирают опыт.";
                case ELF -> "Эльфы — владеют магией, больше маны, ловкие и грациозные.";
                case DWARF -> "Дварфы — выносливы, обладают большим запасом сил и стойкости.";
            };
            System.out.printf("%d. %s — %s\n", i + 1, raceNames[i], desc);
        }
        System.out.println("\n(Совет: Человек — для сбалансированной игры, Эльф — для магов, Дварф — для воинов и выживания)");
        int raceChoice = scanner.nextInt();
        Race race = Race.values()[raceChoice - 1];

        String[] classNames = {"Воин", "Маг", "Разбойник"};
        System.out.println("\nВыберите специализацию (класс):");
        for (int i = 0; i < JobClass.values().length; i++) {
            String desc = switch (JobClass.values()[i]) {
                case WARRIOR -> "Воин — мастер ближнего боя, много здоровья и силы. Особые навыки: оглушение, защита, мощные удары.";
                case MAGE -> "Маг — владеет разрушительной магией, много маны, слаб в ближнем бою. Особые навыки: огонь, лед, лечение.";
                case ROGUE -> "Разбойник — ловкий и быстрый, наносит критические удары, может уклоняться. Особые навыки: яд, уворот, скрытность.";
            };
            System.out.printf("%d. %s — %s\n", i + 1, classNames[i], desc);
        }
        System.out.println("\n(Совет: Воин — для прямых боёв, Маг — для тактики и контроля, Разбойник — для любителей скорости и критов)");
        int classChoice = scanner.nextInt();
        JobClass jobClass = JobClass.values()[classChoice - 1];

        System.out.println("\nМаксимальный уровень персонажа — 10. Достигнув его, вы станете настоящей легендой!\n");
        System.out.println("\nРекомендуем начать с деревни: там можно отдохнуть, поговорить с NPC и взять первые квесты. Не спешите в опасные локации — подготовьтесь, изучите мир, соберите снаряжение!\n");
        return new Player(name, race, jobClass);
    }

    public Enemy createRandomEnemy() {
        String[] types = {"ORC", "GHOST", "BANDIT"};
        int index = (int)(Math.random() * types.length);
        return new Enemy(types[index]);
    }

    public void start() {
        System.out.println("\n=== СЮЖЕТ ===");
        System.out.println("Вы — потомок древних героев Эльдории. Ваша деревня страдает от нашествия монстров, а в легендах говорится о пробуждении Древнего Дракона. Только истинный герой сможет спасти мир.\n");
        System.out.println("Староста деревни просит вас проявить храбрость, защитить родные земли и узнать, что пробудило зло. Ваш путь начинается с простых заданий, но приведёт к великой битве за судьбу Эльдории!\n");
        Player player = createPlayer();
        WorldMap world = new WorldMap();
        world.travel(player);
    }
}