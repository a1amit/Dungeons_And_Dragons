package DAL;

import Backend.Characters.*;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Enemies.Enemy;
import Backend.Enemies.Monster;
import Backend.Enemies.Trap;
import Backend.Tiles.Tile;
import Backend.Tiles.Empty;
import Backend.Tiles.Wall;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile[][] Board;
    protected Player player;
    private List<Enemy> allEnemies;

    public Board() {

    }

    /**
     *
     * @param playerChoice the player picked by the user
     * @param playerPosition the players position on the board
     * this replaces the functionality of the playerFactory
     */
    public String createPlayer(int playerChoice, Position playerPosition) {

        switch (playerChoice) {
            case 1 -> this.player = new Warrior(playerPosition, "Jon Snow", new Health(300), 30, 4, 3);
            case 2 -> this.player = new Warrior(playerPosition, "The Hound", new Health(400), 20, 6, 5);
            case 3 -> this.player = new Mage(playerPosition, "Melisandre", new Health(100), 5, 1, 15, 300, 30, 5, 6);
            case 4 ->
                    this.player = new Mage(playerPosition, "Thoros of Myr", new Health(250), 25, 4, 20, 150, 20, 3, 4);
            case 5 -> this.player = new Rogue(playerPosition, "Arya Stark", new Health(150), 40, 2, 20);
            case 6 -> this.player = new Rogue(playerPosition, "Bronn", new Health(250), 35, 3, 50);
            case 7 -> this.player = new Hunter(playerPosition, "Ygritte", new Health(220), 30, 2, 6);
        }
        return "You have selected:\n" + player.getName() + "\n";
    }


    /**
     * @param lines holds the lvls as a list of strings
     *              this function sets up and builds the current lvl
     */
    public void BuildCurrentLvl(List<String> lines) {
        Tile newTile = null;
        allEnemies = new ArrayList<Enemy>();
        Board = new Tile[lines.size()][lines.get(0).length()]; // The Board
        for (int x = 0; x < lines.size(); x++) {
            for (int y = 0; y < lines.get(0).length(); y++) {
                char tileType = lines.get(x).charAt(y);

                switch (tileType) {
                    case '.' -> Board[x][y] = new Empty(new Position(x, y));
                    case '#' -> Board[x][y] = new Wall(new Position(x, y));
                    case '@' -> {
                        this.player.setPosition(x, y);
                        Board[x][y] = player;
                    }
                    case 's' -> {
                        allEnemies.add(new Monster('s', new Position(x, y), "Lannister Solider", new Health(80), 8, 3, 25, 3));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'k' -> {
                        allEnemies.add(new Monster('k', new Position(x, y), "Lannister Knight", new Health(200), 14, 8, 50, 4));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'q' -> {
                        allEnemies.add(new Monster('q', new Position(x, y), "Queen's Guard", new Health(400), 20, 15, 100, 5));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'z' -> {
                        allEnemies.add(new Monster('z', new Position(x, y), "Wright", new Health(600), 30, 15, 100, 3));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'b' -> {
                        allEnemies.add(new Monster('b', new Position(x, y), "Bear-Wright", new Health(1000), 75, 30, 250, 4));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'g' -> {
                        allEnemies.add(new Monster('g', new Position(x, y), "Giant-Wright", new Health(1500), 100, 40, 500, 5));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'w' -> {
                        allEnemies.add(new Monster('w', new Position(x, y), "White Walker", new Health(2000), 150, 50, 1000, 6));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'M' -> {
                        allEnemies.add(new Monster('M', new Position(x, y), "The Mountain", new Health(1000), 60, 25, 500, 6));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'C' -> {
                        allEnemies.add(new Monster('C', new Position(x, y), "Queen Cersei", new Health(100), 10, 10, 1000, 1));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'K' -> {
                        allEnemies.add(new Monster('K', new Position(x, y), "Knight's King", new Health(5000), 300, 150, 5000, 8));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'B' -> {
                        allEnemies.add(new Trap('B', new Position(x, y), "Bonus Trap", new Health(1), 1, 1, 250, 1, 5));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'Q' -> {
                        allEnemies.add(new Trap('Q', new Position(x, y), "Queen's Trap", new Health(250), 50, 10, 100, 3, 7));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                    case 'D' -> {
                        allEnemies.add(new Trap('D', new Position(x, y), "Death Trap", new Health(500), 100, 20, 250, 1, 10));
                        Board[x][y] = allEnemies.get(allEnemies.size() - 1);
                    }
                }
            }
        }
        player.setAllEnemies(allEnemies);
    }

    public void setAllEnemies(List<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[0].length; j++) {
                board.append(Board[i][j].toString());
            }
            board.append("\n");
        }
        return board + "\n" + player.describe();
    }

    /**
     sets the position of the tile on the board
     */
    public Board setPostion(Position pos1, Position pos2) {
        Tile tile1 = getTile(pos1);
        Tile tile2 = getTile(pos2);

        Board[pos1.getX()][pos1.getY()] = tile2;
        Board[pos2.getX()][pos2.getY()] = tile1;
        return this;
    }

    public List<Enemy> getAllEnemies() {
        return this.allEnemies;
    }

    public Player getPlayer() {
        return player;
    }


    public Tile getTile(Position position) {
        return this.Board[position.getX()][position.getY()];
    }


    public void setTileEmpty(Position pos) {
        this.Board[pos.getX()][pos.getY()] = new Empty(pos);
    }


    public void updateTilePositionOnBoard(Tile tile) {
        this.Board[tile.getPosition().getX()][tile.getPosition().getY()] = tile;
    }


    public void updateEnemy() {
        for (Enemy enemy : allEnemies) {
            if (!enemy.isAlive)
                setTileEmpty(enemy.getPosition());
            else
                updateTilePositionOnBoard(enemy);
        }
        allEnemies.removeIf(enemy -> !enemy.isAlive);
    }


}
