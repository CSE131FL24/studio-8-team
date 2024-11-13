package studio8;

public class SelectAllQuestion extends MultipleChoiceQuestion {

    // Constructor
    public SelectAllQuestion(String prompt, String answer, String[] choices) {
        // Call superclass constructor with prompt, answer, number of points (based on length of answer), and choices
        super(prompt, answer, answer.length(), choices);
    }

    // Method to check given answer and award partial credit based on correct/incorrect answers
    @Override
    public int checkAnswer(String givenAnswer) {
        int points = 0;

        // Points for correctly selected answers
        for (int i = 0; i < givenAnswer.length(); i++) {
            String ans = String.valueOf(givenAnswer.charAt(i));
            if (this.getAnswer().contains(ans)) {
                points += 1; // Add 1 point for each correct answer given
            }
        }

        // Deduct points for missing correct answers
        points -= findMissingCorrectAnswers(givenAnswer);

        // Deduct points for incorrect answers that were not in the original correct answer
        points -= findIncorrectGivenAnswers(givenAnswer);

        // Ensure points are not negative
        return Math.max(points, 0);
    }

    // Calculates how many correct answers are missing in the given answer
    private int findMissingCorrectAnswers(String givenAnswer) {
        String answer = this.getAnswer();
        return findMissingCharacters(givenAnswer, answer); // Number of correct answers not in given answer
    }

    // Calculates how many incorrect answers are in the given answer
    private int findIncorrectGivenAnswers(String givenAnswer) {
        String answer = this.getAnswer();
        return findMissingCharacters(answer, givenAnswer); // Number of extra answers in given answer not in correct answer
    }

    // Helper method to find missing characters in a given answer
    private static int findMissingCharacters(String baseString, String toCheck) {
        int missingValues = 0;
        for (int i = 0; i < toCheck.length(); i++) {
            char characterToLocate = toCheck.charAt(i);
            if (baseString.indexOf(characterToLocate) == -1) { // Character not in baseString
                missingValues++;
            }
        }
        return missingValues;
    }

    // Main method to test the SelectAllQuestion class
    public static void main(String[] args) {
        String[] choices = {"instance variables", "git", "methods", "eclipse"};
        Question selectAll = new SelectAllQuestion("Select all of the following that can be found within a class:", "13", choices);
        selectAll.displayPrompt();
        System.out.println(selectAll.checkAnswer("hi")); // Expected: 0 points
        System.out.println(selectAll.checkAnswer("2")); // Expected: 1 point
        System.out.println(selectAll.checkAnswer("13")); // Expected: Full credit
        System.out.println(selectAll.checkAnswer("31")); // Expected: Full credit
        System.out.println(selectAll.checkAnswer("1")); // Expected: 3 points
        System.out.println(selectAll.checkAnswer("3")); // Expected: 3 points
        System.out.println(selectAll.checkAnswer("23")); // Expected: 2 points
        System.out.println(selectAll.checkAnswer("34")); // Expected: 2 points
        System.out.println(selectAll.checkAnswer("4")); // Expected: 1 point
        System.out.println(selectAll.checkAnswer("124")); // Expected: 1 point
        System.out.println(selectAll.checkAnswer("24")); // Expected: 0 points
    }
}
