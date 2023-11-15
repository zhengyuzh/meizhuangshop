package com.example.vo;

import com.example.entity.MessageInfo;

import java.util.List;

public class MessageInfoVo extends MessageInfo {
    private List<MessageInfoVo> children;

    public List<MessageInfoVo> getChildren() {
        return children;
    }

    public void setChildren(List<MessageInfoVo> children) {
        this.children = children;
    }
}
