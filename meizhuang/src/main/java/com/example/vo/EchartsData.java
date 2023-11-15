package com.example.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EchartsData implements Serializable {

    private Map title;
    private Map legend;
    private Map tooltip;
    private Map xAxis;
    private Object yAxis;
    private List<Series> series;

    public Map getTitle() {
        return title;
    }

    public void setTitle(Map title) {
        this.title = title;
    }

    public Map getLegend() {
        return legend;
    }

    public void setLegend(Map legend) {
        this.legend = legend;
    }

    public Map getxAxis() {
        return xAxis;
    }

    public void setxAxis(Map xAxis) {
        this.xAxis = xAxis;
    }

    public Object getyAxis() {
        return yAxis;
    }

    public void setyAxis(Object yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
    public Map getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map tooltip) {
        this.tooltip = tooltip;
    }


    public static class Data {
        private String orient;
        private Integer top;
        private String x;
        private List<Object> data;

        public String getOrient() {
            return orient;
        }

        public void setOrient(String orient) {
            this.orient = orient;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }

    public static class Series {
        private String name;
        private String type;
        private String radius;
        private List<Object> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }
}
