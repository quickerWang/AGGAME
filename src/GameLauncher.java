public class GameLauncher {


    // set DEMO to false to use your code (true uses DemoGame.class)
//  private static final boolean DEMO = true;
    private static final boolean DEMO = false;

    public static void main(String[] args) {
        if (DEMO) {       // reference game:
            //   - play and observe first the mechanism of the demo
            //     to understand the basic game
            //   - go back to the demo anytime you don't know what the
            //     next step to implement and to test are. you should always be
            //     clear and concrete about the ~5 lines you are trying to code and
            //     how to validate them
            //         figure out according to the game play
            //         (the sequence of display and action) how the functionality
            //         you are implementing next is supposed to operate

            // It's critical to have a plan for each piece of code: follow, understand
            // and study the assignment description details; and explore the basic game.
            // You should always know what you are doing (your current small goal)
            // before implementing that piece or talk to us.

            System.out.println("Running the demo: DEMO=" + DEMO);
            //constructor for client to adjust the game window size
            //TRY different values

        } else {
            System.out.println("Running student game: DEMO=" + DEMO);
            // !DEMO   -> your code should execute those lines when you are
            // implementing your game

            // TEST 1: with parameterless constructor
            JiYUANRENGAME game = new JiYUANRENGAME();

            // TEST 2: with constructor specifying grid size
            //IT SHOULD ALSO WORK as long as height < width
//      ScrollingGame game = new ScrollingGame(10, 20, 4, 3);

            game.play();
        }
    }
}