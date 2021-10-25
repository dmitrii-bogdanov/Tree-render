package bogdanov.treerender;

import bogdanov.treerender.utils.Renderer;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
        System.out.println("Should have 2 parameters");
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

//        Renderer renderer = new Renderer("in.txt", "out.txt");
        Renderer renderer = new Renderer(inputFileName, outputFileName);
        renderer.run();

    }

}

