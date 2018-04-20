package c.ab.accommodationfinder;

/**
 * Created by avina on 4/18/2018.
 */

public class BindImages {
    private String name;
    private int imageID;

    public BindImages(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
