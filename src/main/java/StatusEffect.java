public interface StatusEffect {
    void apply(PlayableCharacter target);
    void reduceDuration();
    boolean isExpired();
    String getName();
}
