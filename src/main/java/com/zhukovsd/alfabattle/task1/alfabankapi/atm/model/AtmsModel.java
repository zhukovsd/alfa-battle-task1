package com.zhukovsd.alfabattle.task1.alfabankapi.atm.model;

import java.util.List;

public class AtmsModel {

    private Data data;

    public AtmsModel() {
    }

    public AtmsModel(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<AtmModel> atms;

        public Data() {
        }

        public Data(List<AtmModel> atms) {
            this.atms = atms;
        }

        public List<AtmModel> getAtms() {
            return atms;
        }

        public void setAtms(List<AtmModel> atms) {
            this.atms = atms;
        }
    }

}
