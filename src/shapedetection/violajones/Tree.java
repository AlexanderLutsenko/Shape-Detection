package shapedetection.violajones;

import java.util.ArrayList;
import java.util.List;

/**
 * Двоичное дерево, состоящее из особенностей. В зависимости от результата теста
 * на каждом узле переходим к левому или правому потомку. Если идти в выбранном
 * направлении нельзя, возвращается хранящееся в узле левое или правое значение
 * (в завис-ти от направления)
 */
public class Tree {

    final static int LEFT = 0;
    final static int RIGHT = 1;
    List<Feature> features;

    public Tree() {
        features = new ArrayList<Feature>();
    }

    public void addFeature(Feature f) {
        features.add(f);
    }

    public float getVal(int[][] integralImage, int[][] squares, int i, int j, float scale) {
        Feature cur_node = features.get(0);
        while (true) {
            //вычисляем значение особенности в узле и решаем, куда идти
            int where = cur_node.getLeftOrRight(integralImage, squares, i, j, scale);
            if (where == LEFT) {
                if (cur_node.has_left_val) {
                    return cur_node.left_val;
                } else {
                    cur_node = features.get(cur_node.left_node);
                }

            } else {
                if (cur_node.has_right_val) {
                    return cur_node.right_val;
                } else {
                    cur_node = features.get(cur_node.right_node);
                }
            }
        }
    }
}
