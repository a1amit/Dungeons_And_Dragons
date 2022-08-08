package DAL;

import Backend.Characters.RelatedAttributes.Position;
import Backend.Enemies.Enemy;
import Frontend.Observers.Observer;

import java.util.List;


public class RunningGame implements Observer {

    private Board board;
    private List<List<String>> levels;
    private MessageHandler summary;
    private String tickSummary = "";

    public RunningGame() {
        this.board = new Board();
    }

    /**
     * represents the flow of the game
     */

    public void run() {
        this.board.player.gameTick();
        if (!board.getAllEnemies().isEmpty()) {
            for (Enemy enemy : board.getAllEnemies()) {

                Position lastEnemyPosition = new Position(enemy.getUnitPosition().getX(), enemy.getUnitPosition().getY());

                tickSummary += enemy.interact(board.getTile(enemy.gameTick(this.board.player.getUnitPosition()))); // enemy movement
                if (!board.player.isAlive) //if the player died stop the game
                    break;
                this.board.setPostion(enemy.getUnitPosition(), lastEnemyPosition); //changes enemy's position on board

            }
        }

        /**
         * message handler
         */
        summary.send(tickSummary);
        if (board.getAllEnemies().isEmpty()) {
            levels.remove(0);
            if (!levels.isEmpty()) {
                board.BuildCurrentLvl(levels.get(0));
            } else {
                summary.send("Congrats, You beat the game!");
                System.exit(0);
            }
        }
        summary.send(board.toString());
        if (!board.player.isAlive) {
            summary.send("You Lose");
            System.exit(0);
        }
    }


    @Override
    public void update(String choice) {
        board.player.setAllEnemies(board.getAllEnemies());

        if (choice.equals("e")) {
            tickSummary = this.board.player.castAbility();
        } else {
            Position moveTo = new Position(this.board.player.getPosition().getX(), this.board.player.getPosition().getY());
            switch (choice) {
                case "a":
                    moveTo.moveLeft();
                    break;
                case "d":
                    moveTo.moveRight();
                    break;
                case "s":
                    moveTo.moveDown();
                    break;
                case "w":
                    moveTo.moveUp();
                    break;
                case "q":
                    break;
                default:
                    return;
            }

            Position positionBeforeMovement = new Position(board.getPlayer().getUnitPosition().getX(), board.getPlayer().getUnitPosition().getY());
            tickSummary = this.board.player.interact(board.getTile(moveTo));
            if (!positionBeforeMovement.equals(board.getPlayer().getUnitPosition())) {
                board.setTileEmpty(positionBeforeMovement);


            }
        }
        board.setAllEnemies(board.player.getAllEnemies());
        board.updateEnemy();
        board.updateTilePositionOnBoard(board.getPlayer());
        run();
    }


    @Override
    public void update(List<List<String>> gameLevels) {
        this.levels = gameLevels;
        board.BuildCurrentLvl(levels.get(0));
        summary.send(board.toString());
    }


    @Override
    public void update(int choice) {
        if (board.getPlayer() == null) {
            summary.send(board.createPlayer(choice, new Position(0, 0)));
        }
    }


    @Override
    public void update(MessageHandler messageHandler) {
        this.summary = messageHandler;
    }

}