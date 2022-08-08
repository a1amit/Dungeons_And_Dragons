package Backend.Characters.RelatedAttributes;

public class Position {


    /**
     * @param x on board
     * @param y on board
     */
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    /**
     * represents the movement on the board
     */
    public void moveUp() {
        this.x -= 1;
    }

    public void moveDown() {
        this.x += 1;
    }

    public void moveRight() {
        this.y += 1;
    }

    public void moveLeft() {
        this.y -= 1;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

    }

}