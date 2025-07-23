public class Skill {
    private String name;
    private String description;
    private int cost;
    private ResourceType resourceType;
    public enum ResourceType { MANA, STAMINA, NONE }
    private SkillAction action;

    public Skill(String name, String description, int cost, ResourceType resourceType, SkillAction action) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.resourceType = resourceType;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public int getCost() { return cost; }
    public ResourceType getResourceType() { return resourceType; }

    public boolean canUse(Player user) {
        return switch (resourceType) {
            case MANA -> user.mana >= cost;
            case STAMINA -> user.stamina >= cost;
            case NONE -> true;
        };
    }

    public void use(PlayableCharacter user, PlayableCharacter target) {
        action.execute(user, target);
    }

    public String getDescription() {
        return description;
    }

    public interface SkillAction {
        void execute(PlayableCharacter user, PlayableCharacter target);
    }
}
