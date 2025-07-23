public class FreezeEffect implements StatusEffect {
    private int duration = 2;

    @Override
    public void apply(PlayableCharacter target) {
        System.out.println(target.getName() + " обморожен — снижены действия!");
        target.spendActionPoint(); // Забираем одно действие
    }

    @Override public void reduceDuration() { duration--; }
    @Override public boolean isExpired() { return duration <= 0; }
    @Override public String getName() { return "Обморожение"; }
}
