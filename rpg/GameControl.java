package rpg;

/**
 * This is a simplified version of a role-playing game.
 */
public class GameControl {

  /**
   * Creates a human player to play the game.
   */
  HumanPlayer human = new HumanPlayer();

  /**
   * Creates a computer player to play the game.
   */
  ComputerPlayer computer = new ComputerPlayer();

  /**
   * Prints the game's context and rules.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void printInstructions(){
    System.out.println();
    System.out.println("Welcome to the final battle against enemy forces. You will be facing off against the computer.");
    System.out.println("Each of you will have 3 units with randomly generated jobs and levels.");
    System.out.println("The jobs are: mage, knight, and archer. Archers are strong against mages, but weak against knights.");
    System.out.println("Mages are strong against knights, but weak against archers. Knights are strong against archers, but weak against mages.");
    System.out.println("There are two moves: attack (deal damage to one target) and block (temporarily increase defense).");
    System.out.println("Combat is turn based; all your love units will take a turn and then all the computer's live units will take a turn.");
    System.out.println("You have 10 turns to defeat the computer. If both players still have units standing, you only win ");
    System.out.println("if the combined HP of your units exceeds the computer's.");
    System.out.println("========================================================");
  }
  
  /**
   * Prints the current status of all human units and all computer units.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void printStatus(){
    System.out.println();
    System.out.println("Your units:");
    this.human.getFalia().printCurrentStatus();
    this.human.getErom().printCurrentStatus();
    this.human.getAma().printCurrentStatus();
    System.out.println();
    System.out.println("Computer units:");
    this.computer.getCriati().printCurrentStatus();
    this.computer.getLedde().printCurrentStatus();
    this.computer.getTyllion().printCurrentStatus();
    System.out.println();
  }

  /**
   * Takes the human player's turn by calling moveUnit on each of the human player's three units: Falia, Erom, and Ama.
   * Prints the unit's job and level before moving it. Checks if there is no winner before proceeding to the next move.
   * If there is a winner between the first and second unit's turn or between the second and third unit's turn,
   * then return out of the method to end the human turn.
   * Resets any computer temporary defense after all human units have made their move.
   * Note: This method does not return anything.
   * @param turn int representing the current turn that the game is on.
   */
  public void takeHumanTurn(int turn){

    System.out.println("Turn " + turn + ": Human player's turn.");
    System.out.println();
    System.out.println("Job " + this.human.getFalia().getJob() + ". Level " + this.human.getFalia().getLevel());
    this.human.moveUnit(this.human.getFalia(), this.computer);
    if (getWinner(turn) != null) return;
    
    System.out.println();
    System.out.println("Job " + this.human.getErom().getJob() + ". Level " + this.human.getErom().getLevel());
    this.human.moveUnit(this.human.getErom(), this.computer);
    if (getWinner(turn) != null) return;

    System.out.println();
    System.out.println("Job " + this.human.getAma().getJob() + ". Level " + this.human.getAma().getLevel());
    this.human.moveUnit(this.human.getAma(), this.computer);
    
    this.computer.resetTemporaryDefense();
  }

  /**
   * Takes the computer player's turn and resets any human temporary defense after the computer has made its moves.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void takeComputerTurn(){

    this.computer.moveUnit("Criati", this.computer.getCriati(), this.human.getFalia(), "attack");
    this.computer.moveUnit("Ledde", this.computer.getLedde(), this.human.getErom(), "attack");
    this.computer.moveUnit("Tyllion", this.computer.getTyllion(), this.human.getAma(), "attack");

    this.human.resetTemporaryDefense();
  
  }

  /**
   * Gets the winner of the game based on the turn parameter and whether one of the players has been knocked out.
   * If the turn is less than 10, return null if both players are alive, otherwise return the winner if the opposing player is knocked out.
   * If both players still have living units after 10 turns, then the player with the greatest sum of HP wins, otherwise it is a tie.
   * @param turn int representing the current turn that the game is on.
   * @return String representing who won the game ("human" or "computer") or "tie" if there is a tie.
   * Return null if both players are still alive and the current turn is less than 10.
   */
  public String getWinner(int turn) {

    int computerPoints = this.computer.getCriati().getHp() + this.computer.getLedde().getHp() + this.computer.getTyllion().getHp();
    int humanPoints = this.human.getAma().getHp() + this.human.getErom().getHp() + this.human.getFalia().getHp();

    if (turn < 10) {
        if (!this.human.isKnockedOut() && !this.computer.isKnockedOut()) {
            return null;
        } else if (this.human.isKnockedOut()) {
            return "computer";
        } else if (this.computer.isKnockedOut()) {
            return "human";
        }
    } else {
        if (computerPoints > humanPoints) {
            return "computer";
        } else if (humanPoints > computerPoints) {
            return "human";
        } else {
            return "tie";
        }
    }
    return null;
}

  /**
   * Creates an instance of GameControl and contains the flow of this role-playing game.
   * Note: This method does not return anything.
   * @param args Not used.
   */
  public static void main(String[] args){

    GameControl gC = new GameControl();
    gC.printInstructions();

    String winner = null;
    boolean gameWon = false;

    int turn = 0;
    while (turn < 10) {
            gC.printStatus();
            System.out.println("------------------------------------------------");
            System.out.println("Turn: " + turn);

            gC.takeHumanTurn(turn);
            winner = gC.getWinner(turn);
            if (winner != null) {
                    gameWon = true;
                    break;
            }

            gC.printStatus();
            gC.takeComputerTurn();
            winner = gC.getWinner(turn);
            if (winner != null) {
                    gameWon = true;
                    break;
            }

            turn++;
    }

    if (!gameWon) {
        winner = gC.getWinner(10); // Determine the winner after 10 turns if no one has won yet
    }

    if ("computer".equals(winner)) {
            System.out.println("All your heroes have been defeated, enemy forces have won!");
    } else if ("human".equals(winner)) {
            System.out.println("You've defeated the enemy!");
    } else {
            System.out.println("Nobody wins, it is a tie!");
            }
        }
}