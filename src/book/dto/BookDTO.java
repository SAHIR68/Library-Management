package book.dto;

public class BookDTO {
    private Integer id;
    private String title;

    private String writer;
    private boolean reserve;

    public BookDTO(Integer id, String title, boolean reserve) {
        this.id = id;
        this.title = title;
        this.reserve = reserve;
    }

    public BookDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isReserved() {
        return reserve;
    }


    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                ", title='" + title + '\'' +
                ", writer='" + writer +
                ", reserve=" + reserve +
                '}';
    }
}
