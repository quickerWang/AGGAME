import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class JiYUANRENGAME {
    //the number of milliseconds since the game main loop started.
    private int msElapsed;
    //psuse time
    private int pauseTime;
    //the gow number of game
    private int height = 5;
    //the col number of game
    private int weight = 10;
    //the start row number of U
    private int userRow = 0;
    //times of G
    private int timesGet;
    //times of A
    private int timesAvoid;
    private int t;
    //game view
    private GameGrid grid;
    // is pause
    private boolean pause = false;
    //游戏是否开始
    private boolean splash;
    //鼠标点击次数
    private int gameClick = 0;

    JiYUANRENGAME() {
        this.init();
    }

    private void init() {
        this.grid = new GameGrid(this.height, this.weight, "ink.png", (String) null);
        this.splash = true;
        this.t = 0;
        this.msElapsed = 0;
        this.pauseTime = 100;
        this.timesGet = 0;
        this.timesAvoid = 0;
        this.updateTitle();
        this.grid.setCellImage(new Location(this.userRow, 0), "user.gif");
    }


    /**
     * 处理鼠标事件
     */
    public boolean handleMouseClick() {
        Location var1 = this.grid.checkLastLocationClicked();
        if (var1 != null) {
            System.out.println("You clicked on a square " + var1);
            if (this.splash) {
                this.splash = false;
            } else {
                if (this.grid.getColor(var1) == null) {
                    this.grid.setColor(var1, Color.YELLOW);
                } else {
                    this.grid.setColor(var1, (Color) null);
                }

                ++this.gameClick;
                System.out.println("total click " + this.gameClick);
                if (this.gameClick == 8) {
                    this.grid.setGameBackground("ink.png");
                } else if (this.gameClick == 15) {
                    this.grid.setSplash("ink.png");
                    this.splash = true;
                }
            }

            return true;
        } else {
            return false;
        }
    }


    /**
     * 入口函数，循环调用其他函数
     */

    public void play() {
        boolean var1;

        //to judge if the game is started
        if (this.splash) {
            var1 = false;
            //wait for mouse click
            while (!var1) {
                var1 = this.handleMouseClick();
                this.grid.setTitle("Intro");
            }

            this.splash = false;
            this.grid.setSplash((String) null);
            this.grid.setCellImage(new Location(this.userRow, 0), "user.gif");
        }

        GameGrid gameGrid;
        while (!this.isGameOver()) {
            gameGrid = this.grid;
            GameGrid.sleep(this.pauseTime);
            this.handleKeyPress();
            this.handleMouseClick();
            if (!this.pause) {
                this.msElapsed += this.pauseTime;
                ++this.t;
                // 控制屏幕向左滑动的速度，即游戏的快慢，% N，N越大，速度越慢
                if (this.t % 3 == 0) {
                    this.scrollLeft();
                    this.populateRightEdge();
                }
                this.updateTitle();
            }
        }

        var1 = false;
        this.grid.setSplash("ink.png");

        while (true) {
            int SCORE_TO_WIN = 100;
            if (this.getScore() >= SCORE_TO_WIN) {
                this.grid.setTitle("YOU WON!!");
            } else {
                this.grid.setTitle("GAME OVER: YOU LOSE!");
            }

            var1 = this.handleMouseClick();
            gameGrid = this.grid;
            GameGrid.sleep(this.pauseTime);
            if (var1) {
                System.out.println("a click");
            }

            this.handleKeyPress();
        }
    }

    /**
     * 处理键盘事件
     */
    private void handleKeyPress() {
        int var1 = this.grid.checkLastKeyPressed();
        if (var1 == KeyEvent.VK_Q) {
            System.exit(0);
        } else if (var1 == KeyEvent.VK_S) {
            System.out.println("save screen...");
            this.grid.save("capture.png");
        } else if (var1 == KeyEvent.VK_D) {
            if (this.grid.getLineColor() == null) {
                this.grid.setLineColor(Color.RED);
            } else {
                this.grid.setLineColor((Color) null);
            }

            System.out.println("Toggle linecolor for debugging");
        } else if (var1 == KeyEvent.VK_P) {
            System.out.println("Try S, D, and clicking in square");
            this.pause = !this.pause;
        } else if (var1 == KeyEvent.VK_T) {
            boolean var2 = this.t % 3 == 0;
            System.out.println("pauseTime: " + this.pauseTime + ", msElapsed: " + this.msElapsed + ", interval " + var2 + ", paused: " + this.pause);
        } else if (!this.pause) {
            if (var1 == KeyEvent.VK_UP && this.userRow > 0) {
                this.grid.setCellImage(new Location(this.userRow, 0), (String) null);
                --this.userRow;
//                System.out.println("zhaoj:moved down");
                this.grid.setCellImage(new Location(this.userRow, 0), "user.gif");
            } else if (var1 == KeyEvent.VK_DOWN && this.userRow < this.grid.getNumRows() - 1) {
                this.grid.setCellImage(new Location(this.userRow, 0), (String) null);
                ++this.userRow;
//                System.out.println("zhaoj:moved up");
                this.grid.setCellImage(new Location(this.userRow, 0), "user.gif");
            } else if (var1 == 44 && this.pauseTime < 500) {
                this.pauseTime += 10;
                System.out.println("new pauseTime " + this.pauseTime);
            } else if (var1 == 46 && this.pauseTime > 10) {
                this.pauseTime -= 10;
                System.out.println("new pauseTime " + this.pauseTime);
            }
        } else if (this.pause && var1 != -1) {
            System.out.println("The game is paused, thus not taking key event except 'P', 'Q', 'T'");
        }

    }


    /**
     * 新的A和G出现
     */
    private void populateRightEdge() {
        Random var1 = new Random();

        for (int var2 = 0; var2 < this.grid.getNumRows(); ++var2) {
            Location var3 = new Location(var2, this.grid.getNumCols() - 1);
            if (this.grid.getCellImage(var3) == null) {
                int var4 = var1.nextInt(10) + 1;
                if (var4 == 1) {
                    this.grid.setCellImage(var3, "get.gif");
                } else if (var4 == 4) {
                    this.grid.setCellImage(var3, "avoid.gif");
                }
            }
        }

    }

    /***
     * 将cell向左移动，移动之后设置该cell的image为null
     * @param var1：cell row
     * @param var2: cell column
     * @param var3: cell image，not null and not ""user.gif(调用该函数前做了保证)
     */
    private void moveLeft(int var1, int var2, String var3) {
        if (var2 > 0) {
            Location var4 = new Location(var1, var2 - 1);
            if (this.grid.getCellImage(var4) == null) {
                this.grid.setCellImage(var4, var3);
            } else {
                this.handleCollision(new Location(var1, var2));
            }
        }

        this.grid.setCellImage(new Location(var1, var2), (String) null);
    }

    public void handleCollision(Location var1) {
        if (this.grid.getCellImage(var1).equals("get.gif")) {
            ++this.timesGet;
            System.out.println("G");
        } else if (this.grid.getCellImage(var1).equals("avoid.gif")) {
            ++this.timesAvoid;
            System.out.println("A");
        }

    }

    /**
     * 将整个游戏的grid的向左移动
     */
    private void scrollLeft() {
        for (int var1 = 0; var1 < this.grid.getNumRows(); ++var1) {
            for (int var2 = 0; var2 < this.grid.getNumCols(); ++var2) {
                String var3 = this.grid.getCellImage(new Location(var1, var2));
                if (var3 != null && var3 != "user.gif") {
                    this.moveLeft(var1, var2, var3);
                }
            }
        }

    }

    /**
     * 得到当前分数
     * @return
     */
    private int getScore() {
        return this.timesGet * 10;
    }

    /**
     * 更新标题
     */
    private void updateTitle() {
        this.grid.setTitle("JIYUANRENGAME Game:  " + this.getScore());
    }

    /**
     * 游戏是否结束
     * @return
     */
    public boolean isGameOver() {
        return this.getScore() >= 100 || this.timesAvoid > 10;
    }

    public static void main(String[] var0) {
        System.out.println("Running the JIYUANRENGAME game");
        (new JiYUANRENGAME()).play();
    }
}
