abstract class Question {
    private String description;
    private String answer;

    Question() {
    }

    public String getDescription() {
        return this.description;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setDescription(String var1) {
        this.description = var1;
    }

    public void setAnswer(String var1) {
        this.answer = var1;
    }
}
