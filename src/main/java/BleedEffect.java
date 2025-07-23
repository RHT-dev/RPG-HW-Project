public class BleedEffect implements StatusEffect {
    private int duration = 3;

    @Override
    public void apply(PlayableCharacter target) {
        target.takeDamage(5);
        System.out.println(target.getName() + " страдает от кровотечения! (-5 HP)");
    }

    @Override public void reduceDuration() { duration--; }
    @Override public boolean isExpired() { return duration <= 0; }
    @Override public String getName() { return "Кровотечение"; }
}
