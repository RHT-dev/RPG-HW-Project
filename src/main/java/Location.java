public class Location {
    private final String name;
    private final String description;
    private final LocationEvent event;

    public Location(String name, String description, LocationEvent event) {
        this.name = name;
        this.description = description;
        this.event = event;
    }

    public void enter(Player player) {
        System.out.println("\n📍 Локация: " + name);
        System.out.println(description);
        System.out.println("--------");
        event.trigger(player);
    }

    public String getName() {
        return name;
    }

    // Функциональный интерфейс для событий
    public interface LocationEvent {
        void trigger(Player player);
    }
}