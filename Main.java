import java.util.ArrayList;

public class Main {
    // InterruptedException necessary for Thread.sleep (used to ensure Tracker onComplete() function finishes)
    public static void main(String[] args) throws InterruptedException {
        GameDriver game = new GameDriver();
        game.play();

//        ArrayList<Integer> nums = new ArrayList<>();
//        nums.add(0);
//        for (Integer i : nums) System.out.println(i);
    }
}
