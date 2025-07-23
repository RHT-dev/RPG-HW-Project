public class Quest {
    public enum Status { NOT_TAKEN, IN_PROGRESS, COMPLETED }
    private final String title;
    private final String description;
    private final int expReward;
    private final int goldReward;
    private Status status = Status.NOT_TAKEN;
    private boolean isCompleted = false;

    public Quest(String title, String description, int expReward, int goldReward) {
        this.title = title;
        this.description = description;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getExpReward() { return expReward; }
    public int getGoldReward() { return goldReward; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public boolean isCompleted() { return isCompleted; }
    public void complete() { isCompleted = true; status = Status.COMPLETED; }
} 