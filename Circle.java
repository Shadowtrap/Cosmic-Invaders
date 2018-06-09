public class Circle {
    
    //Instance Variables
    private int x,y,diam,vX,vY, damage;
    
    //Paramterized Constructor
    public Circle(int x, int y, int diam, int vX, int vY, int damage){
        this.x = x;
        this.y = y;
        this.diam = diam;
        this.vX = vX;
        this.vY = vY;
        this.damage = damage;
    }
    
    //toString Methods
    @Override
    public String toString(){
        return "Circle- \nPosition: (" + x + "," + y + ")\nDiameter: " + diam + "\nVelocityX: " + vX + "\nVelocityY: " + vY + "\nDamage: " + damage; 
    }
    
    //Setters
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setDiam(int diam){
        this.diam = diam;
    }
    public void setVX(int vX){
        this.vX = vX;
    }
    public void setVY(int vY){
        this.vY = vY;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    
    //Getters
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getDiam(){
        return this.diam;
    }
    public int getVX(){
        return this.vX;
    }
    public int getVY(){
        return this.vY;
    }
    public int getDamage(){
        return this.damage;
    }
    
    public boolean destroyCircleUp()
    {
        if(y<0)
            return true;
        else 
            return false;
    }
    public boolean destroyCircleDown()
    {
        if(y > 1000)
            return true;
        else 
            return false;
    }
    
    //Moving Methods
    public void moveUp(){
        y = y - vY;
    }
    public void moveDown(){
        y = y + vY;
    }
    public void moveLeft(){
        x = x - vX;
    }
    public void moveRight(){
        x = x + vX;
    }
}


