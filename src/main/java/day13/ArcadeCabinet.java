package day13;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class ArcadeCabinet {
    private InputStream input;
    private int[][] tiles;
    private Thread t;
    private int score;
    private int paddleX;
    private int paddleY;
    private int ballX;
    private int ballY;
    private boolean runAI;
    private boolean ballSeen;
    private boolean paddleSeen;
    private OutputStream output;
    private boolean gameStarted;

    public ArcadeCabinet() {
        this.output = new ByteArrayOutputStream();
    }

    public void connectInput(InputStream input) {
        this.input = input;
    }

    public void run() {
        t = new Thread(() -> doRun());
        t.start();
    }

    public void doRun() {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(output));

        try {
            while(true) {
                String read = in.readLine();
                if (read == null) {
                    break;
                }

                int x = Integer.parseInt(read);

                read = in.readLine();
                if (read == null) {
                    break;
                }

                int y = Integer.parseInt(read);

                read = in.readLine();
                if (read == null) {
                    break;
                }

                int tileId = Integer.parseInt(read);

                if (x == -1 && y == 0) {
                    updateScore(tileId);
                } else {
                    writeToTile(x, y, tileId);
                }
                if (runAI) {
                    stepAI(x,y,tileId,out);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stepAI(int x, int y, int tileId, PrintWriter out) {
       if (tileId == 4) {
           // ball painted, send direction if we know where the paddle is
           if (paddleSeen) sendDirection(out);
           ballSeen = true;
       } else if (tileId == 3) {
           // paddle painted, send direction if we know where the ball and paddle are
           // but only when the game is not already afoot
           if (!gameStarted && ballSeen) sendDirection(out);
           paddleSeen = true;
       }
       if (ballSeen && paddleSeen) {
           gameStarted = true;
       }
    }

    private void sendDirection(PrintWriter out) {
        int direction = Integer.compare(ballX,paddleX);
        out.println(direction);
        out.flush();
    }

    private void updateScore(int score) {
        this.score = score;
    }

    private void writeToTile(int x, int y, int tileId) {
        if (tiles == null) {
            tiles = new int[100][100];
        }
        if (tiles.length <= y) {
            tiles = Arrays.copyOf(tiles,y*2);
        }
        if (tiles[0].length <= x) {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = Arrays.copyOf(tiles[i], x*2);
            }
        }
        tiles[y][x] = tileId;

        if (tileId == 3) {
            paddleX = x;
            paddleY = y;
        }
        if (tileId == 4) {
            ballX = x;
            ballY = y;
        }
    }

    public int tileAt(int x, int y) {
        if (tiles == null) {
            return 0;
        }
        if (tiles.length <= y) {
            return 0;
        }
        if (tiles[y].length <= x) {
            return 0;
        }
        return tiles[y][x];
    }

    public int tileCount(int tileId) {
        int count = 0;
        for (int y = 0; y<tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == tileId) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getScore() {
        return this.score;
    }

    public void join() {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startAI() {
        this.runAI = true;
    }

    public void connectOutput(OutputStream output) {
        this.output = output;
    }
}
