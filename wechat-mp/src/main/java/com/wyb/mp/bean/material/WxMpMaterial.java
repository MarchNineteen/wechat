package com.wyb.mp.bean.material;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class WxMpMaterial implements Serializable {
    private static final long serialVersionUID = -1651816949780969485L;

    private String name;
    private File file;
    private String videoTitle;
    private String videoIntroduction;

    public WxMpMaterial() {
    }

    public WxMpMaterial(String name, File file, String videoTitle, String videoIntroduction) {
        this.name = name;
        this.file = file;
        this.videoTitle = videoTitle;
        this.videoIntroduction = videoIntroduction;
    }

    public Map<String, String> getForm() {
        Map<String, String> form = new HashMap<>();
        form.put("title", this.videoTitle);
        form.put("introduction", this.videoIntroduction);
        return form;
    }
}
