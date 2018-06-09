import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.applet.*;


public class OspinaMittalProj7 extends JComponent implements KeyListener, MouseListener{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private Rectangle player, boss;
    private ArrayList<Circle> bullets;
    private ArrayList<Circle> enemyBullets;
    private ArrayList<Circle> asteroids;
    private ArrayList<Circle> asteroids2;
    private Date startTime;
    private boolean lose,win;
    private boolean bossBattle;
    private AudioClip bulletSounds;
    private boolean left = false;
    private boolean right = false;
    private JFrame gui = new JFrame();
    private boolean gameover;
    private Rectangle phBar, bhBar;
    private boolean enemyBulletsStart;
    
    
    //Default Constructor
    public OspinaMittalProj7()
    {
        //initializing instance variables
        WIDTH = 525;
        HEIGHT = 1000;
        startTime = new Date();
        player = new Rectangle(WIDTH/2 - 30, HEIGHT - 125, 60, 70, 10, 0);
        boss = new Rectangle(WIDTH /2 - 50, 50, 100, 110, 0,0);
        bullets = new ArrayList<Circle>();
        enemyBullets = new ArrayList<Circle>();
        asteroids = new ArrayList<Circle>();
        asteroids2 = new ArrayList<Circle>();
        lose = false;
        win = false;
        bossBattle = false;
        bulletSounds = Applet.newAudioClip(this.getClass().getResource("Laser_Shoot3.wav"));
        enemyBulletsStart = true;
        phBar = new Rectangle(WIDTH/2 - 50, HEIGHT - 30, 100, 10, 0, 0);
        bhBar = new Rectangle(WIDTH/2 - 100, 30, 200, 10, 0, 0);
        
        //Setting up the GUI
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Cosmic Invaders");
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        //Place for buttons
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);

    }
    //This method will acknowledge user input
    public void keyPressed(KeyEvent e) 
    {

        //getting the key pressed
        int key = e.getKeyCode();
        System.out.println(key);
        //moving the rectangle
        if(key == 37){
            left = true;
        }
        else if(key == 39){
            right = true;
        }
        //Placing bullets at the starting positon
        if(key == 32){
            if(bullets.size()<=4) 
            {
                int centerLeftX = player.getX() - (4);
                int centerLeftY = player.getY() - (4);
                int centerRightX = (player.getX() + player.getW()) - (4);
                int centerRightY = player.getY() - (4);

                Circle bullet1 = new Circle(0,0,0,0,15,0);
                bullet1.setDiam(8);
                bullet1.setX(centerLeftX);
                bullet1.setY(centerLeftY - 20);
                bullet1.setDamage(1);
                Circle bullet2 = new Circle(0,0,0,0,15,0);
                bullet2.setDiam(8);
                bullet2.setX(centerRightX - 10);
                bullet2.setY(centerRightY - 20);
                bullet2.setDamage(1);
                if(!lose && !win){
                    bulletSounds.play();
                }
                bullets.add(bullet1);
                bullets.add(bullet2);
                
            }
            
        }
        
        //To start new game after losing
        if(key == 10 && lose == true){
            gui.dispose();
            gameover = true;
            OspinaMittalProj7 g = new OspinaMittalProj7();
            g.start(60);
        }
        
        //To start new game after winning
        if(key == 10 && win == true){
            gui.dispose();
            gameover = true;
            OspinaMittalProj7 g = new OspinaMittalProj7();
            g.start(60);
        }
                                                    
    }   
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        //Drawing a Dark Grey Rectangle to be the background
        //g2d.setColor(Color.DARK_GRAY);
        //g2d.fillRect(0, 0, WIDTH, HEIGHT);
        ImageIcon background = new ImageIcon(OspinaMittalProj7.class.getResource("Space_Background.png"));
        Image back = background.getImage();
        g2d.drawImage(back, 0,0, WIDTH, HEIGHT, null);

        //Drawing the user-controlled rectangle
        //g.setColor(Color.YELLOW);
        //g.fillRect(player.getX(), player.getY(), player.getW(), player.getH());
        ImageIcon user = new ImageIcon(OspinaMittalProj7.class.getResource("player.png"));
        Image ship = user.getImage();
        g2d.drawImage(ship, player.getX(),player.getY(), player.getW(), player.getH() + 10, null);

        //Drawing the bullets
        paintBullets(g);
        
        //Drawing the Asteroids
        paintAsteroids(g);
        
        //Drawing You lose font
        paintFontLose(g);
        
        //painting boss
        if(bossBattle && !win){
            paintBoss(g);
        }
        
        //Drawing Win Text
        paintFontWin(g); 
        
        //Drawing Health Bars
        paintHealthBars(g);
        
        //Drawing Enemy Bullets
        if(enemyBulletsStart == true)
            paintEnemyBullets(g);
        
        //Drawing controls Text
        paintStart(g);
        
        //Drawing Boss indicator Text
        if(!lose){
            paintBossText(g);
        }
        
        //Painting second wave of Asteroids
        paintAsteroid2(g);
        
        //Drawing Mid Boss Battle Text
        paintBossMidText(g);
        
    }
    
    //Painting Asteroids method
    public void paintAsteroids(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        
        for(int i = 0; i < asteroids.size();i++)
        {
            //g.setColor(Color.GREEN);
            //g.fillOval(asteroids.get(i).getX(), asteroids.get(i).getY(), asteroids.get(i).getDiam(), asteroids.get(i).getDiam());
            ImageIcon user = new ImageIcon(OspinaMittalProj7.class.getResource("asteroid.png"));
            Image asteroid = user.getImage();
            if(i < asteroids.size())
                g2d.drawImage(asteroid, asteroids.get(i).getX(), asteroids.get(i).getY(), asteroids.get(i).getDiam(), asteroids.get(i).getDiam(), null);
            
        }
    }
    
    //Destroying Asteroids method
    public void destroyAsteroids()
    {
        for(int j = asteroids.size()-1;j >= 0;j--)
        {
            if(lose){
                asteroids.get(j).setY(1000);
            }
            if(asteroids.get(j).destroyCircleDown())
                asteroids.remove(j);
            
        }
        
    }
    
    //Moving Asteroids method
    public void moveAsteroids()
    {
        for(int i = 0; i< asteroids.size();i++)
            asteroids.get(i).moveDown();
    }
    
    //Painting Player Bullets method
    public void paintBullets(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i< bullets.size();i++)
        {
            //g.setColor(Color.RED);
            //g.fillOval(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getDiam() + 5, bullets.get(i).getDiam() + 80);
            ImageIcon user = new ImageIcon(OspinaMittalProj7.class.getResource("player_bullet.png"));
            Image bullet = user.getImage();
            if(i<bullets.size())
                g2d.drawImage(bullet, bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getDiam() + 5, bullets.get(i).getDiam() + 80, null);
        }
    }
    
    //Destroying Bullets method
    public void destroyBullets()
    {
        for(int i = bullets.size()-1;i>=0;i--)
        {
            if(bullets.get(i).destroyCircleUp())
                bullets.remove(i);
            else if(lose){
                bullets.get(i).setX(-100);
                bullets.get(i).setY(-100);
            }
        }
    }
    
    //Moving Bullets method
    public void moveBullets()
    {
        for(int i = bullets.size()-1; i>=0;i--)
            bullets.get(i).moveUp();
    }
    
    //Distance formula method
    public static double distance(int x1, int y1, int x2, int y2){
        double xDiff = Math.pow(x2 - x1, 2);
        double yDiff = Math.pow(y2 - y1, 2);
        double distance = Math.sqrt(xDiff + yDiff);
        return distance;
    }
    
    //Painting Lose Text method
    public void paintFontLose(Graphics g){
        if(lose){
            Font f1 = new Font("Arial", Font.BOLD, 50);
            g.setFont(f1);
            g.setColor(Color.WHITE);
            g.drawString("You Lose", (int)(WIDTH/3.3), (int)(HEIGHT/1.9));
            Font f2 = new Font("Arial", Font.BOLD, 20);
            g.setFont(f2);
            g.setColor(Color.WHITE);
            g.drawString("(To Play Again Press Enter)", (int)(WIDTH/3.6), (int)(HEIGHT/1.8));
        }
    }
    
    //Painting Win Text method
    public void paintFontWin(Graphics g){
        if(win){
            Font f1 = new Font("Arial", Font.BOLD, 50);
            g.setFont(f1);
            g.setColor(Color.WHITE);
            g.drawString("You Won", (int)(WIDTH/3.3), (int)(HEIGHT/1.9));
            Font f2 = new Font("Arial", Font.BOLD, 20);
            g.setFont(f2);
            g.setColor(Color.WHITE);
            g.drawString("(To Play Again Press Enter)", (int)(WIDTH/3.6), (int)(HEIGHT/1.8));
        }
    }
    
    //Bullet to Asteroid collision method
    public void cToCCollision(){
        for(int i = bullets.size()-1;i>=0;i--)
        {
            boolean removeBull = false;
            for(int j = asteroids.size()-1; j>=0 ; j--){
                double distanceBetween = distance(asteroids.get(j).getX() + asteroids.get(j).getDiam()/2, asteroids.get(j).getY() + asteroids.get(j).getDiam()/2, bullets.get(i).getX() + bullets.get(i).getDiam()/2, bullets.get(i).getY() + bullets.get(i).getDiam()/2);
                if(distanceBetween <= (bullets.get(i).getDiam()/2) + (asteroids.get(j).getDiam()/2)){
                    asteroids.remove(j);
                    removeBull = true;
                }
            }
            if(removeBull==true)
                bullets.remove(i);
        }
    }
    
    //Player to Asteroid Collision Method
    public void asteroidToRCollision(){
        for(int i = player.getX(); i <= player.getX() + player.getW(); i++){
            for(int h = player.getY(); h <= player.getY() + player.getH(); h++){
                for(int j = asteroids.size()-1; j>=0 ; j--){
                    int radius = (asteroids.get(j).getDiam())/2;
                    int centerX = asteroids.get(j).getX() + radius;
                    int centerY = asteroids.get(j).getY() + radius;
                    double distanceBetween = distance(centerX,centerY,i,h);
                    if(distanceBetween <= radius || asteroids.get(j).getY() + asteroids.get(j).getDiam() == 1000){
                        lose = true;
                        player.setX(0);
                        player.setY(0);
                        player.setH(0);
                        player.setW(0);
                        asteroids.remove(j);
                    }
                }
            }
        }
    }
    
    //Asteroid Spawn Method
    public void asteroidSpawn(){
        Date gameTime = new Date();
        if((gameTime.getTime() - startTime.getTime())%14 == 0){
            int randX = (int)(Math.random() * 476);
            Circle asteroid = new Circle(randX,0,50,0,2,1);
            asteroids.add(asteroid);
        }
    }
    
    //Declaring Boss Battle Method
    public void bossBattleDeclaration(){
         Date gameTime = new Date();
         long initTime, afterInit;
         initTime = startTime.getTime()/1000;
         afterInit = gameTime.getTime()/1000;
         //System.out.println(initTime);
         //System.out.println(afterInit);
         long secondsPassed = afterInit - initTime;
         if(secondsPassed > 60 && lose == false){
            bossBattle = true;
         }
        
    }
    
    //Painting Boss Method
    public void paintBoss(Graphics g){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        if(bossBattle && secondsPassed > 70 && lose == false){
            Graphics2D g2d = (Graphics2D)g;
            //g.setColor(Color.MAGENTA);
            //g.fillRect(boss.getX(), boss.getY(), boss.getW(), boss.getH());
            ImageIcon cpu = new ImageIcon(OspinaMittalProj7.class.getResource("Enemy_Ship.png"));
            Image bossImage = cpu.getImage();
            g2d.drawImage(bossImage, boss.getX(), boss.getY(), boss.getW(), boss.getH() + 10, null);
        }
    }

    //Boss Battle Method
    public void bossBattle(){
        if(!bossBattle && !lose)
            asteroidSpawn();
        else if(bossBattle && !lose)
        {
            bossMovement();
            bulletToBossCollision();
            if(!win || !lose){
                enemyBullet();
            }
            moveEnemyBullets();
            enemyBulletToPlayerCollision();
            bulletToEnemyBulletCollision();
            barLess30();
        }
    }
    
    //Boss Movement Method
    public void bossMovement(){
        Date gameTime = new Date();
         long initTime, afterInit;
         initTime = startTime.getTime()/1000;
         afterInit = gameTime.getTime()/1000;
         //System.out.println(initTime);
         //System.out.println(afterInit);
         long secondsPassed = afterInit - initTime;
         int rand = (int)(Math.random() * 426);
         if(secondsPassed > 70){
             if(secondsPassed % 3 == 0){
                 boss.setX(rand);
             }
         }
    }
    
    //Bullet to Boss Collision Method
    public void bulletToBossCollision(){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        boolean removeBull = false;
        int currentHealthShown = bhBar.getW();
        for(int j = bullets.size()-1; j>=0; j--){
            int radius = bullets.get(j).getDiam()/2;
            int centerX = bullets.get(j).getX() + radius;
            int centerY = bullets.get(j).getY() + radius;
            boolean hBarDep = false;
            for(int i = boss.getX(); i <= boss.getX() + boss.getW(); i++){
                for(int h = boss.getY(); h <= boss.getY() + boss.getH(); h++){
                    double distanceBetween = distance(centerX,centerY,i,h);  
                    if(distanceBetween <= radius && secondsPassed > 70){
                        hBarDep = true;
                        removeBull = true;
                        if(bhBar.getW() <= 0){
                            win = true;
                        }
                        else{
                            win = false;
                        }
                        
                    }
                }
            }
            if(removeBull){
                bullets.remove(j);
            }
            if(hBarDep){
                bhBar.setW(currentHealthShown - 3);
            }
        }
    }
    
    //Painting Health Bars Method
    public void paintHealthBars(Graphics g){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        if(bossBattle && secondsPassed > 70){
            g.setColor(Color.RED);
            g.fillRect(phBar.getX(), phBar.getY(), phBar.getW(), phBar.getH());
            g.setColor(Color.RED);
            g.fillRect(bhBar.getX(), bhBar.getY(), bhBar.getW(), bhBar.getH());
        }
    }
    
    //Player Movement Method
    public void playerMovement(){
        if(player.getX() <= 0){
            player.setX(0);
        }
        if(player.getX() + player.getW() >= WIDTH){
            player.setX(WIDTH - player.getW());
        }
    }
    
    //Enemy Bullet Method
    public void enemyBullet(){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        if(secondsPassed > 72 && bossBattle){
            if(secondsPassed % 3 == 0){
                if(enemyBullets.size() <= 5){
                    Circle bullet1 = new Circle(0,0,0,0,5,0);
                    bullet1.setDiam(20);
                    bullet1.setX(boss.getX() + (boss.getW()/2));
                    bullet1.setY(boss.getY() + (boss.getH())/2);
                    enemyBullets.add(bullet1);
                }
            }
        }
    }
    
    //Movement for Enemy Bullet Method
    public void moveEnemyBullets()
    {
        for(int i = enemyBullets.size()-1; i>=0;i--){
            enemyBullets.get(i).moveDown();
            if(enemyBullets.get(i).getY() == 1000){
                enemyBullets.remove(i);
            }
        }
            
    }
    
    //Enemy Bullet to Player Collision Method
    public void enemyBulletToPlayerCollision(){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        boolean removeBull = false;
        int currentHealthShown = phBar.getW();
        for(int j = enemyBullets.size()-1; j>=0; j--){
            int radius = enemyBullets.get(j).getDiam()/2;
            int centerX = enemyBullets.get(j).getX() + radius;
            int centerY = enemyBullets.get(j).getY() + radius;
            boolean hBarDep = false;
            for(int i = player.getX(); i <= player.getX() + player.getW(); i++){
                for(int h = player.getY(); h <= player.getY() + player.getH(); h++){
                    double distanceBetween = distance(centerX,centerY,i,h);  
                    if(distanceBetween <= radius && secondsPassed > 70){
                        hBarDep = true;
                        removeBull = true;
                        if(phBar.getW() <= 1){
                            lose = true;
                        }
                        else{
                            lose = false;
                        }
                        
                    }
                }
            }
            if(removeBull){
                enemyBullets.remove(j);
            }
            if(hBarDep){
                phBar.setW(currentHealthShown - 5);
            }
        }
    }
       
    //Bullet to Enemy Bullet Collision
    public void bulletToEnemyBulletCollision(){
        for(int i = bullets.size()-1;i>=0;i--)
        {
            boolean removeBull = false;
            for(int j = enemyBullets.size()-1; j>=0 ; j--){
                double distanceBetween = distance(enemyBullets.get(j).getX() + enemyBullets.get(j).getDiam()/2, enemyBullets.get(j).getY() + enemyBullets.get(j).getDiam()/2, bullets.get(i).getX() + bullets.get(i).getDiam()/2, bullets.get(i).getY() + bullets.get(i).getDiam()/2);
                if(distanceBetween <= (bullets.get(i).getDiam()/2) + (enemyBullets.get(j).getDiam()/2)){
                    enemyBullets.remove(j);
                    removeBull = true;
                }
            }
            if(removeBull==true)
                bullets.remove(i);
        }
    }
        
    //Painting Enemy Bullets
    public void paintEnemyBullets(Graphics g){
        for(int i = enemyBullets.size()-1; i>= 0; i--){
            g.setColor(new Color(71, 52, 193));
            g.fillOval(enemyBullets.get(i).getX(), enemyBullets.get(i).getY(), enemyBullets.get(i).getDiam(), enemyBullets.get(i).getDiam());
        }
    }
    
    //Painting start controls Text
    public void paintStart(Graphics g){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        if(secondsPassed < 5){
            Font f1 = new Font("Arial", Font.BOLD, 30);
            g.setFont(f1);
            g.setColor(Color.WHITE);
            g.drawString("Controls:", (int)(WIDTH/15), (int)(HEIGHT/2.01));
            Font f2 = new Font("Arial", Font.BOLD, 30);
            g.setFont(f2);
            g.setColor(Color.WHITE);
            g.drawString("Left and Right Keys to Move", (int)(WIDTH/15), (int)(HEIGHT/1.91));
            Font f3 = new Font("Arial", Font.BOLD, 30);
            g.setFont(f3);
            g.setColor(Color.WHITE);
            g.drawString("Space Bar to Shoot", (int)(WIDTH/15), (int)(HEIGHT/1.81));
        }
    }
    
    //Painting Boss Text
    public void paintBossText(Graphics g){
        Date gameTime = new Date();
        long initTime, afterInit;
        initTime = startTime.getTime()/1000;
        afterInit = gameTime.getTime()/1000;
        long secondsPassed = afterInit - initTime;
        if(secondsPassed > 65 && secondsPassed < 69){
            Font f1 = new Font("Arial", Font.BOLD, 50);
            g.setFont(f1);
            g.setColor(Color.WHITE);
            g.drawString("Boss Battle", (int)(WIDTH/4.1), (int)(HEIGHT/1.9));
        }
    }
    
    //Painting Boss MidGame Text
    public void paintBossMidText(Graphics g){
        if((bhBar.getW() <= 101 && bhBar.getW() >= 90) && (!lose && !win)){
            Font f1 = new Font("Arial", Font.BOLD, 24);
            g.setFont(f1);
            g.setColor(Color.WHITE);
            g.drawString("Your weapons system has been damaged.", (int)(WIDTH/26.25), (int)(HEIGHT/1.9));
            Font f2 = new Font("Arial", Font.BOLD, 24);
            g.setFont(f2);
            g.setColor(Color.WHITE);
            g.drawString("They no longer work against the asteroids.", (int)(WIDTH/26.25), (int)(HEIGHT/1.8));
        }
    }
    
    //Second Boss Atrtack Method including all collisions and removing of objects
    public void barLess30(){
        int currentHealth = phBar.getW();
        if(bhBar.getW() <= 100){
            enemyBulletsStart = false;
            Date gameTime = new Date();
            if((gameTime.getTime() - startTime.getTime())%40 == 0){
                int randX = (int)(Math.random() * 476);
                Circle asteroid = new Circle(randX,0,50,0,2,1);
                asteroids2.add(asteroid);
            }
            boolean collisonRemove = false;
            boolean asteroid2Dam = false;
            for(int j = asteroids2.size()-1; j>=0 ; j--){
                int radius = (asteroids2.get(j).getDiam())/2;
                int centerX = asteroids2.get(j).getX() + radius;
                int centerY = asteroids2.get(j).getY() + radius;
                asteroids2.get(j).moveDown();
                if(asteroids2.get(j).getY() == 1000){
                    asteroids2.remove(j);
                }
                for(int h = player.getY(); h <= player.getY() + player.getH(); h++){
                    for(int i = player.getX(); i <= player.getX() + player.getW(); i++){
                        double distanceBetween = distance(centerX,centerY,i,h);
                        if(distanceBetween <= radius){
                            collisonRemove = true;
                            asteroid2Dam = true;
                        }
                    }
                }
                if(collisonRemove){
                    asteroids2.remove(j);
                }
                for(int i = bullets.size()-1;i>=0;i--)
                {
                    boolean removeBull = false;
                    double distanceBetween = distance(asteroids2.get(j).getX() + asteroids2.get(j).getDiam()/2, asteroids2.get(j).getY() + asteroids2.get(j).getDiam()/2, bullets.get(i).getX() + bullets.get(i).getDiam()/2, bullets.get(i).getY() + bullets.get(i).getDiam()/2);
                    if(distanceBetween <= (bullets.get(i).getDiam()/2) + (asteroids2.get(j).getDiam()/2)){
                        removeBull = true;
                    }
                    if(removeBull==true)
                        bullets.remove(i);
                }
                if(lose || win){
                    asteroids2.get(j).setY(1000);
                } 
            }
            
            if(asteroid2Dam){
                phBar.setW(currentHealth - 3);
            }
            if(currentHealth == 0){
                win = true;
            }
            
        }
    }
    
    //Painting Second Wave Asteroids
    public void paintAsteroid2(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i < asteroids2.size();i++)
        {
            //g.setColor(Color.GREEN);
            //g.fillOval(asteroids.get(i).getX(), asteroids.get(i).getY(), asteroids.get(i).getDiam(), asteroid.get(i).getDiam());
            ImageIcon user = new ImageIcon(OspinaMittalProj7.class.getResource("asteroid.png"));
            Image asteroid = user.getImage();
            if(i < asteroids2.size())
                g2d.drawImage(asteroid, asteroids2.get(i).getX(), asteroids2.get(i).getY(), asteroids2.get(i).getDiam(), asteroids2.get(i).getDiam(), null);
            
        }
    }
    
    //Win or Lose Method
    public void winOLose(){
        if(lose || win){
            bossBattle = false;
            player.setX(0);
            player.setY(0);
            player.setH(0);
            player.setW(0);
            boss.setX(0);
            boss.setY(0);
            boss.setH(0);
            boss.setW(0);
            bhBar.setX(0);
            bhBar.setY(0);
            bhBar.setH(0);
            bhBar.setW(0);
            phBar.setX(0);
            phBar.setY(0);
            phBar.setH(0);
            phBar.setW(0);
            for(int i = enemyBullets.size() -1; i>=0; i--){
                enemyBullets.remove(i);
            }
        }
    }
        
    //Game Logic Loop Method
    public void loop()
    {

        cToCCollision();
        asteroidToRCollision();
        destroyBullets();
        destroyAsteroids();
        bossBattleDeclaration();
        bossBattle();
        moveBullets();
        moveAsteroids();
        playerMovement();
        if(left){
            player.moveLeft();
        }
        if(right){
            player.moveRight();
        }
        winOLose();
        //Do not write below this
        repaint();
    }
    
    
    
    //These methods are required by the compiler.  
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e) 
    {
    }
    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();
        if(key == 37){
            left = false;
        }
        else if(key == 39){
            right = false;
        }
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(!gameover){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };	
        gameThread.start();
    }

    public static void main(String[] args)
    {
        OspinaMittalProj7 g = new OspinaMittalProj7();
        g.start(60);
    }
}


//PROBLEMS
//Luckily no more problems were present after 6/5/18 :)



