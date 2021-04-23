package com.qkm.springbootredis.POJO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    private String comment;

    /**
     * 
     */
    private String creatat;

    /**
     * 
     */
    private String blogcreatatandname;

    /**
     * 
     */
    private String bossid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Comment other = (Comment) that;
        return (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getCreatat() == null ? other.getCreatat() == null : this.getCreatat().equals(other.getCreatat()))
            && (this.getBlogcreatatandname() == null ? other.getBlogcreatatandname() == null : this.getBlogcreatatandname().equals(other.getBlogcreatatandname()))
            && (this.getBossid() == null ? other.getBossid() == null : this.getBossid().equals(other.getBossid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getCreatat() == null) ? 0 : getCreatat().hashCode());
        result = prime * result + ((getBlogcreatatandname() == null) ? 0 : getBlogcreatatandname().hashCode());
        result = prime * result + ((getBossid() == null) ? 0 : getBossid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", comment=").append(comment);
        sb.append(", creatat=").append(creatat);
        sb.append(", blogcreatatandname=").append(blogcreatatandname);
        sb.append(", bossid=").append(bossid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}