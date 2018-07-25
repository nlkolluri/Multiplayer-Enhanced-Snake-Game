// Lab32bst.java
// The student version of the Lab32b assignment.


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.*;
import java.awt.image.BufferedImage;
import java.net.*;

public class multiPlayerSnake
{
 public static void main(String args[])
 {
  GfxApp gfx = new GfxApp();
  gfx.setSize(1024,768);
  gfx.addWindowListener(new WindowAdapter() {public void
  windowClosing(WindowEvent e) {System.exit(0);}});
  //gfx.startGame(); // make a method called startgame that has the start screen, then call theMethod internally from this method if a rectangle 
  //is clicked
  gfx.theMethod();
  gfx.show();
 }
}

class Snake extends Frame{
 private int startX;
 private int startY;
 private int speed;
 private double angle;
 private int thickness;
 private int nowX;
 private int nowY;
 private ArrayList <Rectangle> previousParts;
 public Snake(int x, int y, int sp, double ang, int thicc){
  startX=x;
  startY=y;
  speed=sp;
  angle=ang;
  nowX=startX;
  nowY=startY;
  thickness=thicc;
  previousParts = new ArrayList<Rectangle>();
 }
 public void move(){
  nowX+=(int)(Math.cos(angle)*speed*2);
  nowY+=(int)(Math.sin(angle)*speed*2);
  previousParts.add(new Rectangle(nowX,nowY,thickness,thickness));
 }
 
 public ArrayList<Rectangle> getParts(){
  return previousParts;
 }
 public void setAngle(double ang){
  angle=ang;
 }
 }
class Food{
 private ArrayList<Rectangle> food;
 private ArrayList <BufferedImage> foodImages;
 private String myPath;
 private BufferedImage img;
 public Food(){
  food=new ArrayList<Rectangle>();
  myPath="cakeImg.png";
  setPath(myPath);
 }
 public void makeFood(int amt){
  for (int i=0; i<amt; i++){
   Rectangle foodRect = new Rectangle((int)(Math.random()*1024),(int)(Math.random()*768),10,10);
   food.add(foodRect);
  }
 }
 public void setPath(String path){
  myPath=path;
   try{

            img = ImageIO.read(new File(path));
        } catch (IOException e) {
        }
 }
 public ArrayList<Rectangle> getFood (){
  return food;
 }
 public String /*or file*/ getFoodPath(){
  return myPath;
 }
 public BufferedImage getImg(){
  return img;
 }
}
class Enemy{
 private ArrayList<Rectangle> enemy;
 private ArrayList <BufferedImage> enemyImages;
 private String myPath;
 private BufferedImage img;
 //way to move this is to go thru every element in the arraylist and increment the x or y of each
 //can be like rect = new rectangle(rect.getX+5, .getY+5, w, h)
 public Enemy(String path){
  enemy=new ArrayList<Rectangle>();
  myPath=path;
  setPath(myPath);
 }
 public void makeEnemies(int amt){
   for (int i=0; i<amt; i++){
   Rectangle enemyRect = new Rectangle((int)(Math.random()*1024),(int)(Math.random()*768),40,40);
   enemy.add(enemyRect);
  }
 }
 
 public void moveEnemies(int xVal, int yVal){
  for (int i=0; i<enemy.size();i++){
  // enemy.get(i);
  int xCooord = (int)( (enemy.get(i).getX())+((xVal)-(Math.random()*xVal)*2) );
  int yCooord = (int)( (enemy.get(i).getY())+((yVal)-(Math.random()*yVal)*2) );
  xCooord=(xCooord+1024)%1024;
  yCooord=(yCooord+1024)%1024;
   enemy.add(i,new Rectangle(xCooord,yCooord,(int)enemy.get(i).getWidth(),(int)enemy.get(i).getHeight()));
   enemy.remove(i+1);
  // thisRect = new Rectangle(((int)(thisRect.getX())+xVal)%1024,((int)(thisRect.getX())+yVal)%768,(int)thisRect.getWidth(),(int)thisRect.getHeight());
  }
 }
 
 public ArrayList<Rectangle> getEnemies(){
  return enemy;
 }
 
 public BufferedImage getImg(){
  return img;
 }
 
 public void setPath(String path){
  myPath=path;
   try{
   //Image img = new ImageIcon(path).getImage();
            img = ImageIO.read(new File(path));
            
       } catch (IOException e) {
        }
        
 }
}

class GfxApp extends Frame implements MouseListener, KeyListener
{
 Snake playerOne;
 Snake playerTwo;
 Enemy autoBot;
 Food onePCake;
 Food powerUpCake;
 Food invincibilityMelon;
 Pool singlePool;
 Space mySpace;
 boolean started = false;
 Rectangle button = new Rectangle(450,600,100,50);
 int playerOneScore=0; //MAYBE put these in themethod
 int playerTwoScore=0;
 int playerOneSize=5;
 int bestPlayerOneSize=5;
 int cakeCount=0;
 Rectangle single = new Rectangle(225,355,150,75);
 Rectangle multi = new Rectangle(450,355,150,75);
 Rectangle howtoplay = new Rectangle(250,600,150,50);
 Rectangle backtostart = new Rectangle(763,647,100,50);
 Rectangle easy = new Rectangle(700,350,100,50);
 Rectangle medium = new Rectangle(700,430,100,50);
 Rectangle hard = new Rectangle(700,510,100,50);
 boolean howto = false;
 boolean singleplay = false;
 boolean isClicked = false;
 boolean goback = false;
 boolean ez = false;
 boolean med = false;
 boolean har = false;
 boolean p1HasPowerUp=false;
 boolean p2HasPowerUp=false;
 boolean onePInvincible=false;
 int speed = 50000;
 
 public GfxApp(){
        addMouseListener ( this ) ;
        addKeyListener(this);
    }
    public void startGame(Graphics g){
      g.setColor(Color.GREEN);
     g.fillRect(0,0,1024,768);
     Font start = new Font("SansSerif", Font.BOLD, 100);
     g.setFont(start);
     g.setColor(Color.YELLOW);
     g.drawString("SNAKE", 320, 300);
     
     g.setColor(Color.WHITE);
     g.fillRect((int)button.getX(),(int)button.getY(),(int)button.getWidth(),(int)button.getHeight());
     
     Font startingFont = new Font("SansSerif", Font.BOLD, 20);
     g.setFont(startingFont);
     g.setColor(Color.BLACK);
     g.drawString("START", 465, 630);
     ///Vivians code
     Font choose = new Font("SansSerif", Font.BOLD, 40);  //choose between multiplayer and singleplayer
     g.setFont(choose);
     g.setColor(Color.RED);
     g.drawString("number of players:", 200, 350);
     g.setColor(Color.BLACK);
       Graphics2D g2dLol = (Graphics2D) g;
        g2dLol.setColor(Color.BLACK);
        float[] fan = {10,10, 0};
        BasicStroke thisStroke = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,fan,0);
        g2dLol.setStroke(thisStroke);
        
        
     g.drawRect((int)single.getX(),(int)single.getY(),(int)single.getWidth(),(int)single.getHeight());
     g.setColor(Color.BLACK);
     g.drawRect((int)multi.getX(),(int)multi.getY(),(int)multi.getWidth(),(int)multi.getHeight());
     Font one = new Font("SansSerif", Font.BOLD, 40);  
    g.setFont(one);
     g.setColor(Color.RED);
     g.drawString("one", 240, 400);
     Font two = new Font("SansSerif", Font.BOLD, 40);  
    g.setFont(two);
     g.setColor(Color.RED);
     g.drawString("two", 470, 400);
     if(isClicked && singleplay)  //selected singleplayer
     {
      g.setColor(Color.RED);
      Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        float[] fa = {10,10, 0};
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, fa,0);
        g2d.setStroke(bs);
     g2d.drawRect((int)single.getX(),(int)single.getY(),(int)single.getWidth(),(int)single.getHeight());
     Font oneplay = new Font("SansSerif", Font.BOLD, 50); 
     g.setFont(oneplay);
     g.setColor(Color.BLACK);
     g.drawString("SINGLEPLAYER",250 , 550);
     System.out.println("aethaj");
     }
 if(isClicked && !singleplay)   //selected multiplayer
     {
      g.setColor(Color.RED);
       Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        float[] fa = {10,10, 0};
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, fa,0);
        g2d.setStroke(bs);
     g2d.drawRect((int)multi.getX(),(int)multi.getY(),(int)multi.getWidth(),(int)multi.getHeight());
     Font twoplay = new Font("SansSerif", Font.BOLD, 50); 
     g.setFont(twoplay);
     g.setColor(Color.BLACK);
     g.drawString("MULTIPLAYER",250 , 550);
     System.out.println("uyouip");
     }
     
     Font gamemode = new Font("SansSerif", Font.BOLD, 40);  //text : game mode
     g.setFont(choose);
     g.setColor(Color.BLACK);
     g.drawString("game mode:",240 , 500);
     
     g.setColor(Color.WHITE);
     g.fillRect((int)howtoplay.getX(),(int)howtoplay.getY(),(int)howtoplay.getWidth(),(int)howtoplay.getHeight());
     Font htp = new Font("SansSerif", Font.BOLD, 20);
     g.setFont(htp);
     g.setColor(Color.BLACK);
     g.drawString("HOW TO PLAY", 255, 630);
     
        g.setColor(Color.WHITE);      //choose speed
     g.fillRect((int)easy.getX(),(int)easy.getY(),(int)easy.getWidth(),(int)easy.getHeight());
     g.setColor(Color.WHITE);
     g.fillRect((int)medium.getX(),(int)medium.getY(),(int)medium.getWidth(),(int)medium.getHeight());
     g.setColor(Color.WHITE);
     g.fillRect((int)hard.getX(),(int)hard.getY(),(int)hard.getWidth(),(int)hard.getHeight());
     Font sspeed = new Font("SansSerif", Font.BOLD, 20); 
     g.setFont(sspeed);
     g.setColor(Color.BLUE);
     g.drawString("EASY",725,380); 
     g.setFont(sspeed);
     g.setColor(Color.BLUE);
     g.drawString("MEDIUM",710,460); 
     g.setFont(sspeed);
     g.setColor(Color.BLUE);
     g.drawString("HARD",725, 540);
     if(ez == true)
     {
      g.setColor(Color.RED);
      g.fillRect((int)easy.getX()-7,(int)easy.getY()-7,(int)easy.getWidth()+14,(int)easy.getHeight()+14);
      g.setColor(Color.WHITE);
      g.fillRect((int)easy.getX(),(int)easy.getY(),(int)easy.getWidth(),(int)easy.getHeight());
      g.setFont(sspeed);
      g.setColor(Color.BLUE);
      g.drawString("EASY",725,380); 
     }
     if(med == true)
     {
      g.setColor(Color.RED);
      g.fillRect((int)medium.getX()-7,(int)medium.getY()-7,(int)medium.getWidth()+14,(int)medium.getHeight()+14);
      g.setColor(Color.WHITE);
      g.fillRect((int)medium.getX(),(int)medium.getY(),(int)medium.getWidth(),(int)medium.getHeight());
      g.setFont(sspeed);
      g.setColor(Color.BLUE);
      g.drawString("MEDIUM",710,460);
     }
     if(har == true)
     {
      g.setColor(Color.RED);
      g.fillRect((int)hard.getX()-7,(int)hard.getY()-7,(int)hard.getWidth()+14,(int)hard.getHeight()+14);
      g.setColor(Color.WHITE);
      g.fillRect((int)hard.getX(),(int)hard.getY(),(int)hard.getWidth(),(int)hard.getHeight());
      g.setFont(sspeed);
      g.setColor(Color.BLUE);
      g.drawString("HARD",725, 540);
     }
     
    }
 public void theMethod(){
  bestPlayerOneSize=Math.max(playerOneSize,bestPlayerOneSize);
  playerOne = new Snake(250,500,5,0.0,10);
  playerTwo = new Snake(750,500,5,Math.PI,10);
  onePCake = new Food();
  onePCake.makeFood(50);
  powerUpCake = new Food();
  powerUpCake.makeFood(10);
  invincibilityMelon = new Food();
  invincibilityMelon.setPath("melon.png");
  invincibilityMelon.makeFood(5);
  playerOneSize=5;
  singlePool = new Pool();
  mySpace = new Space();
  autoBot = new Enemy("enemyGIF.gif");
  autoBot.makeEnemies(10);
 }
 public void paint (Graphics g)
 {
  if (cakeCount>25){
   p1HasPowerUp=false;
   p2HasPowerUp=false;
   onePInvincible=false;
  }
  cakeCount++;
  if (!started) startGame(g);
  if(!started && howto)
  {
   drawInstrucs(g);
  }
  if (started && !singleplay){
  mySpace.drawSpace(g);
  drawScores(g);
  draw2PCakes(g);
  
  
  moveP1(g);
  if (p1HasPowerUp) moveP1(g);
  moveP2(g);
  if (p2HasPowerUp) moveP2(g);
  checkForHits(g);
  twoPlayerCakeCheck(g);
  delay(speed);
  repaint();
  }
  if(started && singleplay)
  {
   singlePool.drawPool(g);
   drawP1ScoreCounter(g);
   drawP1Cake(g);
   autoBot.moveEnemies(10,10);
   drawEnemies(g);
   //need to check for enemy hits
   onePlayerMoveP1(g);
   //onePlayerCheckHit(g);
   onePlayerHitManager(g);
   onePlayerCheckCake(g);
   delay(speed);
   repaint();
  }
 }
public void drawInstrucs(Graphics g)
 {
  g.setColor(Color.GREEN);
     g.fillRect(0,0,1024,768);
     Font instrucs1 = new Font("SansSerif", Font.BOLD, 50);  
     g.setFont(instrucs1);
     g.setColor(Color.BLACK);
     g.drawString("HOW TO PLAY",320 , 110);
     Font instrucs2 = new Font("SansSerif", Font.BOLD, 30); 
     g.setFont(instrucs2);
     g.setColor(Color.BLACK);
     g.drawString("Player 1 uses W-A-S-D",90 , 155);
     Font instrucs3 = new Font("SansSerif", Font.BOLD, 30);  
     g.setFont(instrucs3);
     g.setColor(Color.BLACK);
     g.drawString("Player 2 uses Arrow Keys",565 , 155);
     Font instrucs9 = new Font("SansSerif", Font.BOLD, 30);  
     g.setFont(instrucs9);
     g.setColor(Color.BLACK);
     g.drawString("Don't run into the edge of the screen.",90 , 190);
     
     Font instrucs4 = new Font("SansSerif", Font.BOLD, 40); 
     g.setFont(instrucs4);
     g.setColor(Color.BLACK);
     g.drawString("SINGLEPLAYER:",90 , 250);
     
     Font instrucs5 = new Font("SansSerif", Font.BOLD, 30);  
     g.setFont(instrucs5);
     g.setColor(Color.BLACK);
     g.drawString("Cupcakes: grows longer.",90 , 290);
     g.drawImage(onePCake.getImg(),(int)50,(int)263,30,30,null,null);
     g.drawString("Watermelons: invinciblility for seconds; turns gold",90 , 330);
     g.drawImage(invincibilityMelon.getImg(),(int)45,(int)305,35,25,null,null);
     Font instrucs6 = new Font("SansSerif", Font.BOLD, 30);  
     g.setFont(instrucs6);
     g.setColor(Color.BLACK);
     g.drawString("Avoid running into yourself or the borders.",90 , 370);
     g.drawString("Avoid the enemies.",90 , 410);
     
     Font instrucs7 = new Font("SansSerif", Font.BOLD, 40);  
     g.setFont(instrucs7);
     g.setColor(Color.BLACK);
     g.drawString("MULTIPLAYER:",90 , 475);
     Font instrucs8 = new Font("SansSerif", Font.BOLD, 30);  
     g.setFont(instrucs8);
     g.setColor(Color.BLACK);
     g.drawString("Avoid the walls and the stars.",90 , 515);
     g.drawString("Don't run into the other player.",90 , 555);
     g.drawString("Cupcakes: double speed",90 , 595);
     g.drawImage(onePCake.getImg(),(int)50,(int)570,30,30,null,null);
     
     g.setColor(Color.WHITE);
     g.fillRect((int)backtostart.getX(),(int)backtostart.getY(),(int)backtostart.getWidth(),(int)backtostart.getHeight());
     Font back = new Font("SansSerif", Font.BOLD, 22);
     g.setFont(back);
     g.setColor(Color.BLACK);
     g.drawString("BACK", 780, 680);
 }
 public void drawP1ScoreCounter(Graphics g){
  Font start = new Font("SansSerif", Font.BOLD, 50);
  g.setColor(Color.BLACK);
     g.setFont(start);
  g.drawString("Player 1 Length: " + playerOneSize,50,150);
  g.drawString("Previous Best Score: " + bestPlayerOneSize,350,750);
 }
 public void drawP1Cake(Graphics g){
  for (Rectangle rNum: onePCake.getFood()){
  // g.setColor(Color.GREEN);
  // g.drawRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
  g.drawImage(onePCake.getImg(),(int)rNum.getX(),(int)rNum.getY(),10,10,null,null);
  }
  for (Rectangle thaatNum:invincibilityMelon.getFood()){
  g.drawImage(invincibilityMelon.getImg(),(int)thaatNum.getX(),(int)thaatNum.getY(),15,10,null,null);
 
  }
 }
 
 public void draw2PCakes(Graphics g){
  for (Rectangle rNum: powerUpCake.getFood()){
  // g.setColor(Color.GREEN);
  // g.drawRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
  g.drawImage(powerUpCake.getImg(),(int)rNum.getX(),(int)rNum.getY(),10,10,null,null);
  }
 }
 
 public void drawEnemies(Graphics g){
  for (Rectangle rNum: autoBot.getEnemies()){

 // autoBot.paintIcon(autoBot.getImg(), g, (int)rNum.getX(),(int)rNum.getY());
  g.drawImage(autoBot.getImg(),(int)rNum.getX(),(int)rNum.getY(),40,40,null,null);
  }
 }
 
 
 
 public void onePlayerCheckCake(Graphics g){
  Rectangle p1Head = playerOne.getParts().get(playerOne.getParts().size()-1);
  for (Rectangle rNum: onePCake.getFood()){
   if (rNum.intersects(p1Head)) {
    playerOneSize++;
    onePCake.getFood().remove(rNum);
    repaint();
    }
   
 }
 
 for (Rectangle thaatNum: invincibilityMelon.getFood()){
   if (thaatNum.intersects(p1Head)) {
    onePInvincible=true;
    invincibilityMelon.getFood().remove(thaatNum);
    cakeCount=0;
    repaint();
    }
   
 }
 
 
 }
 
 public void twoPlayerCakeCheck(Graphics g){
  Rectangle p1Head = playerOne.getParts().get(playerOne.getParts().size()-1);
  for (Rectangle rNum: powerUpCake.getFood()){
   if (rNum.intersects(p1Head)) {
    p1HasPowerUp=true;
    powerUpCake.getFood().remove(rNum);
    cakeCount=0;
    repaint();
    }
 }
 Rectangle p2Head = playerTwo.getParts().get(playerTwo.getParts().size()-1);
  for (Rectangle thatNum: powerUpCake.getFood()){
   if (thatNum.intersects(p2Head)) {
    p2HasPowerUp=true;
    powerUpCake.getFood().remove(thatNum);
    cakeCount=0;
    repaint();
    }
 }
 }
 
 
 public void drawScores(Graphics g){
 Font start = new Font("SansSerif", Font.BOLD, 30);
     g.setFont(start);
     g.setColor(Color.WHITE);
  g.drawString("Player 1 score: " + playerOneScore,110,150);
  g.drawString("Player 2 score: " + playerTwoScore,550,150);
 }
 public void moveP1(Graphics g){
  playerOne.move();
  for (Rectangle rNum: playerOne.getParts()){
   g.setColor(Color.RED);
   g.fillRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
  }
 
 }
 public void moveP2(Graphics g){
  playerTwo.move();
  for (Rectangle rNum: playerTwo.getParts()){
   g.setColor(Color.BLUE);
   g.fillRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
  }
 }
 public void checkForHits(Graphics g){
  if (checkP1Hit(g)&&!checkP2Hit(g)){
   playerTwoScore++;
   g.drawString("Snake 1 dies!",20,20);
  // delay(500000);//insert arbitrary value to display win graphic
   theMethod();
   repaint();
  //Delay for 5 seconds then restart
  // playerOneVictory();
  //increment playerOne wins
  //RESET SNAKE - otherwise they keep going
  //display playerone wins
  //have a quick graphic like something on the screen saying playeronewins
  //option to continue playing? or, in game selection, select # of games to play and
  //based on the # of games do something
  } else if (checkP2Hit(g)&&checkP1Hit(g)){
   tieGame(g);
   
  } else if (checkP2Hit(g)&&!checkP1Hit(g)){
   playerOneScore++; // TRY TO COMMENT ALL THIS BELOW OUT TO SHOW THAT THE SNAKE
   //LOST WHENEVER IT DIES
  // playerTwoVictory();
   g.drawString("Snake 2 dies!",20,20);
  // delay(500000);//insert arbitrary value to display win graphic
   theMethod();
   repaint();
  } else;
 }
 public void onePlayerHitManager(Graphics g){
  if (onePlayerCheckHit(g)){
   g.drawString("Your snake dies!",200,200);
   delay(50000);
   theMethod();
   repaint();
  }
 }
 public boolean onePlayerCheckHit(Graphics g){
  if (onePInvincible) return false;
  Rectangle p1Head = playerOne.getParts().get(playerOne.getParts().size()-1);
  for (int i=0; i<playerOne.getParts().size()-1; i++){
   Rectangle rNum = playerOne.getParts().get(i);
   if (p1Head.intersects(rNum)) {
    //g.fillRect(500,0,500,500);
    //g.drawString("Your snake dies!",200,200);
    return true;
   }
  for (Rectangle mahNum: singlePool.getBorders().keySet()){
   if (p1Head.intersects(mahNum)) {
    return true;
   }
  }
  for (Rectangle thatNum: autoBot.getEnemies()){
   if (p1Head.intersects(thatNum)) {
    return true;
   }
  }
  
  }
  return false;
 }
 
 protected void onePlayerMoveP1(Graphics g){
  playerOne.move();
  if (playerOne.getParts().size()>playerOneSize){
  // playerOne.getParts().removeRange(0,playerOne.getParts().size()-playerOneSize);
  for (int i=0; i<playerOne.getParts().size()-playerOneSize;i++){
   playerOne.getParts().remove(0);
  }
  }
  for (Rectangle rNum: playerOne.getParts()){
   g.setColor(Color.RED);
   g.fillRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
   if (onePInvincible){
   
   g.setColor(Color.YELLOW);
   g.drawRect((int)rNum.getX(),(int)rNum.getY(),(int)rNum.getWidth(),(int)rNum.getHeight());
   }
  }
  
 }
 
 public boolean checkP1Hit(Graphics g){
  //add thing here to detect if P1 hits itself
  Rectangle p1Head = playerOne.getParts().get(playerOne.getParts().size()-1);
  for (Rectangle rNum: playerTwo.getParts()){
   if (p1Head.intersects(rNum)){
     //g.fillRect(0,0,500,500);
     g.drawString("Snake 1 dies!",20,20);
     return true;
      }
  }
  for (Rectangle mahNum: mySpace.getBorders().keySet()){
   if (p1Head.intersects(mahNum)) {
    return true;
   }
  }
  
  return false;
 }
 public boolean checkP2Hit(Graphics g){
  Rectangle p2Head = playerTwo.getParts().get(playerTwo.getParts().size()-1);
  for (Rectangle rNum: playerOne.getParts()){
   if (p2Head.intersects(rNum)) {
    //g.fillRect(500,0,500,500);
    g.drawString("Snake 2 dies!",200,200);
    return true;
   }
   for (Rectangle mahNum: mySpace.getBorders().keySet()){
   if (p2Head.intersects(mahNum)) {
    return true;
   }
  }
  }
  return false;
 }
 public void tieGame(Graphics g){
  g.drawString("It was a tie",400,400);
 // delay(500000);
  theMethod();
  repaint();
 }
 public void makeSnakes(){
  
 }
 
 
 
 private void delay(double n)
 {
  for (double k = 1; k < n; k+=0.001);
 }
   
   
 public void moveIt(KeyEvent evt){
  //might want to use a direction variable string
  //instead of these double values bc
  //might want a thing such that the snake can't go backwards into itself
          switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                playerTwo.setAngle(Math.PI/2);
                break;
            case KeyEvent.VK_UP:
                playerTwo.setAngle(3*Math.PI/2);
                break;
            case KeyEvent.VK_LEFT:
                playerTwo.setAngle(Math.PI);
                break;
            case KeyEvent.VK_RIGHT:
                playerTwo.setAngle(0);
                break;
            case KeyEvent.VK_S:
          playerOne.setAngle(Math.PI/2);
                break;
            case KeyEvent.VK_W:
            playerOne.setAngle(3*Math.PI/2);
                break;
            case KeyEvent.VK_A:
          playerOne.setAngle(Math.PI);
                break;
            case KeyEvent.VK_D:
          playerOne.setAngle(0);
                break;
        }
    }
 int count = 0; 
     int count2 = 0;  
 public void mouseClicked(MouseEvent e) {
 
        System.out.println("X Coord: " + e.getX());
        System.out.println("Y Coord: " +e.getY());
     /*if(button.contains(e.getX(),e.getY()))
     {
      started = true;
      repaint();
     }*/
          if(button.contains(e.getX(),e.getY()))
     {
      started = true;
      repaint();
     }
     if(single.contains(e.getX(),e.getY()))
     {
       count +=1;
      if(count % 2 == 1)
      {
       singleplay = true;
       isClicked = true;
      }
      else
      {
       singleplay = false;
       isClicked = false;
      }
     
      
      repaint();
     }
     if(multi.contains(e.getX(),e.getY()))
     {
      count +=1;
      if(count % 2 == 1)
      {
       isClicked = true;
      }
      else
      {
       isClicked = false;
      }
      repaint();
     }
     if(howtoplay.contains(e.getX(),e.getY()))
     {
      howto = true;
      repaint();
     }
      if(backtostart.contains(e.getX(),e.getY()))
     {
      count2 +=1;
      if(count2 % 2 == 1)
      {
       goback = true;
       howto = false;
      }
      else
      {
       goback = false;
      }
      repaint();
     }
          if(easy.contains(e.getX(),e.getY()))
     {
      speed = 200000;
      ez = true;
      med = false;
      har = false;
      repaint();
     }
     if(medium.contains(e.getX(),e.getY()))
     {
      speed = 100000;
      med = true;
      ez = false;
      har = false;
      repaint();
     }
     if(hard.contains(e.getX(),e.getY()))
     {
      speed = 50000;
      har = true;
      ez = false;
      med = false;
      repaint();
     }
     
    } 
   // @Override
    public void mousePressed(MouseEvent e) {

     //   System.out.println(e.getX());
    }
    //@Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("success");
    }
    //@Override
    public void mouseExited(MouseEvent e) {
        // System.out.println("success");
    }
    // @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("success");
    }
 public void keyPressed(KeyEvent evt){
  moveIt(evt);
 } 
  
 public void keyReleased(KeyEvent evt){
 // moveIt(evt);
 } 
  
 public void keyTyped(KeyEvent evt){
 // moveIt(evt);
 } 
}




class Pool extends Frame{
 HashMap <Rectangle, Color> poolRects;
 HashMap <Rectangle, Color> borderRects;
// ArrayList <Rectangle> poolRects;
 public Pool() {
  poolRects = new HashMap<Rectangle,Color>();
  borderRects = new HashMap<Rectangle,Color>();
 //Blue background
 // Color c = new Color(174,255,255);
//  g.setColor(c);
  poolRects.put(new Rectangle(0,0,1024,768),new Color(174,255,255));
  
 // g.setColor(Color.BLACK);
 //g.fillRect(0,1000,500,2000);
  
  
    //edges
 // g.setColor(Color.WHITE);
  borderRects.put(new Rectangle(60,70,904,30),Color.WHITE); // top
  borderRects.put(new Rectangle(60,70,30,548),Color.WHITE); // left
  borderRects.put(new Rectangle(934,70,30,548),Color.WHITE); //right
  borderRects.put(new Rectangle(60,618,904,30),Color.WHITE); //bottom
  
  //drawPinkSpots
 // g.setColor(Color.PINK);
  borderRects.put(new Rectangle(0,0,1024,70),Color.PINK); // top
  borderRects.put(new Rectangle(0,0,60,768),Color.PINK); // left
  borderRects.put(new Rectangle(964,0,60,768),Color.PINK); //right
  borderRects.put(new Rectangle(60,648,904,110),Color.PINK); //bottom
    borderRects.put(new Rectangle(60,300,150,50),Color.WHITE);//add new boundaries like this
  borderRects.put(new Rectangle(60,350,130,20),Color.WHITE);
  borderRects.put(new Rectangle(60,280,130,20),Color.WHITE);
  borderRects.put(new Rectangle(60,260,90,20),Color.WHITE);
  borderRects.put(new Rectangle(60,370,90,20),Color.WHITE);
   borderRects.put(new Rectangle(250,548,500,70),Color.WHITE);
 borderRects.put(new Rectangle(220,568,30,50),Color.WHITE);
 borderRects.put(new Rectangle(750,568,30,50),Color.WHITE);
 borderRects.put(new Rectangle(205,603,15,15),Color.WHITE);
 borderRects.put(new Rectangle(780,603,15,15),Color.WHITE);
 borderRects.put(new Rectangle(550,350,60,60),Color.WHITE);
  
  
 // borderRects.put(new Rectangle(),Color.PINK);//add new boundaries like this
  //borderRects.put(new Rectangle(),Color.PINK);
  //borderRects.put(new Rectangle(),Color.PINK);

 
 // g.setColor(Color.BLACK);
//  poolRects.put(new Rectangle(80,90,862,597),Color.BLACK);
 }
 public void drawPool(Graphics g){
  for (Rectangle thisRect : poolRects.keySet()){
   g.setColor(poolRects.get(thisRect));
   g.fillRect((int)thisRect.getX(),(int)thisRect.getY(),(int)thisRect.getWidth(),(int)thisRect.getHeight());
  }
  g.setColor(Color.BLACK);
 // g.drawRect(130,90,862,597);
  drawBorders(g);
 }
 public void drawBorders(Graphics g){
  for (Rectangle thisRect : borderRects.keySet()){
   g.setColor(borderRects.get(thisRect));
   g.fillRect((int)thisRect.getX(),(int)thisRect.getY(),(int)thisRect.getWidth(),(int)thisRect.getHeight());
  }
 } 
  
 public HashMap <Rectangle, Color>  getBorders(){
  return borderRects;
 }
 
}

class Space extends Frame{
 HashMap <Rectangle, Color> spaceRects;
 HashMap <Rectangle, Color> borderRects;
// ArrayList <Rectangle> poolRects;
 public Space() {
  spaceRects = new HashMap<Rectangle,Color>();
  borderRects = new HashMap<Rectangle,Color>();
 //Blue background
 // Color c = new Color(174,255,255);
//  g.setColor(c);
  spaceRects.put(new Rectangle(0,0,1024,768),Color.BLACK);
  
 // g.setColor(Color.BLACK);
 //g.fillRect(0,1000,500,2000);
  
  
    //edges
 // g.setColor(Color.WHITE);
  borderRects.put(new Rectangle(60,70,904,30),Color.WHITE); // top
  borderRects.put(new Rectangle(60,70,30,548),Color.WHITE); // left
  borderRects.put(new Rectangle(934,70,30,548),Color.WHITE); //right
  borderRects.put(new Rectangle(60,618,904,30),Color.WHITE); //bottom
  
  //drawPinkSpots
 // g.setColor(Color.PINK);
  borderRects.put(new Rectangle(0,0,1024,70),Color.BLACK); // top
  borderRects.put(new Rectangle(0,0,60,768),Color.BLACK); // left
  borderRects.put(new Rectangle(964,0,60,768),Color.BLACK); //right
  borderRects.put(new Rectangle(60,648,904,110),Color.BLACK); //bottom
  for(int x = 0; x<30; x++)
  {
   int ranx = (int)(Math.random()*784)+120;
   int rany = (int)(Math.random()*458)+120;
   int ransize = (int)(Math.random()*15)+3;
   int rancolor1 = (int)(Math.random()*256);
   int rancolor2 = (int)(Math.random()*256);
   int rancolor3 = (int)(Math.random()*256);
   borderRects.put(new Rectangle(ranx,rany,ransize,ransize),new Color(rancolor1,rancolor2,rancolor3));
  }
  
  
 // borderRects.put(new Rectangle(),Color.PINK);//add new boundaries like this
  //borderRects.put(new Rectangle(),Color.PINK);
  //borderRects.put(new Rectangle(),Color.PINK);

 
 // g.setColor(Color.BLACK);
//  poolRects.put(new Rectangle(80,90,862,597),Color.BLACK);
 }
  public void drawSpace(Graphics g){
  for (Rectangle thisRect : spaceRects.keySet()){
   g.setColor(spaceRects.get(thisRect));
   g.fillRect((int)thisRect.getX(),(int)thisRect.getY(),(int)thisRect.getWidth(),(int)thisRect.getHeight());
  }
  g.setColor(Color.BLACK);
 // g.drawRect(130,90,862,597);
  drawBorders(g);
 }
 public void drawBorders(Graphics g){
  for (Rectangle thisRect : borderRects.keySet()){
   g.setColor(borderRects.get(thisRect));
   g.fillRect((int)thisRect.getX(),(int)thisRect.getY(),(int)thisRect.getWidth(),(int)thisRect.getHeight());
  }
 } 
  
 public HashMap <Rectangle, Color>  getBorders(){
  return borderRects;
 }
}



