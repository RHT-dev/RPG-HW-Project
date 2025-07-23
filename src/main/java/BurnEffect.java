public class BurnEffect implements StatusEffect {
    private int duration = 3;

    @Override
    public void apply(PlayableCharacter target) {
        target.takeDamage(4);
        System.out.println(target.getName() + " горит! (-4 HP)");
    }

    @Override public void reduceDuration() { duration--; }
    @Override public boolean isExpired() { return duration <= 0; }
    @Override public String getName() { return "Горение"; }
}
