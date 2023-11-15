package com.example.entity;

import java.util.List;

public class AuthorityInfo {
    private Integer level;
    private String name;
    private List<Model> models;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public static class Model {
        private Integer modelId;
        private List<Integer> operation;

        public Integer getModelId() {
            return modelId;
        }

        public void setModelId(Integer modelId) {
            this.modelId = modelId;
        }

        public List<Integer> getOperation() {
            return operation;
        }

        public void setOperation(List<Integer> operation) {
            this.operation = operation;
        }
    }
}
