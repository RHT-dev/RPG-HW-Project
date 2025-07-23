import java.util.Scanner;
import java.util.Random;

public class BattleSystem {
    private final Scanner scanner = new Scanner(System.in);

    public void runBattle(Player player, Enemy enemy) {
        System.out.println("\n══════════════════════════════════════════════════════════════════");
        System.out.println("⚔️  Битва начинается: " + player.getName() + " против " + enemy.getName() + "!");
        System.out.println("══════════════════════════════════════════════════════════════════\n");

        int manaBefore = player.mana;
        int staminaBefore = player.stamina;

        while (player.isAlive() && enemy.isAlive()) {
            player.processEffects();
            enemy.processEffects();

            player.resetActionPoints();
            enemy.resetActionPoints();

            boolean playerFirst = player.getJobClass() == JobClass.ROGUE || Math.random() < 0.5;

            if (playerFirst) {
                handlePlayerTurn(player, enemy);
                if (enemy.isAlive()) handleEnemyTurn(enemy, player);
            } else {
                handleEnemyTurn(enemy, player);
                if (player.isAlive()) handlePlayerTurn(player, enemy);
            }

            System.out.println("\n──────────────────────────────────────────────────────────────────\n");
        }

        // Восстановление ресурсов после боя
        player.mana = Math.max(player.mana, manaBefore);
        player.stamina = Math.max(player.stamina, staminaBefore);
        if (player.isAlive()) {
            System.out.println("\n🏆 🎉 ПОБЕДА! " + player.getName() + " одолел " + enemy.getName() + "! 🎉 🏆");
            player.addExperience(enemy.getExpReward());
            player.addGold(enemy.getGoldReward());
            System.out.println("💧 Ваши ресурсы восстановлены: Мана = " + player.mana + ", Выносливость = " + player.stamina);
        } else {
            System.out.println("\n💀 Поражение! Вы были побеждены... 💀");
        }
        System.out.println("══════════════════════════════════════════════════════════════════\n");
    }

    private void handlePlayerTurn(Player player, Enemy enemy) {
        while (player.canAct()) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("🧑‍🎤 Ваш ход. Очки действий: " + player.actionPoints);
            System.out.println("👤 Игрок: " + player.getName() + " | Уровень: " + player.getLevel() + " | ❤️ HP: " + player.health + "/" + player.getMaxHealth() + " | 🔮 Мана: " + player.mana + "/" + player.getMaxMana() + " | 💪 Выносливость: " + player.stamina + "/" + player.getMaxStamina() + " | 🪙 Золото: " + player.gold);
            System.out.println("   Сила: " + player.strength + " | Ловкость: " + player.agility + " | Интеллект: " + player.intelligence);
            System.out.println("👹 Враг: " + enemy.getName() + " | Уровень: " + enemy.getLevel() + " | ❤️ HP: " + enemy.health + "/? ");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("1️⃣  Обычная атака");
            System.out.println("2️⃣  Мощная атака (больше урона, но шанс промаха)");
            System.out.println("3️⃣  Использовать навык");
            System.out.println("4️⃣  Защита 🛡️");
            System.out.println("5️⃣  Открыть инвентарь 🎒 (использовать зелье)");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> player.attack(enemy);
                case 2 -> powerfulAttack(player, enemy);
                case 3 -> handleSkillUse(player, enemy);
                case 4 -> {
                    System.out.println("🛡️  Вы встали в защитную стойку!");
                    player.defend();
                    player.spendActionPoint();
                }
                case 5 -> player.inventory.displayInventory(player);
                default -> System.out.println("❗ Некорректный ввод. Попробуйте снова.");
            }

            if (!enemy.isAlive()) return;
        }
    }

    private void powerfulAttack(Player player, Enemy enemy) {
        if (Math.random() < 0.3) {
            System.out.println("Вы размахиваетесь для мощной атаки, но враг ловко уклоняется!");
        } else {
            int damage = (player.getJobClass() == JobClass.MAGE ? player.intelligence : player.strength) * 2;
            boolean isCrit = Math.random() < 0.25;
            if (isCrit) {
                damage *= 2;
                System.out.println("⚡ Молниеносный крит! Ваш удар сотрясает поле битвы!");
            }
            System.out.println(player.getName() + " с силой обрушивает мощную атаку на " + enemy.getName() + "!");
            enemy.takeDamage(damage);
        }
        player.spendActionPoint();
    }

    private void handleSkillUse(Player player, Enemy enemy) {
        var skills = player.getSkills();
        System.out.println("\n✨ Доступные навыки:");
        for (int i = 0; i < skills.size(); i++) {
            Skill s = skills.get(i);
            System.out.printf("%d. %s — %s (💰 %d %s)%n", i + 1, s.getName(), s.getDescription(), s.getCost(),
                s.getResourceType() == Skill.ResourceType.MANA ? "мана" : s.getResourceType() == Skill.ResourceType.STAMINA ? "выносливость" : "");
        }
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < skills.size()) {
            Skill skill = skills.get(index);
            if (!skill.canUse(player)) {
                System.out.println("❌ Недостаточно ресурсов для использования навыка!");
                return;
            }
            System.out.println("✨ " + player.getName() + " использует навык: " + skill.getName() + "!");
            skill.use(player, enemy);
            player.spendActionPoint();
        } else {
            System.out.println("❗ Неверный выбор навыка");
        }
    }

    private void handleEnemyTurn(Enemy enemy, Player player) {
        System.out.println("\n😈 " + enemy.getName() + " готовится к атаке...");
        if (enemy.canAct()) {
            if (Math.random() < 0.2) {
                System.out.println("🔥 " + enemy.getName() + " использует особую атаку! Пламя или тьма окутывает поле боя!");
                int damage = enemy.strength * 2;
                player.takeDamage(damage);
            } else {
                if (Math.random() < (0.1 + player.agility * 0.01)) {
                    System.out.println("💨 " + player.getName() + " ловко уклоняется, оставляя врага в замешательстве!");
                } else {
                    String[] phrases = {
                        "наносит удар с размаху!",
                        "пытается пробить вашу защиту!",
                        "атакует с яростью!",
                        "бросается в атаку!"
                    };
                    String phrase = phrases[new Random().nextInt(phrases.length)];
                    System.out.println("😈 " + enemy.getName() + " " + phrase);
                    enemy.attack(player);
                }
            }
        } else {
            System.out.println("😵 " + enemy.getName() + " запнулся и пропускает ход!");
        }
    }
}
