package cn.metast.tuoke.module.community.controller.app.cmPost.vo;

public class KeyWord {

    /**
     * 关键字的值
     */
    private String value;

    public KeyWord() {
    }

    public KeyWord(String value) {
        this.value = value;
    }

    public KeyWord(String value, String color) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
