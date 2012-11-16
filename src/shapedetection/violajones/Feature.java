package shapedetection.violajones;

import java.util.ArrayList;
import java.awt.Point;

/**
 * "Особенность" классификатора (она же feature, она же примитив Хаара) - 
 * прямоугольник, состоящий из светлих и тёмных областей (тоже прямоугольных).
 * Имеет границу перехода. Засчитывает тест, если разность интенсивностей светлых и тёмных областей 
 * превосходит границу.
 */
public class Feature { 

    Rect[] rects;
    int nb_rects;
    float threshold;
    float left_val;
    float right_val;
    Point size;
    int left_node;
    int right_node;
    boolean has_left_val;
    boolean has_right_val;

    public Feature(float threshold, float left_val, int left_node, boolean has_left_val,
            float right_val, int right_node, boolean has_right_val, Point size) {
        nb_rects = 0;
        rects = new Rect[3];
        this.threshold = threshold;
        this.left_val = left_val;
        this.left_node = left_node;
        this.has_left_val = has_left_val;
        this.right_val = right_val;
        this.right_node = right_node;
        this.has_right_val = has_right_val;
        this.size = size;
    }

    public int getLeftOrRight(int[][] integralImage, int[][] squares, int i, int j, float scale) {

        int w = (int) (scale * size.x);
        int h = (int) (scale * size.y);
        double inv_area = 1. / (w * h);

        /* Для этого и нужно интегральное изображение - быстро вычисляем сумму и "квадратную сумму"
         * интенсивностей пикселей в данной области, а затем - среднюю интенсивность этой области*/
        int total_x = integralImage[i + w][j + h] + integralImage[i][j] - integralImage[i][j + h] - integralImage[i + w][j];
        int total_x2 = squares[i + w][j + h] + squares[i][j] - squares[i][j + h] - squares[i + w][j];
        double moy = total_x * inv_area;
        double vnorm = total_x2 * inv_area - moy * moy;
        vnorm = (vnorm > 1) ? Math.sqrt(vnorm) : 1;

        int rect_sum = 0;
        // Для каждого прямоугольника в особенности 
        for (int k = 0; k < nb_rects; k++) {
            Rect r = rects[k];
            //масштабируем прямоугольник в соответствии с размером окна
            int rx1 = i + (int) (scale * r.x1);
            int rx2 = i + (int) (scale * (r.x1 + r.y1));
            int ry1 = j + (int) (scale * r.x2);
            int ry2 = j + (int) (scale * (r.x2 + r.y2));
            //Добавляем к итоговой сумме сумму интенсивностей пикселей внутри прямоугольника, умноженную на его вес
            rect_sum += (int) ((integralImage[rx2][ry2] - integralImage[rx1][ry2] - integralImage[rx2][ry1] + integralImage[rx1][ry1]) * r.weight);
        }
        double rect_sum2 = rect_sum * inv_area;

        /* Возвращаем LEFT или RIGHT в зависимости от того, перешла итоговая сумма
         * границу или нет */
        return (rect_sum2 < threshold * vnorm) ? Tree.LEFT : Tree.RIGHT;

    }

    public void add(Rect r) {
        rects[nb_rects++] = r;
    }
}
