import java.util.Scanner;

public class Game {
    static char[][] map = new char[9][9];
    static Tank p1 = new Tank(1, 7);
    static Tank p2 = new Tank(7, 1);
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            draw();
            playerTurn(p1, 1);

            draw();
            playerTurn(p2, 2);
        }
    }

    static void playerTurn(Tank t, int num) {
        while (true) {
            System.out.print("Игрок " + num + " (w/a/s/d — двигаться, f — стрелять): ");
            String cmd = sc.nextLine().trim().toLowerCase();

            if (cmd.isEmpty()) {
                System.out.println("Введи команду!\n");
                continue;
            }
            if (!"wasdf".contains(cmd) || cmd.length() != 1) {
                System.out.println("Только w, a, s, d или f!\n");
                continue;
            }

            int oldX = t.x;
            int oldY = t.y;
            char oldLook = t.look;

            switch (cmd) {
                case "w" -> t.look = '^';
                case "a" -> t.look = '<';
                case "s" -> t.look = 'v';
                case "d" -> t.look = '>';
            }

            boolean actionWasValid = true;

            if ("wasd".contains(cmd)) {
                int newX = t.x;
                int newY = t.y;

                if (cmd.equals("w")) newY--;
                if (cmd.equals("s")) newY++;
                if (cmd.equals("a")) newX--;
                if (cmd.equals("d")) newX++;

                if (newX < 0 || newX > 8 || newY < 0 || newY > 8 || map[newY][newX] == '#') {
                    System.out.println("ты упираешься в стену\n");
                    actionWasValid = false;
                } else {
                    t.x = newX;
                    t.y = newY;
                }
            }

            if (cmd.equals("f")) {
                int dx = 0, dy = 0;
                switch (t.look) {
                    case '^' -> dy = -1;
                    case 'v' -> dy = 1;
                    case '<' -> dx = -1;
                    case '>' -> dx = 1;
                }

                int x = t.x + dx;
                int y = t.y + dy;

                if (x < 0 || x > 8 || y < 0 || y > 8 || map[y][x] == '#') {
                    System.out.println("не могу стрелять, впереди препятствие\n");
                    actionWasValid = false;
                } else {
                    while (x >= 0 && x < 9 && y >= 0 && y < 9 && map[y][x] != '#') {
                        if (x == p1.x && y == p1.y) {
                            System.out.println("\nигрок 2 победил\n");
                            System.exit(0);
                        }
                        if (x == p2.x && y == p2.y) {
                            System.out.println("\nигрок 1 победил\n");
                            System.exit(0);
                        }
                        map[y][x] = '*';
                        x += dx; y += dy;
                    }
                }
            }

            if (!actionWasValid) {
                t.look = oldLook;
                continue;
            }

            return;
        }
    }

    static void draw() {
        map[4][4] = '#';
        map[3][4] = '#';
        map[5][4] = '#';
        map[4][3] = '#';
        map[4][5] = '#';

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (map[i][j] != '#') {
                    map[i][j] = '.';
                }

        map[p1.y][p1.x] = p1.look;
        map[p2.y][p2.x] = p2.look;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
