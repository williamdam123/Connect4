import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Connect 4!");
        System.out.print("Enter Player 1 name (X): ");
        String player1Name = scanner.nextLine();
        Player player1 = new Player(player1Name, 'X');

        System.out.print("Play against a human or AI? (h/ai): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        Player player2;
        boolean isAI = false;

        if (choice.equals("ai")) {
            player2 = new AIPlayer("AI-Bot", 'O');
            isAI = true;
        } else {
            System.out.print("Enter Player 2 name (O): ");
            String player2Name = scanner.nextLine();
            player2 = new Player(player2Name, 'O');
        }

        int p1Score = 0;
        int p2Score = 0;
        int round = 1;

        while (round <= 3) {
            System.out.println("\n=== Round " + round + " ===");
            Game game = new Game(player1, player2, isAI);
            int winner = game.play(scanner);

            if (winner == 1) p1Score++;
            else if (winner == 2) p2Score++;

            System.out.println("Current Score: " + player1.getName() + " [" + p1Score + "] - " + player2.getName() + " [" + p2Score + "]");

            if (round < 3) {
                System.out.print("Would you like to play another round? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (!response.equals("yes")) {
                    break;
                }
            }

            round++;
        }

        System.out.println("\n=== Final Result ===");
        if (p1Score > p2Score) {
            System.out.println(player1.getName() + " wins the series!");
        } else if (p2Score > p1Score) {
            System.out.println(player2.getName() + " wins the series!");
        } else {
            System.out.println("It's a tie!");
        }

        // Save scores to file
        try (FileWriter writer = new FileWriter("scoreboard.txt", true)) {
            writer.write("Game Result:\n");
            writer.write(player1.getName() + " score: " + p1Score + "\n");
            writer.write(player2.getName() + " score: " + p2Score + "\n");
            writer.write("Winner: ");
            if (p1Score > p2Score) {
                writer.write(player1.getName() + "\n");
            } else if (p2Score > p1Score) {
                writer.write(player2.getName() + "\n");
            } else {
                writer.write("Tie\n");
            }
            writer.write("----\n");
        } catch (IOException e) {
            System.out.println("Error writing to scoreboard.txt: " + e.getMessage());
        }

        System.out.println("\nGame results saved to 'scoreboard.txt'");
        scanner.close();
    }
}
