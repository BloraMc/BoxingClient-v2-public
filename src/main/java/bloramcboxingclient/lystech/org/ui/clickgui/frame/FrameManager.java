package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import java.io.*;
import java.util.List;

public class FrameManager {
    private List<Frame> frames;
    private File file;

    public FrameManager(List<Frame> frames) {
        this.frames = frames;
        this.file = new File("frame-positions.txt");
    }

    public void save() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (Frame frame : frames) {
                writer.println(frame.getX() + ":" + frame.getY());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (!file.exists()) {
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                frames.get(i).setX(x);
                frames.get(i).setY(y);
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}