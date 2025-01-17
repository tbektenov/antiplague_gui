package model.difficulty;

public enum Difficulty {

    EASY(0.5f, 0.75f),
    MEDIUM(1f, 1f),
    HARD(1.5f, 1.25f);

    private final float infectionMultiplier;
    private final float spawnRateMultiplier;

    Difficulty(float infectionMultiplier, float spawnRateMultiplier) {
        this.infectionMultiplier = infectionMultiplier;
        this.spawnRateMultiplier = spawnRateMultiplier;
    }

    public float getInfectionMultiplier() {
        return infectionMultiplier;
    }

    public float getSpawnRateMultiplier() {
        return spawnRateMultiplier;
    }

    public float calculateScoreMultiplier() {
        return (infectionMultiplier + spawnRateMultiplier) / 2;
    }
}
