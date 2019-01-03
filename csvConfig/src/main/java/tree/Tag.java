package tree;

public class Tag {
    String name;
    String value;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
        this.value = "";
    }

    public Tag(String name, String value) {
        this.name = name;
        this.value = transform(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = transform(value);
    }

    private String transform(String value) {
        if (value.equals("TRUE")) {
            return "true";
        } else if (value.equals("FALSE")) {
            return "false";
        } else return value;
    }
}
