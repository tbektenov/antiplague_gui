package model.difficulty;

public enum Difficulty {
    EASY(0.5f, 0.75f),
    MEDIUM(1.0f, 1.0f),
    HARD(1.5f, 1.25f);

    private final float infectionMultiplier;
    private final float spawnRateMultiplier;

    Difficulty(float infectionMultiplier, float spawnRateMultiplier) {
        this.infectionMultiplier = infectionMultiplier;
        this.spawnRateMultiplier = spawnRateMultiplier;
    }

    public float getInfectionMultiplier() {
        System.out.println(infectionMultiplier);
        return infectionMultiplier;
    }

    public float getSpawnRateMultiplier() {
        System.out.println(spawnRateMultiplier);
        return spawnRateMultiplier;
    }
}
