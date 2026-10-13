import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Point> points = new ArrayList<>();

        // 1. Ввод 5 точек пользователем
        System.out.println("=== Ввод координат для 5 точек ===");
        for (int i = 1; i <= 5; i++) {
            System.out.print("Введите X и Y для точки " + i + " через пробел: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }

        System.out.println("\n=== Расчет кратчайшего расстояния между парами точек ===");

        // 2. Демонстрируем работу функции с разными стратегиями
        DistanceCalculator euclid = new EuclideanDistance();
        DistanceCalculator vonNeumann = new VonNeumannDistance();
        DistanceCalculator moore = new MooreDistance();

        System.out.printf("По Евклиду: %.4f\n", findShortestDistance(points, euclid));
        System.out.printf("По фон Нейману: %.4f\n", findShortestDistance(points, vonNeumann));
        System.out.printf("По Муру: %.4f\n", findShortestDistance(points, moore));

        scanner.close();
    }

    public static double findShortestDistance(List<Point> points, DistanceCalculator calculator) {
        if (points == null || points.size() < 2) {
            return 0.0;
        }

        double minDistance = Double.MAX_VALUE;

        // Перебираем все уникальные пары точек (i, j)
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double distance = calculator.calculate(points.get(i), points.get(j));
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }
}

// КЛАСС ТОЧКИ

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// ИНТЕРФЕЙС ВЫЧИСЛЕНИЯ РАССТОЯНИЯ

interface DistanceCalculator {
    double calculate(Point p1, Point p2);
}

// РЕАЛИЗАЦИИ ИНТЕРФЕЙСА

// 1. Расстояние по Евклиду: sqrt((x2-x1)^2 + (y2-y1)^2)
class EuclideanDistance implements DistanceCalculator {
    @Override
    public double calculate(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
}

// 2. Расстояние по фон Нейману: |x2-x1| + |y2-y1|
class VonNeumannDistance implements DistanceCalculator {
    @Override
    public double calculate(Point p1, Point p2) {
        return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
    }
}

// 3. Расстояние по Муру: max(|x2-x1|, |y2-y1|)
class MooreDistance implements DistanceCalculator {
    @Override
    public double calculate(Point p1, Point p2) {
        return Math.max(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
    }
}

