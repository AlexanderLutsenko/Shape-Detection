package shapedetection.violajones;

import java.util.LinkedList;
import java.util.List;

/**
 * Стадия(Stage) классификатора. Состоит из нескольких деревьев особенностей и границы.
 * Если сумма значений, возвращаемых каждым деревом, превосходит границу, то стадия пройдена, иначе - нет.
 */
public class Stage {

    List<Tree> trees;
    float threshold;

    public Stage(float threshold) {
        this.threshold = threshold;
        trees = new LinkedList<>();
    }

    public void addTree(Tree t) {
        trees.add(t);
    }

    public boolean pass(int[][] integralImage, int[][] squares, int i, int j, float scale) {
        float sum = 0;
        //Суммы значений, возвращаемая каждым деревом
        for (Tree t : trees) {
            sum += t.getVal(integralImage, squares, i, j, scale);
        }
        
        return sum > threshold;
    }
}