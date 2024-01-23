import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener,KeyListener{
     private class Tile{
          int x;
          int y;

          Tile(int x, int y){
               this.x=x;
               this.y=y;
          }
     }

     int boardWidth;
     int boardHeight;
     int tileSize=20;
     
     //This is the snake
     Tile snakeHead;
     ArrayList <Tile> snakeBody;

     //This is the food
     Tile food;
     Random random;



     //gameLogic
     Timer gameLoop;
     int velocityX;
     int velocityY;
     boolean gameOver=false;



     SnakeGame(int boardWidth,int boardHeight){
          this.boardWidth=boardWidth;
          this.boardHeight=boardHeight;
          setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
          setBackground(Color.BLACK);
          addKeyListener(this);
          setFocusable(true);

          snakeHead= new Tile(5,5);
          snakeBody=new ArrayList<Tile>();

          food=new Tile(10,10);
          random= new Random();
          placeFood();

          velocityX=0;
          velocityY=0;

          gameLoop= new Timer(100,this);
          gameLoop.start();

     }

     public void paintComponent(Graphics g){
          super.paintComponent(g);
          draw(g);
     }

     public void draw(Graphics g){
          // //Grid
          // for(int i=0;i< boardWidth/tileSize ;i++){
          //      g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
          //      g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
          //      g.setColor(Color.BLUE);
          // }
          //Snake Head
          g.setColor(Color.green);
          g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize ,tileSize, tileSize);

          //SnakeBody
          for(int i=0;i<snakeBody.size();i++){
               Tile SnakePart=snakeBody.get(i);
               g.fillRect(SnakePart.x * tileSize, SnakePart.y * tileSize, tileSize, tileSize);
          }

          //Food
          g.setColor(Color.red);
          g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);


          //Score
          g.setFont(new Font("Arial",Font.PLAIN,16));
          if(gameOver){
               g.setColor(Color.RED);
               g.drawString("GAME OVER:"+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
          }
          else{
               g.setColor(Color.GREEN);
               g.drawString("SCORE:"+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
          }

     }
     public void placeFood(){
          food.x=random.nextInt(boardWidth/tileSize);
          food.y=random.nextInt(boardHeight/tileSize);
     }
     public boolean collision(Tile tile1, Tile tile2){
          return tile1.x==tile2.x && tile1.y==tile2.y;
     }

     public void move(){
          //Eating food
          if(collision(snakeHead, food)){
               snakeBody.add(new Tile(food.x,food.y));
               placeFood();
          }          
          
          //SnakeBody
          for(int i=snakeBody.size()-1;i>=0;i--){
               Tile snakePart= snakeBody.get(i);
               if(i==0){
                    snakePart.x=snakeHead.x;
                    snakePart.y=snakeHead.y;
               }else{
                    Tile prevSnakePart=snakeBody.get(i-1);
                    snakePart.x=prevSnakePart.x;
                    snakePart.y=prevSnakePart.y;
               }
          }


          //SnakeHead
          snakeHead.x+=velocityX;
          snakeHead.y+=velocityY;


          //GameOver
          for(int i=0;i<snakeBody.size()-1;i++){
               Tile snakePart=snakeBody.get(i);
               if(collision(snakeHead, snakePart)){
                    gameOver=true;
               }
          }
          if(snakeHead.x*tileSize<0 || snakeHead.y*tileSize>boardWidth|| 
          snakeHead.x*tileSize>boardHeight || snakeHead.y*tileSize<0){
               gameOver=true;
          }
     }

     @Override
     public void actionPerformed(ActionEvent e) {
          // TODO Auto-generated method stub
          repaint();
          // food.x=random.nextInt(boardWidth/tileSize);
          // food.y=random.nextInt(boardHeight/tileSize);
          // snakeHead.x++;
          // if(snakeHead.x>=boardWidth){
          //      snakeHead.x=0*tileSize;
          // }
          move();
          if(gameOver==true){
               gameLoop.stop();
          }
          
     }
     
     @Override
     public void keyPressed(KeyEvent e) {
          // TODO Auto-generated method stub
          if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
               velocityX=0;
               velocityY=-1;
          }if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
               velocityX=0;
               velocityY=1;
          }if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
               velocityX=-1;
               velocityY=0;
          }if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
               velocityX=1;
               velocityY=0;
          }

     }




     //We donot need these
     @Override
     public void keyTyped(KeyEvent e) {
     }
     @Override
     public void keyReleased(KeyEvent e) {
     }
     
}
