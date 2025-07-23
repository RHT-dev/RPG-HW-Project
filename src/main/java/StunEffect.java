public class StunEffect implements StatusEffect {
    private int duration = 1;

    @Override
    public void apply(PlayableCharacter target) {
        System.out.println(target.getName() + " оглушен и пропускает ход!");
        target.spendActionPoint(); // Забираем ход
    }

    @Override public void reduceDuration() { duration--; }
    @Override public boolean isExpired() { return duration <= 0; }
    @Override public String getName() { return "Оглушение"; }
}
