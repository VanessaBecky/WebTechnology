/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author aimabi
 */
@Entity
public class Amavuna {
    @Id
    private String time;
    private String amavunaId;
    private String preacher;
    private String script;

    public String getAmavunaId() {
        return amavunaId;
    }

    public void setAmavunaId(String amavunaId) {
        this.amavunaId = amavunaId;
    }

    public String getPreacher() {
        return preacher;
    }

    public void setPreacher(String preacher) {
        this.preacher = preacher;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
