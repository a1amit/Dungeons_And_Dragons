package Backend.Tiles;

import Backend.Characters.RelatedAttributes.Position;
import Backend.Visitors.TileVisited;
import Backend.Visitors.TileVisitor;


public abstract class Tile implements TileVisited, TileVisitor {


    protected char tileType; // the symbol
    protected Position position; // the position of the tile on the board

    public Tile(char tile, Position position) {

        this.tileType = tile;
        this.position = position;

    }


    public char getTileType() {
        return tileType;
    }

    /**
     *
     * @return the position of the tile
     */
    public Position getPosition() {
        return position;
    }
    /**
     *
     * sets the position of the tile by position object
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     *
     * sets the position of the tile by coordinates
     */
    public void setPosition(int x, int y) {
        this.position.setPosition(x, y);
    }


    /**
     *
     * @param p the position of the p element in the euclidean distance calculation
     * @param q the position of the q element in the euclidean distance calculation
     * @return euclidean distance between two tiles
     */
    public double Range(Position p, Position q) {
        return Math.sqrt(Math.pow((p.getX() - q.getX()), 2) + Math.pow((p.getY() - q.getY()), 2));
    }

    /**
     * 
     * implementation of the visitor pattern
     */
    public String interact(TileVisited tile) {
        return tile.accept(this);
    }

    @Override
    public String toString() {
        return "" + this.tileType;
    }



}
