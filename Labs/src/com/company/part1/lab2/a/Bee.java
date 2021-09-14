package com.company.part1.lab2.a;


public class Bee implements Runnable{
    private final Forest forest;
    private final long duration;
    public Bee(Forest forest, long duration) {
        this.duration = duration;
        this.forest = forest;
    }

    @Override
    public void run() {
        ForestLine line = forest.getNextLine();
        while (!Thread.interrupted()&&line != null)
        {
            for(int i = 0; i < line.getSize(); i++)
            {
                try {
                    if(line.check(i)) {
                        try {
                            forest.punishWinnie();
                            return;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            line = forest.getNextLine();
        }
    }
}
