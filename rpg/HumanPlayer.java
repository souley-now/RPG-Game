package rpg;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the human player and holds their units in this role-playing game.
 */
public class HumanPlayer {

  /**
   * Human Unit 1: Falia
   */
  Unit falia;

  /**
   * Human Unit 2: Erom
   */
  Unit erom;

  /**
   * Human Unit 3: Ama
   */
  Unit ama;

  /**
   * A random number generator to be used for returning random levels and jobs.
   */
  Random random = new Random();

  /**
   * A scanner to be used for selecting moves and targets.
   */
  Scanner scan = new Scanner(System.in);

  /**
   * Constructs a human player.
   */
  public HumanPlayer(){
    this.falia = new Unit("Falia", generateLevel(),generateJob());
    this.erom = new Unit("Erom", generateLevel(),generateJob());
    this.ama = new Unit("Ama", generateLevel(),generateJob());
  }

  // Getters and Setters

  /**
   * Returns the falia Unit.
   * Note: This method does not take any parameters.
   * @return falia
   */
  public Unit getFalia(){
    return falia;
  }

  /**
   * Returns the erom Unit.
   * Note: This method does not take any parameters.
   * @return erom
   */
  public Unit getErom() {
    return erom;
  }

  /**
   * Returns the ama Unit.
   * Note: This method does not take any parameters.
   * @return ama
   */
  public Unit getAma() {
    return ama;
  }

  /**
   * Randomly chooses a string representing the level of a unit by generating a random integer.
   * There are three possible levels: low, medium, high.
   * Note: This method does not take any parameters.
   * @return String of the generated level of a human's unit
   */
  private String generateLevel(){
    String generatedLevel;

    // generate a random integer from 0 to 2
    int randomInt = this.random.nextInt(3);

    // assign generatedLevel a level based on randomInt's value
    if(randomInt == 0){

      generatedLevel = "low";
    }
    else if(randomInt == 1){
      generatedLevel = "medium";
    }
    else{
      generatedLevel = "high";
    }

    return generatedLevel;
  }

  /**
   * Randomly chooses a string representing the job of a unit by generating a random integer.
   * There are three possible jobs: mage, knight, archer.
   * Note: This method does not take any parameters.
   * @return String of the generated job a human's unit will take on
   */
  private String generateJob(){
    String generatedJob;

    // generate a random integer from 0 to 2
    int randomInt = this.random.nextInt(3);

    // assign generatedJob a level based on randomInt's value
    if(randomInt == 0){
      generatedJob = "mage";
    }
    else if(randomInt == 1){
      generatedJob = "knight";
    }
    else{
      generatedJob = "archer";
    }

    return generatedJob;
  }

  /**
   * Checks if the user entered a valid move string, meaning it begins with one of the following letters: 'a' 'A' 'b' 'B'
   * Prints a friendly message to enter a valid input and returns null if the string is invalid.
   * @param move String representing the move to be performed by a human unit, for example, "attack" or "block"
   * @return String of "attack" or "block" or null
   */
  public String validateMove(String move){
    if (move.toLowerCase().charAt(0) == 'a'){
      return "attack";
    } else if (move.toLowerCase().charAt(0) == 'b') {
      return "block";
    } else {
      System.out.println("Please enter an 'a' or 'b'");
      return null;
    }
  }

  /**
   * Checks if the computer target selected by the human is alive and returns said target if it exists.
   * If the target with the given name is not alive or does not exist, print a message saying so and return null.
   * @param targetName String that should be the name of a computer unit
   * @param computer ComputerPlayer that the human is currently playing against
   * @return Unit representing the target belonging to the computer or null
   */

  public Unit selectTarget(String targetName, ComputerPlayer computer){

    // Retrieve all computer units
    Unit[] computerUnits = {computer.getCriati(), computer.getLedde(), computer.getTyllion() };
    
    // Loop through each unit and check if the targetName matches
    for (Unit unit : computerUnits) {
        if (unit.name.equals(targetName)) {
            if (unit.getHp() > 0) {
                return unit;
            } else {
                System.out.println("The target has already been vanquished.");
                return null;
            }
        }
  }
  // If no matching unit found
  System.out.println("The target is not a member of the enemy’s forces.");
  return null;
}

  /**
   * Determines the strength of the attacker by comparing the attacker's job and the job of the target.
   * Mages are strong against knights, but weak against archers. Knights are strong against archers, but weak against mages.
   * There are three possible attacker strengths: same, strong, weak.
   * @param attacker Unit belonging to human that is attacking the target
   * @param target Unit belonging to computer that is being attacked by the human
   * @return String representing the strength of the attacker relative to the target
   */
  public String determineAttackerStrength(Unit attacker, Unit target){
    String determinedStrength;

    // assign determinedStrength by comparing job of attacker with job of the target
    if(attacker.getJob().equalsIgnoreCase(target.getJob())){
      determinedStrength = "same";
    }
    else if((attacker.getJob().equalsIgnoreCase("knight") && target.getJob().equalsIgnoreCase("archer")) ||
            (attacker.getJob().equalsIgnoreCase("archer") && target.getJob().equalsIgnoreCase("mage")) ||
            (attacker.getJob().equalsIgnoreCase("mage") && target.getJob().equalsIgnoreCase("knight"))){
      determinedStrength = "strong";
    }
    else{
      determinedStrength = "weak";
    }

    return determinedStrength;
  }

  /**
   * For the given unit, allow human player to pick between attacking a target of their choosing or blocking.
   * This human unit will carry out the selected move during its turn.
   * Note: This method does not return anything.
   * @param unit Unit that is currently taking a turn
   * @param computer ComputerPlayer that human is playing against
   */
  public void moveUnit(Unit unit, ComputerPlayer computer) {
    if (unit.getHp() > 0) {
        // Perform the move
    } else {
        System.out.println("The unit is not alive");
        return;
    }

    System.out.println("Please select a move 'Attack as a' or 'Block as b': ");
    String move = scan.next();
    String validateMove = validateMove(move);

    if (validateMove != null) {
        if (validateMove.equals("attack")) {
            System.out.println("Please enter the name of the target: ");
            String targetName = scan.next();
            Unit target = selectTarget(targetName, computer);
            if (target != null) {
                String attackMethod = determineAttackerStrength(unit, target);
                int damage = unit.attack(attackMethod);
                target.receiveDamage(damage);
            } else {
                System.out.println("Error: Please pick another target.");
            }
        } else if (validateMove.equals("block")) {
            unit.block();
        }
    }
}

  /**
   * Resets temporary defensive buff of each human unit by setting temporaryDefense back to 0.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void resetTemporaryDefense(){
    this.erom.setTemporaryDefense(0);
    this.falia.setTemporaryDefense(0);
    this.ama.setTemporaryDefense(0);
  }

  /**
   * Determines if human player has lost or is knocked out.
   * This is done by checking if all of its three units are knocked out.
   * Note: This method does not take any parameters.
   * @return boolean true if human has no units left or false
   */
  public boolean isKnockedOut(){

    // return true if all human units have 0 HP or less
    return this.falia.getHp() <= 0 && this.erom.getHp() <= 0 && this.ama.getHp() <= 0;
  }
}