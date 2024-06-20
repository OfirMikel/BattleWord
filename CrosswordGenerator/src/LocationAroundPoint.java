public class LocationAroundPoint {
    int right;
    int left;
    int top;
    int bottom;

    public LocationAroundPoint(int right, int left, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public void print(){
        System.out.println("Empty spaces { \n\tright: " + right  + ",\n\tleft:  " +left  + ",\n\ttop:  " + top + ",\n\tbottom: " + bottom + "\n}");
    }
}
