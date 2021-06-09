class FillIn extends Question {
    FillIn() {
    }

    public String toString() {
        return this.getDescription().replace("{blank}", "_____");
    }
}
