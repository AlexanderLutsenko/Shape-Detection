package shapedetection.violajones;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.*;

public class Detector {

    ArrayList<LinkedList<Stage>> cascades; //Список каскадов, по которым будут находиться образы
    //Каждый каскад состоит из стадий.
    ArrayList<Point> sizes; //Базовые размеры сканирующих окон для каскадов (будут считаны из xml-файлов)
    Point maxSize; //Макс. из размеров сканирующих окон

    public static Detector create(LinkedList<String> filenames) {
        //Чтение xml-файлов (созданных посредством утилит из библиотеки OpenCV)

        LinkedList<org.jdom.Document> documents = new LinkedList<>();

        SAXBuilder sxb = new SAXBuilder();

        //Преобразование файлов в xml-документы
        for (String filename : filenames) {
            try {
                documents.add(sxb.build(new File(filename)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Detector(documents);
    }

    /**
     * Конструктор детектора. Читает список xml-документов и создаёт по нему
     * список каскадов
     */
    public Detector(LinkedList<org.jdom.Document> documents) {

        cascades = new ArrayList<>(documents.size());
        sizes = new ArrayList<>(documents.size());

        int maxXsize = 0;
        int maxYsize = 0;

        for (org.jdom.Document document : documents) {

            LinkedList cascade = new LinkedList<Stage>();

            /* Читаем базовый размер сканирующего окна (в пикселях) */
            Element racine = (Element) document.getRootElement().getChildren().get(0);
            Scanner scanner = new Scanner(racine.getChild("size").getText());
            Point size = new Point(scanner.nextInt(), scanner.nextInt());

            /* Считываем все стадии (stages) */
            Iterator it = racine.getChild("stages").getChildren("_").iterator();
            while (it.hasNext()) {
                Element stage = (Element) it.next();
                /* Считываем границу (treeshold) для каждого сильного классификатора */
                float thres = Float.parseFloat(stage.getChild("stage_threshold").getText());

                /*Считываем все деревья особенностей(features), из которых состоят стадии*/
                Iterator it2 = stage.getChild("trees").getChildren("_").iterator();
                Stage st = new Stage(thres);
                while (it2.hasNext()) {
                    Element tree = ((Element) it2.next());
                    Tree t = new Tree();
                    Iterator it4 = tree.getChildren("_").iterator();
                    while (it4.hasNext()) {
                        Element feature = (Element) it4.next();
                        float thres2 = Float.parseFloat(feature.getChild("threshold").getText());
                        int left_node = -1;
                        float left_val = 0;
                        boolean has_left_val = false;
                        int right_node = -1;
                        float right_val = 0;
                        boolean has_right_val = false;
                        Element e;
                        if ((e = feature.getChild("left_val")) != null) {
                            left_val = Float.parseFloat(e.getText());
                            has_left_val = true;
                        } else {
                            left_node = Integer.parseInt(feature.getChild("left_node").getText());
                            has_left_val = false;
                        }

                        if ((e = feature.getChild("right_val")) != null) {
                            right_val = Float.parseFloat(e.getText());
                            has_right_val = true;
                        } else {
                            right_node = Integer.parseInt(feature.getChild("right_node").getText());
                            has_right_val = false;
                        }
                        Feature f = new Feature(thres2, left_val, left_node, has_left_val, right_val, right_node, has_right_val, size);
                        Iterator it3 = feature.getChild("feature").getChild("rects").getChildren("_").iterator();
                        while (it3.hasNext()) {
                            String s = ((Element) it3.next()).getText().trim();
                            Rect r = Rect.fromString(s);
                            f.add(r);
                        }

                        t.addFeature(f);
                    }
                    st.addTree(t);
                }
                cascade.add(st);
            }
            cascades.add(cascade);
            sizes.add(size);
            if (size.x > maxXsize) {
                maxXsize = size.x;
            }
            if (size.y > maxYsize) {
                maxYsize = size.y;
            }
        }
        maxSize = new Point(maxXsize, maxYsize);
    }

    /**
     * Основной метод - нахождение искомых образов на изображении
     *
     * @param image - изображение, на котором будут искаться образы
     * @param baseScale Начальный размер сканирующего окна (в пикс.)
     * @param maxScale Максимальный размер сканирующего окна (в пикс.)
     * @param scaleMultiplier_inc число, на которое будет умножаться размер
     * скан. окна на каждом шаге (по умолчанию 1.25, для лучшего обнаружения -
     * 1.1 или даже меньше)
     * @param increment смещение положения сканирующего окна на каждом подшаге
     * (в процентах от размера скан. окна)
     * @return для каждого классификатора список прямоугольников, координаты
     * которых - область изображения, на которой обнаружен данный образ. т.о.
     * получаем двумерный список
     */
    public ArrayList<LinkedList<Rectangle>> getShapes(BufferedImage image, int baseScale, int maxScale, float scaleMultiplier_inc, float increment, int min_neighbors, boolean doCannyPruning) {
        try {

            /**
             * Преобразование абсолютных размеров baseScale и maxScale в
             * относительные, baseScaleMultiplier и maxScaleMultiplier -
             * начальный и максимальный множитель размера сканирующего окна (с
             * учётом допустимых значений).
             */
            float baseScaleMultiplier;
            float maxScaleMultiplier;

            int width = image.getWidth();
            int height = image.getHeight();

            if (baseScale < maxSize.x) {
                baseScale = maxSize.x;
            }
            if (maxScale < baseScale) {
                if (width / height > maxSize.x / maxSize.y) {
                    maxScale = (int) (maxSize.x / maxSize.y * height);
                } else {
                    maxScale = width;
                }
            }

            baseScaleMultiplier = baseScale / maxSize.x;
            maxScaleMultiplier = maxScale / maxSize.x;

            return getShapes(image, baseScaleMultiplier, maxScaleMultiplier, scaleMultiplier_inc, increment, min_neighbors, doCannyPruning);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LinkedList<Rectangle>> getShapes(BufferedImage image, float baseScaleMultiplier, float maxScaleMultiplier, float scaleMultiplier_inc, float increment, int min_neighbors, boolean doCannyPruning) {

        int width = image.getWidth();
        int height = image.getHeight();

        /* Переводим изображение в градации серого, создаём интегральное 
         * и "квадратно"-интегральное изображения */
        final int[][] integralImage = new int[width][height];
        final int[][] img = new int[width][height];
        final int[][] squares = new int[width][height];

        for (int i = 0; i < width; i++) {
            int col = 0;
            int col2 = 0;
            for (int j = 0; j < height; j++) {
                int c = image.getRGB(i, j);
                int red = (c & 0x00ff0000) >> 16;
                int green = (c & 0x0000ff00) >> 8;
                int blue = c & 0x000000ff;
                int value = (30 * red + 59 * green + 11 * blue) / 100;
                img[i][j] = value;
                integralImage[i][j] = (i > 0 ? integralImage[i - 1][j] : 0) + col + value;
                squares[i][j] = (i > 0 ? squares[i - 1][j] : 0) + col2 + value * value;
                col += value;
                col2 += value * value;
            }
        }

        /* Строим изображение с выделенными границами, если опция включена */
        int[][] canny = null;
        if (doCannyPruning) {
            canny = CannyPruner.getIntegralCanny(img);
        }

        //Инициализируем выходной массив прямоугольников
        final ArrayList<LinkedList<Rectangle>> ret = new ArrayList<>(cascades.size());
        for (int o = 0; o < cascades.size(); o++) {
            ret.add(new LinkedList<Rectangle>());
        }

        //Сердце алгоритма - поиск образов 

        //Создаём пул потоков. Число одновременно выполняющихся потоков - кол-во доступных процессоров виртуальной машины.
        int processors_num = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors_num);

        //Ищем для каждого размера скан. окна создаём отдельный поток
        for (float scaleMultiplier = baseScaleMultiplier; scaleMultiplier < maxScaleMultiplier; scaleMultiplier *= scaleMultiplier_inc) {

            /*Смещение сканирующего окна*/
            int step = (int) (scaleMultiplier * maxSize.x * increment);
            int size = (int) (scaleMultiplier * this.maxSize.x);

            //Для потоков нужны переменные, помеченные как final
            final int width_f = width;
            final int height_f = height;
            final int size_f = size;
            final int step_f = step;
            final float scaleMultiplier_f = scaleMultiplier;
            final int[][] canny_f = canny;
            final boolean doCannyPruning_f = doCannyPruning;



            Runnable r = new Runnable() {
                public void run() {
                    
                    //Для каждого положения скан. окна 
                    for (int i = 0; i < width_f - size_f; i += step_f) {
                        for (int j = 0; j < height_f - size_f; j += step_f) {

                            /*Если CannyPruning включён, вычисляем плотность границ в данном скан. окне.
                             * если она слишком мала, то образа, вероятно, здесь нет.*/
                            if (doCannyPruning_f) {
                                int edges_density = canny_f[i + size_f][j + size_f] + canny_f[i][j] - canny_f[i][j + size_f] - canny_f[i + size_f][j];
                                int d = edges_density / size_f / size_f;
                                if (d < 20 || d > 100) {
                                    continue;
                                }
                            }

                            //Пропускаем содержимое скан. окна через каждый каскад
                            int k = 0;
                            for (LinkedList<Stage> cascade : cascades) {
                                boolean pass = true;

                                /*Если на какой-то стадии классификатор не зачёл тест, 
                                 *то прекращаем поск - здесь искомого образа нет */
                                for (Stage s : cascade) {
                                    if (!s.pass(integralImage, squares, i, j, scaleMultiplier_f * maxSize.x / sizes.get(k).x)) {
                                        pass = false;
                                        break;
                                    }
                                }

                                //Дабы избежать повреждения данных, производим запись в synchronized-блоке
                                if (pass) {
                                    synchronized (ret) {
                                        LinkedList<Rectangle> temp = ret.get(k);
                                        temp.add(new Rectangle(i, j, size_f, size_f));
                                        ret.set(k, temp);
                                    }
                                }
                                k++;
                            }
                        }
                    }
                }
            };
            //Добавляем в пул текущий поток
            pool.execute(r);
        }
        //Блокируем добавление в пул потоков
        pool.shutdown();
        
        //Ждём окончания выполнения всех потоков (для запаса - 10 секунд)
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        /*Для каждого каскада сливаем вместе перекрывающиеся найденные прямоугольники,
         *выдаём их в качестве результата */
        return merge(ret, min_neighbors);
    }

    /**
     * Обычно если на данном участке изображения действительно есть искомый
     * образ, он обнаруживается детектором почти во всех скан. окнах,
     * расположенных в непосредственной близости, т.о. получается группа
     * перекрывающихся/вложенных прямоугольников. Слияние этой группы в один
     * прямоугольник не только визуально улучшает результат работы детектора, но
     * и существенно снижает процент ложных срабатываний (если группа
     * прямоугольников состоит менее чем из количества, определяемого параметром
     * min_neighbors, то вся группа бракуется)
     *
     * @param rects список найденных прямоугольнов
     * @param min_neighbors мин. число прямоугольников, которые должны быть
     * рядом, чтобы не стать забракованными
     * @return список объединённых прямоугольников
     */
    public ArrayList<LinkedList<Rectangle>> merge(ArrayList<LinkedList<Rectangle>> rectsArray, int min_neighbors) {
        ArrayList<LinkedList<Rectangle>> mergedArray = new ArrayList<>(rectsArray.size());
        for (LinkedList<Rectangle> rects : rectsArray) {
            LinkedList<Rectangle> retour = new LinkedList<>();
            int[] ret = new int[rects.size()];
            int nb_classes = 0;
            for (int i = 0; i < rects.size(); i++) {
                boolean found = false;
                for (int j = 0; j < i; j++) {
                    if (equals(rects.get(j), rects.get(i))) {
                        found = true;
                        ret[i] = ret[j];
                    }
                }
                if (!found) {
                    ret[i] = nb_classes;
                    nb_classes++;
                }
            }
            int[] neighbors = new int[nb_classes];
            Rectangle[] rect = new Rectangle[nb_classes];
            for (int i = 0; i < nb_classes; i++) {
                neighbors[i] = 0;
                rect[i] = new Rectangle(0, 0, 0, 0);
            }
            for (int i = 0; i < rects.size(); i++) {
                neighbors[ret[i]]++;
                rect[ret[i]].x += rects.get(i).x;
                rect[ret[i]].y += rects.get(i).y;
                rect[ret[i]].height += rects.get(i).height;
                rect[ret[i]].width += rects.get(i).width;
            }
            for (int i = 0; i < nb_classes; i++) {
                int n = neighbors[i];
                if (n >= min_neighbors) {
                    Rectangle r = new Rectangle(0, 0, 0, 0);
                    r.x = (rect[i].x * 2 + n) / (2 * n);
                    r.y = (rect[i].y * 2 + n) / (2 * n);
                    r.width = (rect[i].width * 2 + n) / (2 * n);
                    r.height = (rect[i].height * 2 + n) / (2 * n);
                    retour.add(r);
                }
            }
            mergedArray.add(retour);
        }
        return mergedArray;
    }

    //Возвращает true, если прямоугольники перекрываются и должны быть объединены
    public boolean equals(Rectangle r1, Rectangle r2) {
        int distance = (int) (r1.width * 0.2);

        if (r2.x <= r1.x + distance
                && r2.x >= r1.x - distance
                && r2.y <= r1.y + distance
                && r2.y >= r1.y - distance
                && r2.width <= (int) (r1.width * 1.2)
                && (int) (r2.width * 1.2) >= r1.width) {
            return true;
        }
        if (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width && r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height) {
            return true;
        }
        return false;
    }
}
