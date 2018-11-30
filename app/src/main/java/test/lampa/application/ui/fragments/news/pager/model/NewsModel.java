package test.lampa.application.ui.fragments.news.pager.model;

public class NewsModel {

    private String title;
    private String image;
    private String source;
    private String date;
    private boolean selected;

    public NewsModel(String title, String image, String source, String date) {
        this.title = title;
        this.image = image;
        this.source = source;
        this.date = date;
        this.selected = false;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
